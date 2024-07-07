(ns unit.adapters.user-test(:require
                            [clojure.test :refer :all]
                            [adapters.user]
                            [schema.core :as s] 
                            [models.user]
                            [wire.in.user]))

;(s/set-fn-validation! true)

(defonce user-info {:name "Fulana" :surname "De Silva" :age "42"})

(deftest wire-in->internal-test
  (testing "adapts from request to internal"
    (is (= {:user/name "Fulana" :user/surname "De Silva" :user/age "42"}
           (adapters.user/wire-in->internal user-info))))
  #_(testing "output type validation"
    (is (nil? (s/check models.user/UserNew (adapters.user/wire-in->internal user-info)))))
  #_(testing "input type validation"
    (is (nil? (s/check wire.in.user/UserRequest user-info)))))

;gen/fmap
;alura tests module