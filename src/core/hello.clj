(ns core.hello
  (:require [core.db :as db :refer [accounts users]]
            [core.server :as server]))




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