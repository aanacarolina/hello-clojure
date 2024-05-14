(ns models.user
  (:require [schema.core :as s]))


;entity 

(s/def id s/Uuid)
(s/def user-name s/Str)
(s/def surname s/Str)
(s/def age s/Int)

(s/defschema User
  {:user/id id
   :user/name user-name
   :user/surname surname
   :user/age age})

