(ns logic.user
  (:require [schema.core :as s]
            [models.user]))

;uuid aqui eh recebida por parametro pra ser pura!
(s/defn new-user :- models.user/UserCreated
  [uuid :- s/Uuid 
   model :- models.user/User]
  (assoc model :user/id uuid))