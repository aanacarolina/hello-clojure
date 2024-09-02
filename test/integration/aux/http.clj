(ns integration.aux.http
  (:require
   [state-flow.api :refer [flow get-state return]]
   [io.pedestal.http :as http]
   [io.pedestal.test :as test]
   [clojure.edn :as edn]
   #_[data.json :as json]))


(def fetch-servelet
  (comp ::http/service-fn deref :server :server :system))


 ;& args variadic - qdo body nao existir nao envia na req / a resposta eh igual {:status and :body} . se body read-string
(defn *request
  ([verb url]
   (let  [http  (get-state fetch-servelet)
          {:keys [status body]} (test/response-for http verb url)]
     {:status status :body body :headers {"Content-Type" "application/edn"}}))
  ([verb url body-request]
   (let  [http  (get-state fetch-servelet)
          {:keys [status body]} (test/response-for http verb url body-request)]
     {:status status :body body :headers {"Content-Type" "application/edn"}})))

(defn request
  ([verb url]
   (flow "make request"
         [http (get-state fetch-servelet)]
         (return (test/response-for http verb url))))
  ([verb url body] ;& args variadic - qdo body nao existir nao envia na req / a resposta eh igual {:status and body} . se body read-string
   (flow "make request"
         [http (get-state fetch-servelet)]
         (return (test/response-for http verb url :body body)))))


(defn request-v2
  ([verb url]
   (flow "make request" 
         (return (*request verb url))))
  ([verb url body-request] 
   (flow "make request" 
         (return (*request verb url body-request)))))