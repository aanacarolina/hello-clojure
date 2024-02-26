(ns hello-clojure.hello
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [io.pedestal.http.body-params :as body-params]
            [io.pedestal.test :as test]))


(def users (atom {}))

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
      {:status 200 :body (str "Info provided not found in the user base")})
   ))

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

(def common-interceptors [(body-params/body-params)])

(def routes
  (route/expand-routes
   #{["/hello" :get respond-hello :route-name :hello]
     ["/users" :get all-users :route-name :users]
     ["/users" :post (conj common-interceptors create-user!) :route-name :create-user]
     ["/users/:id" :get user-by-id :route-name :user-by-id]
     ["/users/q" :get query-user :route-name :query-user-by-id]
     ["/users/:id" :put (conj common-interceptors update-user!) :route-name :update-user]
     ["/users/:id" :delete delete-user! :route-name :delete-user]}))

(defn create-server []
  (http/create-server
   {::http/routes routes
    ::http/type :jetty
    ::http/port 7171
    ::http/join? false}))

(defonce server (atom nil))

;integration test
(defn test-request [verb url]
  (test/response-for (::http/service-fn @server) verb url))

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

(comment
  (start) 
  (stop) 
  (reset-server)

  (println (test-request :delete "/users/4c1e6f95-8875-4a90-ae06-799151834500"))
  (println (test-request :put "/users/38ce74e5-2c28-473b-aef5-010c275c7027?name=Homer&surname=Simpson&age=40"))
  (test-request :get "/hello")
  @users
  )
