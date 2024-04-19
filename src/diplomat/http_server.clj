
(ns diplomat.http-server
  (:require
   [io.pedestal.http.body-params :as body-params]))

;======================== USERS =====================

(defn respond-hello [request]
  (let [user-name (get-in request [:query-params :name])]
    (if user-name
      {:status 200 :body (str "Hello, " user-name "\n")}
      {:status 200 :body (str "Hello, stranger\n" request)})))

(defn all-users [request]
  {:status 200
   :body (str "List of all users: " @(get-in request [:components :db :atom-database]))})

(defn create-user! [request] 
  (let [user-uuid (random-uuid)
        {{:keys [name surname age]} :json-params} request]
       (swap! (get-in request [:components :db :atom-database]) assoc user-uuid {:name name :surname surname :age age})
    {:status 201 :body (str "new user created" (last @(get-in request [:components :db :atom-database])))}
    (println "✅✅✅✅✅✅✅✅✅✅✅✅" request "✅✅✅✅✅✅✅✅✅✅✅✅")))


(defn user-by-id
  [request]
  (let [user-id  (get-in request [:path-params :id])
        user-uuid (java.util.UUID/fromString user-id)
        user-not-found {:status  404
                        :headers {"Content-Type" "text/plain"}
                        :body    "User not found"}]
    (if (not (contains? @(get-in request [:components :db :atom-database]) user-uuid))
      user-not-found
      {:status 200 :body (@(get-in request [:components :db :atom-database]) user-uuid)})))

;static methods belong to the class not to the object
(defn query-user
  [request]
  (let [user-id  (get-in request [:query-params :id])
        user-uuid (java.util.UUID/fromString user-id)
        name (get-in request [:query-params :name])
        surname (get-in request [:query-params :surname])
        age  (get-in request [:query-params :age])]
    (if (contains? @(:atom-database request) (or user-uuid name surname age))
      {:status 200 :body (@(get-in request [:components :db :atom-database]) user-uuid)}
      {:status 200 :body (str "Info provided not found in the user base")})))

;melhorar depois - qdo cirar funcoes de filtro
(defn update-user!
  [request]
  (let [user-id (get-in request [:path-params :id])
        user-uuid (java.util.UUID/fromString user-id)
        {{:keys [name surname age]} :json-params} request]
    (swap! (:atom-database request) assoc user-uuid {:name name :surname surname :age age})
    {:status 200 :body (str "user info updated" (@(get-in request [:components :db :atom-database]) user-uuid))}))

(defn delete-user! [request]
  (let [user-id (get-in request [:path-params :id])
        user-uuid (java.util.UUID/fromString user-id)]
    (swap! @(get-in request [:components :db :atom-database]) dissoc user-uuid)
    {:status 204 :body (str "User id # " user-uuid ": deleted!")}))


;======================== ACCOUNT =====================



#_(defn response-create-account-200
    [user-uuid]
    {:status  200
     :headers {"Content-Type" "text/plain"}
     :body (str "Account successfuly created for " (:name (@(get-in request [:components :db :atom-database]) user-uuid)) "\n"
                "Account(s) details: " (get @accounts user-uuid) "\n"
                "Great Success ⭐ \n")})
;this needs to be shown for each account - returns now a map with info for each

#_(def response-404
    {:status  404
     :headers {"Content-Type" "text/plain"}
     :body    "Not found ❌"})

#_(defn response-500 [error-msg]
    {:status  500
     :headers {"Content-Type" "text/plain"}
     :body    error-msg})

;TODO - balance for each account
#_(defn create-account!
    [request]
    (let [user-id (get-in request [:path-params :user-id])
          user-uuid (java.util.UUID/fromString user-id)
          accounts-info (:json-params request)]
      (try (if-not (contains? @(get-in request [:components :db :atom-database]) user-uuid)
             response-404
             (let [created-accounts (for [{:keys [account-type deposit]} accounts-info]
                                      {:account-uuid (random-uuid)
                                       :status "Active"
                                       :type account-type
                                       :amount deposit})]
               (do (swap! db/accounts assoc user-uuid (vec created-accounts))
                   (swap! (get-in request [:components :db :atom-database]) assoc-in [user-uuid :accounts] (vec (map :account-uuid (get @accounts user-uuid))))
                   (response-create-account-200  user-uuid))))
           (catch Exception e
             (response-500 (.getMessage e))))))

#_(defn user-accounts [request]
    (let [user-id (get-in request [:path-params :user-id])
          user-uuid (java.util.UUID/fromString user-id)
          user-accounts (vec (map :type (get @accounts user-uuid)))]
      (try (if-not (nil? (get @accounts user-uuid))
             {:status 200 :body (str "This user has the following accounts: " user-accounts)}
             response-404)
           (catch Exception e
             (response-500 (.getMessage e))))))


#_(defn user-deposits-by-account [request]
    {:status 200 :body "user-deposits-by-account 🏧"})

(def common-interceptors [(body-params/body-params)])

(def endpoints #{["/hello" :get respond-hello :route-name :hello]
                 ["/users" :get all-users :route-name :users]
                 ["/users" :post (conj common-interceptors create-user!) :route-name :create-user]
                 ["/users/:id" :get user-by-id :route-name :user-by-id]
                 ["/users/q" :get query-user :route-name :query-user-by-id]
                 ["/users/:id" :put (conj common-interceptors update-user!) :route-name :update-user]
                 ["/users/:id" :delete delete-user! :route-name :delete-user]
                    ;accounts starts here
                        ;["/accounts/:user-id" :post (conj common-interceptors hello/create-account!) :route-name :create-account]
                        ;["/user/account/:user-id" :get hello/user-accounts :route-name :user-accounts]
                    ;need to make deposit to have a history - to display the amount just change route above with :amount instead of :type 
                 #_["/users/:user-id/account/:account-id/type" :get user-deposits-by-account :route-name :user-deposits-by-account]})

