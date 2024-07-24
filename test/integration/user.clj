(ns integration.user
  (:require
   [state-flow.api :refer [flow match? get-state return]] 
   [state-flow.state :as state ]
   [integration.aux.init :refer [defflow]]
   [io.pedestal.http :as http]
   [io.pedestal.test :as test] ))

;;===================== PLAIN FLOW lib
;setup =  components
;http/endpoints = http.helpers - 
;https://github.com/nubank/state-flow?tab=readme-ov-file#writing-helpers


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
#_(defn servlet []
   (get @(get-in (flow "a" (state/get)) [:system :serve :server]) ::http/service-fn))


(defn servlet []
  (flow "a" (state/return (get-state (comp ::http/service-fn deref :server :serve :system)))))


(defflow hello-endpoint
  (flow "testing" 
        [servlet (servlet)]
        (match? 1  (test/response-for servlet :get "/hello"))))


