(ns core.hello
  (:require [core.db :as db :refer [accounts users]]
            [core.server :as server]))

;======================== USERS =====================


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
  (let [user-uuid (random-uuid) ;CLJ version -> entender pq nao vai sem interoperbilidade
        {{:keys [name surname age]} :json-params} request]
    (swap! db/users assoc user-uuid {:name name :surname surname :age age})
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
    (swap! db/users assoc user-uuid {:name name :surname surname :age age})
    {:status 200 :body (str "user info updated" (@users user-uuid))}))

(defn delete-user! [request]
  (let [user-id (get-in request [:path-params :id])
        user-uuid (java.util.UUID/fromString user-id)]
    (swap! db/users dissoc user-uuid)
    {:status 204 :body (str "User id # " user-uuid ": deleted!")}))


;======================== ACCOUNT =====================



(defn response-create-account-200
  [user-uuid]
  {:status  200
   :headers {"Content-Type" "text/plain"}
   :body (str "Account successfuly created for " (:name (@users user-uuid)) "\n"
              "Account(s) details: " (get @accounts user-uuid) "\n"
              "Great Success ⭐ \n")})
;this needs to be shown for each account - returns now a map with info for each

(def response-404
  {:status  404
   :headers {"Content-Type" "text/plain"}
   :body    "Not found ❌"})

(defn response-500 [error-msg]
  {:status  500
   :headers {"Content-Type" "text/plain"}
   :body    error-msg})

;TODO - balance for each account
(defn create-account!
  [request]
  (let [user-id (get-in request [:path-params :user-id])
        user-uuid (java.util.UUID/fromString user-id)
        accounts-info (:json-params request)]
    (try (if-not (contains? @users user-uuid)
           response-404
           (let [created-accounts (for [{:keys [account-type deposit]} accounts-info]
                                    {:account-uuid (random-uuid)
                                     :status "Active"
                                     :type account-type
                                     :amount deposit})]
             (do (swap! db/accounts assoc user-uuid (vec created-accounts))
                 (swap! db/users assoc-in [user-uuid :accounts] (vec (map :account-uuid (get @accounts user-uuid))))
                 (response-create-account-200  user-uuid))))
         (catch Exception e
           (response-500 (.getMessage e))))))

(defn user-accounts [request]
  (let [user-id (get-in request [:path-params :user-id])
        user-uuid (java.util.UUID/fromString user-id)
        user-accounts (vec (map :type (get @accounts user-uuid)))]
    (try (if-not (nil? (get @accounts user-uuid))
           {:status 200 :body (str "This user has the following accounts: " user-accounts)}
           response-404)
         (catch Exception e
           (response-500 (.getMessage e))))))


(defn user-deposits-by-account [request]
  {:status 200 :body "user-deposits-by-account 🏧"})


(comment
  (server/start)
  (server/stop)
  (server/reset-server)

  @users
  @accounts

  ;(println (test-request :delete "/users/4c1e6f95-8875-4a90-ae06-799151834500"))

  ;(test-request-post :post "/users" {:json-params {:name "John", :surname "Doe", :age 30}} {"Content-Type" "text/plain"})
  ;(= 200 (:status (test-request :get "/hello")))
  (:name (@users #uuid "11e735a5-feaa-458a-8c62-449ba5aa60dc"))
  (java.util.UUID/fromString "11e735a5-feaa-458a-8c62-449ba5aa60dc")

  (def mock-accs {#uuid "e291f340-7e1b-4f78-9cd6-22afeb04eebc"
                  [{:account-uuid #uuid "c2d003cd-a14f-4546-b2e1-0c3681bf8c12", :status "Active", :type "checking", :amount 0}
                   {:account-uuid #uuid "b646dbe2-fad2-47b7-bdaa-5b7fd8a8b484", :status "Active", :type "savings", :amount 0}]}) 


  (map :type (get @accounts #uuid "e291f340-7e1b-4f78-9cd6-22afeb04eebc"))
  (get @accounts #uuid "e291f340-7e1b-4f78-9cd6-22afeb04eebc")
  (map :type (get @accounts #uuid "e291f340-7e1b-4f78-9cd6-22afeb04eebc"))
  (map :amount (get @accounts #uuid "e291f340-7e1b-4f78-9cd6-22afeb04eebc"))
  )