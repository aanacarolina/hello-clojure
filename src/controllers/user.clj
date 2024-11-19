(ns controllers.user
  (:require [wire.in.user :as w.in.user]
            [schema.core :as s]
            [models.user]
            [db.schema :as datomic.model]
            [logic.user]
            [db.user]
            [protocols.database :as p.database])
  (:import (clojure.lang ExceptionInfo)))

;======================== REFATORADAS =====================

;so chama logic e banco de dados
(s/defn create-user! :- models.user/UserCreated
  [user :- models.user/UserNew
   db :- p.database/IDatabase]
  (let [new-user (logic.user/new-user (random-uuid) user)
        user-created (db.user/create-user! new-user db)]
       (println "RESULT> " user-created " <RESULT")
    user-created))

(s/defn user-by-id! :- models.user/UserCreated
  [id :- s/Uuid
   db :- p.database/IDatabase]
  (let [user-by-id (db.user/user-by-id! id db)]
    user-by-id))

;if user nao existe, nao estou tratando
;nil can't be typed
(s/defn delete-user! 
  [user-id :- s/Uuid
   db :- p.database/IDatabase]
  (db.user/delete-user-by-id! user-id db))


;problemas: 1 param entao db primeiro, problema pro db-type args
;nao tem body nem p e q params
(s/defn all-users 
  [db :- p.database/IDatabase]
  #_(println "controller function saying hello" db "!!")
  (db.user/query-all-users db))

(s/defn respond-hello
  [name :- (s/maybe s/Str)]
  (if name
    {:msg (str "Hi, " name)}
    {:msg (str "Hello, stranger")}))

;======================== USERS =====================


;static methods belong to the class not to the object
(defn query-user
  [request]
  (let [user-id  (get-in request [:query-params :id])
        user-uuid (java.util.UUID/fromString user-id)
        name (get-in request [:query-params :name])
        surname (get-in request [:query-params :surname])
        age  (get-in request [:query-params :age])]
    (if (contains? @(:atom-db request) (or user-uuid name surname age))
      {:status 200 :body (@(get-in request [:components :database]) user-uuid)}
      {:status 200 :body (str "Info provided not found in the user base")})))

;melhorar depois - qdo cirar funcoes de filtro
(defn update-user!
  [request]
  (let [user-id (get-in request [:path-params :id])
        user-uuid (java.util.UUID/fromString user-id)
        {{:keys [name surname age] :as data} :json-params} request]
    (try (s/validate w.in.user/UserRequest data)
         (swap! (get-in request [:components :database]) assoc user-uuid {:name name :surname surname :age age})
         {:status 200 :body (@(get-in request [:components :database]) user-uuid)}
         (catch ExceptionInfo e
           {:status 400 :body (.getMessage e)}))))


;======================== ACCOUNT =====================



#_(defn response-create-account-200
    [user-uuid]
    {:status  200
     :headers {"Content-Type" "text/plain"}
     :body (str "Account successfuly created for " (:name (@(get-in request [:components :database]) user-uuid)) "\n"
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
      (try (if-not (contains? @(get-in request [:components :database]) user-uuid)
             response-404
             (let [created-accounts (for [{:keys [account-type deposit]} accounts-info]
                                      {:account-uuid (random-uuid)
                                       :status "Active"
                                       :type account-type
                                       :amount deposit})]
               (do (swap! db/accounts assoc user-uuid (vec created-accounts))
                   (swap! (get-in request [:components :database]) assoc-in [user-uuid :accounts] (vec (map :account-uuid (get @accounts user-uuid))))
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