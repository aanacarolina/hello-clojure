(ns unit.user-test
  (:require
   [clojure.test :refer [deftest is testing]]
   [controllers.user]
   [logic.user]))

(defonce user-id (random-uuid)) 
(defonce user-info {:name "Fulano" :surname "De Tal" :age 42}) 

(deftest new-user-test
  (testing "New user"
    (is (= {:name "Fulano" :surname "De Tal" :age 42 :user/id user-id}
           (logic.user/new-user user-id user-info)))))



