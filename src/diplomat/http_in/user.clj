(ns diplomat.http-in.user
  (:require [wire.in.user :as w.in.user]
            [schema.core :as s]
            [controllers.user]
            [adapters.user])
  (:import (clojure.lang ExceptionInfo)))


(defn create-user! 
  [{:keys [json-params components]}]
  (try (s/validate w.in.user/UserRequest json-params)
       (-> json-params
           adapters.user/wire-in->internal
           (controllers.user/create-user! (get-in components [:db :atom-database])))
       {:status 201 :body (last @(get-in components [:db :atom-database]))}
       (catch ExceptionInfo e
         {:status 400 :body (.getMessage e)})))

