(ns integration.user
  (:require
   [state-flow.api :refer [flow match? get-state return]]
   [state-flow.state :as state]
   [integration.aux.init :refer [defflow]]
   [integration.aux.http :as helper.http]
   [integration.aux.db :as helper.db]
   [io.pedestal.http :as http]
   [io.pedestal.test :as test]))

;;===================== PLAIN FLOW lib
;setup =  components
;http/endpoints = http.helpers - 
;https://github.com/nubank/state-flow?tab=readme-ov-file#writing-helpers
; precisei criar o helper que as docs ja tinham prontos - utilizando o servelet do jetty (http/service-fn) e passando para o funcao pedestal da funcao (test/response-for)

#_(flow "Given:
       - a server running
       - then calls hello world endpoint
       - returns success status and Hello, stranger \n"
        (components/run-dev!);faltaa o mapa estado inicial - binding?
        (http/endpoints {:method :get ;binding [] - http-helpers/request req
                         :uri "/hello"
                         :body {:status 200 :body "Hello, stranger \n"}})
        (match?  {:status 200 :body "Hello, stranger \n"} (endpoint-req)))

;;===================== NUBANK Testing routes with Servlet
;https://nubank.atlassian.net/wiki/spaces/ENGCHAP/pages/263675510883/Integration+test#Testing-routes-with-Servlet

;defflow é uma macro -  usa o componente para iniciliaza o servidor e usa datomic in mem
;setup = sim  -  teardown = (nenhum)?

#_(defflow say-hello
    (match? {:status 200 :body "Hello, stranger \n"}
            (servlet/get {:uri "/hello" :route-name :hello})))

;;===================== CALCULUS-101 repo
; https://github.com/nubank/calculus-101

#_(defflow hello-endpoint
  ;; The /api/answer-of-life endpoint should return {:status 200 :body {:answer 42}}.
  ;; There is a bug in the implementation. Create a flow to check it without
  ;; looking at the implementation. Then fix the implementation after you
  ;; see the test fail. 
    (get-state (comp :server :system))
    (flow "/hello endpoint endpoint that returns hello message"
          (match? {:status 200
                   :body   "Hello, stranger \n"}
                  (servlet/request {:method :get :uri "/hello"}))))

;-----


(defflow hello-endpoint
  (flow "Given a request without a name (query param) to /hello, should return hello world  message"
        (match? {:status 200, :body "Hello, stranger \n"}  (helper.http/request :get "/hello"))))

; NIL
(defflow create-user-endpoint-1
  (flow "Given a request to create a user should return a user"
        [completed-user-creation (helper.http/request :post "/users"
                                                      "{ \"name\": \"John\",
                                                                 \"surname\": \"Doe\",
                                                                 \"age\": 30}")]
   
               (match? {:status 201, :body {:id (:id (:body completed-user-creation))   
                                            :name "John"
                                            :surname "Doe"
                                            :age 30}} completed-user-creation)

        #_(flow "Given a user creation should be able to query this user on DB to confirm persistance" ;flow dentro do mesmo flow! 
                (match?  {:id id
                          :name "John"
                          :surname "Doe"
                          :age 30}
                         (helper.db/datomic-query (:id  (:id completed-user-creation)))))))

 ; FAIL in integration.user/create-user-endpoint:
; ; create-user-endpoint -> Given a request to create a user should return a user (user.clj:60) -> match? (user.clj:66) -> (cats.core/do-let ...)
; ; expected:
; {:status 201, :body {:id nil, :name "John", :surname "Doe", :age 30}}
; 
; ; actual:
; {:status 201,
;  :body
;  (mismatch
;   (expected {:id nil, :name John, :surname Doe, :age 30})
;   (actual
;    {:id #uuid "cba6458e-487c-4cd6-8959-d309c9d0ca38", :name "John", :surname "Doe", :age 30})),
;  :headers
;  {Sxxxxx}}


;nil
(defflow create-user-endpoint
  (flow "Given a request to create a user should return a user"
        [completed-user-creation (helper.http/request :post "/users"
                                                      "{ \"name\": \"John\",
                                                                 \"surname\": \"Doe\",
                                                                 \"age\": 30}")
         id (:id (:body completed-user-creation))]
        (match? {:status 201, :body {:id id
                                     :name "John"
                                     :surname "Doe"
                                     :age 30}} completed-user-creation)

        #_(flow "Given a user creation should be able to query this user on DB to confirm persistance" ;flow dentro do mesmo flow! 
                (match?  {:id id
                          :name "John"
                          :surname "Doe"
                          :age 30}
                         (helper.db/datomic-query (:id  (:id completed-user-creation)))))))


;exception
(defflow create-user-endpoint-2
  (flow "Given a request to create a user should return a user"
        [completed-user-creation (helper.http/request :post "/users"
                                                      "{ \"name\": \"John\",
                                                                 \"surname\": \"Doe\",
                                                                 \"age\": 30}")
         id (get-in completed-user-creation [:body :id])]
        (match? {:status 201, :body {:id id
                                     :name "John"
                                     :surname "Doe"
                                     :age 30}} completed-user-creation)

        #_(flow "Given a user creation should be able to query this user on DB to confirm persistance" ;flow dentro do mesmo flow! 
                (match?  {:id id
                          :name "John"
                          :surname "Doe"
                          :age 30}
                         (helper.db/datomic-query (:id  (:id completed-user-creation)))))))

;                        state-flow.core/clarify-illegal-arg       core.clj:  137
; clojure.lang.ExceptionInfo: Expected a flow, got class: nil
; 
; ERROR in integration.user/create-user-endpoint-2 (core.clj):
; Uncaught exception, not in assertion
; error: clojure.lang.ExceptionInfo: Flow "create-user-endpoint-2 -> Given a request to create a user should return a user (user.clj:118) -> [completed-user-creation ...]" failed with exception {} (core.clj)


;alem do binding tbm parece que o problema eh coom uuid, pq com body fica ok
(defflow create-user-endpoint-3
  (flow "Given a request to create a user should return a user"
        [completed-user-creation (helper.http/request :post "/users"
                                                      "{ \"name\": \"John\",
                                                                 \"surname\": \"Doe\",
                                                                 \"age\": 30}")]
        (match? {:status 201, :body {:id (:body completed-user-creation)
                                     :name "John"
                                     :surname "Doe"
                                     :age 30}} completed-user-creation)

        #_(flow "Given a user creation should be able to query this user on DB to confirm persistance" ;flow dentro do mesmo flow! 
                (match?  {:id id
                          :name "John"
                          :surname "Doe"
                          :age 30}
                         (helper.db/datomic-query (:id  (:id completed-user-creation)))))))

; ; FAIL in integration.user/create-user-endpoint-3:
; ; create-user-endpoint-3 -> Given a request to create a user should return a user (user.clj:145) -> match? (user.clj:151) -> (cats.core/do-let ...)
; ; expected:
; {:status 201,
;  :body
;  {:id
;   "{:id #uuid \"3c487dcc-669c-4453-b16f-2508595a9ed7\", :name \"John\", :surname \"Doe\", :age 30}",
;   :name "John",
;   :surname "Doe",
;   :age 30}}
; 
; ; actual:
; {:status 201,
;  :body
;  (mismatch
;   (expected
;    {:id
;          {:id #uuid "3c487dcc-669c-4453-b16f-2508595a9ed7", :name "John", :surname "Doe", :age 30},
;          :name John,
;          :surname Doe,
;          :age 30})
;   (actual
;    {:id #uuid "3c487dcc-669c-4453-b16f-2508595a9ed7", :name "John", :surname "Doe", :age 30})),
;  :headers
;  {...;
;TODO test create endpoints


;https://clojuredocs.org/clojure.core/with-redefs


