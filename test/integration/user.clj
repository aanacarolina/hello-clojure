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

(defflow create-user-endpoint
  (flow "Given a request to create a user should return a user"
        (match? 1
                (return (helper.http/request :post "/users" user-json)))

        #_(flow "Given a succesful user creation should query this user on DB to confirm persistance" ;flow dentro do mesmo flow! 
                (match?  {:id created-id
                          :name "John"
                          :surname "Doe"
                          :age 30}
                         (helper.db/datomic-query (:id  (:id completed-user-creation)))))))

#_(defflow get-user-by-id-endpoint
  (flow "Given an existent user query by its id (uuid) and should return a user"
        [completed-user-creation (helper.http/request :get "/users/:id" user-uuid)
         created-id (return (:id (:body completed-user-creation)))]
        (match? {:status 201, :body {:id created-id
                                     :name "John"
                                     :surname "Doe"
                                     :age 30}} completed-user-creation)

        (flow "Given a succesful user creation should query this user on DB to confirm persistance" ;flow dentro do mesmo flow! 
              (match?  {:id id
                        :name "John"
                        :surname "Doe"
                        :age 30}
                       (helper.db/datomic-query (:id  (:id completed-user-creation)))))))



;TODO test create endpoints




