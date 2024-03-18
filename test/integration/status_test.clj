(ns integration.status-test
  (:require
   [hello-clojure.hello :refer :all]
   [io.pedestal.http :as http]
   [io.pedestal.test :as test]
   [clojure.test :refer [deftest is are testing]]))


(defn test-request [ verb url]
  (test/response-for (::http/service-fn @server) verb url))

(defn test-request-post [verb url body headers]
  (test/response-for (::http/service-fn @server) verb url body headers))

#_(deftest status200-test
  (testing "Success test"
    (is (= 200 (:status (test-request :get "/hello"))))))

(deftest success-test
  (testing "Success test"
   (is (= {:status 200,
           :body "Hello, stranger\n",
           :headers
           {"Strict-Transport-Security" "max-age=31536000; includeSubdomains",
            "X-Frame-Options" "DENY",
            "X-Content-Type-Options" "nosniff",
            "X-XSS-Protection" "1; mode=block",
            "X-Download-Options" "noopen",
            "X-Permitted-Cross-Domain-Policies" "none",
            "Content-Security-Policy"
            "object-src 'none'; script-src 'unsafe-inline' 'unsafe-eval' 'strict-dynamic' https: http:;",
            "Content-Type" "text/plain"}}
          (do (start)
              (test-request  :get "/hello")))
       (stop))))


(deftest success-create-user-test
  (testing "Success test"
    (is
     (= 201 (do (start)
                (:status (test-request-post :post
                                            "/users"
                                            {:json-params {:name "John", :surname "Doe", :age 30}}
                                            {"Content-Type" "text/plain"})
                         (stop)))))))