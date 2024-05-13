(ns db.user
  (:require [schema.core :as s]
            [models.user]))

(s/defn create-user! :- models.user/User
  [user :- models.user/User
   db :- s/atom]
  (last (swap! db conj user)))