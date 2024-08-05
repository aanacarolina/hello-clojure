(ns integration.aux.http
   (:require
   [state-flow.api :refer [flow get-state return]]
   [io.pedestal.http :as http]
   [io.pedestal.test :as test]))


(def fetch-servelet
  (comp ::http/service-fn deref :server :server :system))

(defn request
  ([verb url]
   (flow "make request"
         [http (get-state fetch-servelet)]
         (return (test/response-for http verb url))))
  ([verb url body]
   (flow "make request"
         [http (get-state fetch-servelet)] 
         (return (test/response-for http verb url :body body :headers {"Content-Type" "application/json"})))))

