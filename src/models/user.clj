(ns models.user
  (:require [schema.core :as s]))


;entity 

(s/def id s/Uuid)
(s/def user-name s/Str)
(s/def surname s/Str)
(s/def age s/Int)

;does not validateeee!
(s/defschema UserCreated
  {:user/id id
   :user/name user-name
   :user/surname surname
   :user/age age})

(s/defschema UserNew
  {:user/name user-name
   :user/surname surname
   :user/age age})
