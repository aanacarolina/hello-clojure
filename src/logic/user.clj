(ns logic.user
  (:require [schema.core :as s]
            ;[models.user]
            [db.schema :as datomic.model]))

;uuid aqui eh recebida por parametro pra ser pura!
(s/defn new-user 
  [uuid :- s/Uuid 
   model :- datomic.model/user]
  (assoc model :user/id uuid))