(ns integration.aux.http
   (:require
   [state-flow.api :refer [flow get-state return]]
   [io.pedestal.http :as http]
   [io.pedestal.test :as test]
     [clojure.edn :as edn]))


(def fetch-servelet
  (comp ::http/service-fn deref :server :server :system))



(defn *request
  ([http verb url]
   (let [{:keys [status body]} (test/response-for http verb url)]
     {:status status
      :body   (edn/read-string body)}))
  ([http verb url body-request]
   (let [{:keys [status body]} (test/response-for http verb url
                                                  :body body-request
                                                  :headers {"Content-Type" "application/json"})]
     {:status status
      :body   (edn/read-string body)})))

(defn request
  ([verb url]
   (flow "make request"
         [http (get-state fetch-servelet)]
         (return (*request http verb url))))
  ([verb url body]
   (flow "make request"
         [http (get-state fetch-servelet)] 
         (return (*request http verb url body)))))

