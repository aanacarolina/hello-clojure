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

(deftest wire-in->internal-test 
  (testing "user from req to internal"
    (is (= {:user/name "Fulano" :user/surname "De Tal" :user/age 42} user-info))))



