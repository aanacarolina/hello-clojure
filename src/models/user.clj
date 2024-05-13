(ns models.user
  (:require [schema.core :as s]))


;entity 
(s/defschema User
  {:user/id s/Uuid
   :user/name s/Str
   :user/surname s/Str
   :user/age s/Int})

