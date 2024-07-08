(ns unit.adapters.user-test (:require
                             [clojure.test :refer :all]
                             [adapters.user]
                             [schema.core :as s]
                             [models.user]
                             [wire.in.user]))



(defn my-test-fixture [f]
  (s/set-fn-validation! true)
  (f)
  (s/set-fn-validation! false))


(use-fixtures :once my-test-fixture)

(def user-info {:name "Fulana" :surname "De Silva" :age 42})

(deftest wire-in->internal-test
  (testing "adapts from request to internal"
    (is (= {:user/name "Fulana" :user/surname "De Silva" :user/age 42}
           (adapters.user/wire-in->internal user-info)))))

;gen/fmap
;alura tests module