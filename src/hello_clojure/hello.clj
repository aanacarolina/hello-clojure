(ns hello-clojure.hello
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [io.pedestal.http.body-params :as body-params]
            [io.pedestal.test :as test]))

;======================== USERS =====================

(def users (atom  {#uuid "11e735a5-feaa-458a-8c62-449ba5aa60dc" {:name "Chaves", :surname "S." :age 8 }
                   #uuid "e291f340-7e1b-4f78-9cd6-22afeb04eebc" {:name "Quico" :surname "S." :age 7}
                   #uuid "4b96037f-f9a3-490f-99f3-5f9b32006edc" {:name "Chiquinha" :surname "S." :age 7}
                   #uuid "fb3281be-11c4-4907-83d0-722df301032f" {:name "Seu Madruga" :surname "S." :age 42}
                   #uuid "457d6d45-0d79-45c8-a743-892170ce356d" {:name "Dona Florinda" :surname "S." :age 35}}))

(defn respond-hello [request]
  (let [user-name (get-in request [:query-params :name])]
    (if user-name
      {:status 200 :body (str "Hello, " user-name "\n")}
      {:status 200 :body (str "Hello, stranger\n")})))



(defn all-users [request]
  {:status 200
   :body (str "List of all users: " @users)})

;refactor using path-params
;try postman (both form-data and raw/json)
;problems: string limitations / data leak / 
;refactor: get-in -> destructuring (less functions to the stack)
(defn create-user! [request]
  (let [user-uuid (java.util.UUID/randomUUID) ;CLJ version -> entender pq nao vai sem interoperbilidade
        {{:keys [name surname age]} :json-params} request]
    (swap! users assoc user-uuid {:name name :surname surname :age age})
    {:status 201 :body (str "new user created" (last @users))}))

(defn user-by-id
  [request]
  (let [user-id  (get-in request [:path-params :id])
        user-uuid (java.util.UUID/fromString user-id)
        user-not-found {:status  404
                        :headers {"Content-Type" "text/plain"}
                        :body    "User not found"}]
    (if (not (contains? @users user-uuid))
      user-not-found
      {:status 200 :body (@users user-uuid)})))

;static methods belong to the class not to the object
(defn query-user
  [request]
  (let [user-id  (get-in request [:query-params :id])
        user-uuid (java.util.UUID/fromString user-id)
        name (get-in request [:query-params :name])
        surname (get-in request [:query-params :surname])
        age  (get-in request [:query-params :age])]
    (if (contains? @users (or user-uuid name surname age))
      {:status 200 :body (@users user-uuid)}
      {:status 200 :body (str "Info provided not found in the user base")})))

;melhorar depois - qdo cirar funcoes de filtro
(defn update-user!
  [request] 
  (let [user-id (get-in request [:path-params :id])
        user-uuid (java.util.UUID/fromString user-id)
        {{:keys [name surname age]} :json-params} request]
    (swap! users assoc user-uuid {:name name :surname surname :age age})
    {:status 200 :body (str "user info updated" (@users user-uuid))}))

(defn delete-user! [request]
  (let [user-id (get-in request [:path-params :id])
        user-uuid (java.util.UUID/fromString user-id)]
    (swap! users dissoc user-uuid)
    {:status 204 :body (str "User id # " user-uuid ": deleted!")}))


;======================== ACCOUNT =====================

(def accounts (atom {}))

(defn response-create-account-200
  [user-uuid account-id account-type deposit]
    {:status  200
     :headers {"Content-Type" "text/plain"}
     :body (str "Account successfuly created for " user-uuid "\n"
                "Account ID: " account-id "\n"
                "Account status: Active \n"
                "Account type: " account-type  "\n"
                "A balance of R$" deposit "\n"
                "Great Success ⭐ \n")})
;this needs to be shown for each account - returns now a map with info for each

(def response-404
    {:status  404
     :headers {"Content-Type" "text/plain"}
     :body    "Can't open account. User not found ❌"})

(defn response-500 [error-msg]
    {:status  500
     :headers {"Content-Type" "text/plain"}
     :body    error-msg})

;TODO - balance for each account
(defn create-account!
  [request]
  (let [account-uuid (java.util.UUID/randomUUID)
        {{:keys [account-type deposit]} :json-params} request
        user-id (get-in request [:path-params :user-id])
        user-uuid (java.util.UUID/fromString user-id)
        json request] 
    (println "\n\n\n\n\n\n\n❗❗❗❗❗❗❗❗❗❗❗❗❗❗❗❗❗❗" request  "❗❗❗❗❗❗❗❗❗❗\n\n\n\n\n\n\n")
    (try (if-not (contains? @users user-uuid)
           response-404
           (do (swap! accounts assoc user-uuid  {:account-uuid account-uuid :status "Active" :type account-type :deposit deposit})
               (swap! users assoc-in [user-uuid :account [(get-in request [:request :json-params])]] (get-in request [:request :json-params]) )
               (response-create-account-200 (:name (@users user-uuid)) account-uuid account-type deposit)))
         (catch Exception e  
           (response-500 (.getMessage e))))))
;[x] todo id de account vai ser o od usuario
; e ai tem um mapa para cada account
;current response #uuid "11e735a5-feaa-458a-8c62-449ba5aa60dc" {:name "Chaves", :surname "S.", :age 8, :account {[nil] nil}},

;ja esta criando mas ainda com valores nil 


(defn user-accounts [request]
  (let [user-id (get-in request [:path-params :id])
        user-uuid (java.util.UUID/fromString user-id)
        account-type [0 0]] 
    {:status 200 :body (str "This user has " (count account-type) "account(s): " account-type)}))


(defn user-deposits-by-account [request]
  {:status 200 :body "user-deposits-by-account 🏧"})



;======================== ROUTES & INTERCEPTORS =====================

(def common-interceptors [(body-params/body-params)])
;duvida: criar um outro def? entity-routes e add to ::http/routes
(def routes
  (route/expand-routes
   #{["/hello" :get respond-hello :route-name :hello]
     ["/users" :get all-users :route-name :users]
     ["/users" :post (conj common-interceptors create-user!) :route-name :create-user]
     ["/users/:id" :get user-by-id :route-name :user-by-id]
     ["/users/q" :get query-user :route-name :query-user-by-id]
     ["/users/:id" :put (conj common-interceptors update-user!) :route-name :update-user]
     ["/users/:id" :delete delete-user! :route-name :delete-user]
     ;accounts starts here
     ["/accounts/:user-id" :post (conj common-interceptors create-account!) :route-name :create-account]
    #_["/users/:user-id/account" :get user-accounts :route-name :user-accounts]
     #_["/users/:user-id/account/:account-id/type" :get user-deposits-by-account :route-name :user-deposits-by-account]}))

;======================== SERVER =====================

(defn create-server []
  (http/create-server
   {::http/routes routes
    ::http/type :jetty
    ::http/port 7171
    ::http/join? false}))

(defonce server (atom nil))

(defn start []
  (try
    (reset! server (http/start (create-server)))
    (catch Exception e
      e)))

(defn stop []
  (try
    (http/stop @server)
    (catch Exception e
      e)))

(defn reset-server []
  (stop)
  (start))

;======================== TESTS =====================

;integration test
(defn test-request [verb url]
  (test/response-for (::http/service-fn @server) verb url))

(defn test-request-post [verb url body headers]
  (test/response-for (::http/service-fn @server) verb url body headers))

;======================== COMMENT =====================

(comment
  (start)
  (stop)  
  (reset-server)

  ;(println (test-request :delete "/users/4c1e6f95-8875-4a90-ae06-799151834500"))
  
  (test-request-post :post "/users" {:json-params {:name "John", :surname "Doe", :age 30}} {"Content-Type" "text/plain"})
  (test-request :get "/hello")
  (:name (@users #uuid "11e735a5-feaa-458a-8c62-449ba5aa60dc" ))
  (java.util.UUID/fromString "11e735a5-feaa-458a-8c62-449ba5aa60dc")
  ; (filter #(= (:user-uuid (val %)) user-uuid) accounts)
  @users 
  @accounts

  (def mock-req {:request {:json-params '({:account-type "checking", :deposit 0} {:account-type "savings", :deposit 0})}})
  (get-in mock-req [:request :json-params])
  (swap! users  assoc-in [user-uuid :account] (get-in mock-req [:request :json-params]))
  (swap! users  assoc-in [#uuid "e291f340-7e1b-4f78-9cd6-22afeb04eebc"  :account] '({:account-type "checking", :deposit 10} {:account-type "savings", :deposit 10}))
  

  )