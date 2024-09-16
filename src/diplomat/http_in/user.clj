(ns diplomat.http-in.user
  (:require [wire.in.user :as w.in.user]
            [schema.core :as s]
            [controllers.user]
            [adapters.user])
  (:import (clojure.lang ExceptionInfo)))

;http-in chama uma controller e obtem uma resposta com status code 
;refatorar 
(defn create-user!
  [{:keys [json-params components]}]
  (let [response (-> json-params
                     adapters.user/wire-in->internal
                     (controllers.user/create-user! (get-in components [:database]))
                     adapters.user/internal->wire-out)]
    {:status 201 :body response}))

(defn respond-hello
  [{:keys [query-params]}]
  (let [response (-> query-params
                     adapters.user/wire-in-hello->internal
                     controllers.user/respond-hello)]
    {:status 200 :body response}))

(defn user-by-id
   [{:keys [path-params components]}]
  (let [user-uuid  (parse-uuid (:id path-params))
        response (-> user-uuid
                     (controllers.user/user-by-id! (get-in components [:database]))
                     adapters.user/internal->wire-out)]
    {:status 200 :body response}))

(defn delete-user!
  [{:keys [path-params components]}]
  (let [user-uuid (parse-uuid (:id path-params))
        response (-> user-uuid
                     (controllers.user/delete-user! (get-in components [:database])))]
    {:status 204 :body response}))

