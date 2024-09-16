(ns integration.user
  (:require
   [state-flow.api :refer [flow match? get-state return]]
   [state-flow.state :as state]
   [integration.aux.init :refer [defflow]]
   [integration.aux.http :as helper.http]
   [integration.aux.db :as helper.db] 
     [clojure.edn :as edn]
   ))


(def user-json "{ \"name\": \"John\", \"surname\": \"Doe\", \"age\": 30}")
(def expected-response {:status 201 :body {:name "John", :surname "Doe", :age 30}})

(def user-uuid "3ae1dc15-b3ff-4529-be66-38c6ececc7bb")

(defflow hello-endpoint
  (flow "Given a request without a name (query param) to /hello, should return hello world  message"
        [say-hello (return (helper.http/request :get "/hello")) ]
        (match? {:status 200 :body {:msg "Hello, stranger"}}
                say-hello)))

(defflow crud-endpoints
  (flow "Given a request to create a user should return a user"
        [{:keys [body] {:keys [id]} :body :as created-user} (helper.http/request :post "/users" user-json)]
        (match? expected-response created-user)
        (flow "Given a succesful user creation should query this user on DB to confirm persistance" ;flow dentro do mesmo flow! 
                (match?  {:user/id id
                          :user/name "John"
                          :user/surname "Doe"
                          :user/age 30}
                         (helper.db/datomic-query id)))
        (flow "Given an existent user query by its id (uuid) and should return a user"
              [completed-user-creation (helper.http/request :get (str "/users/" id))]
              (match? {:status 200, :body body} completed-user-creation))
        
        (flow "Given an existent user we will delete it"
              [{:keys [body status]} (helper.http/request :delete (str "/users/" id))]
              (match? {:status 204, :body nil} {:status status :body body}))))

;TODO test create endpoints




