(ns wire.out.user-response
  (:require [schema.core :as s]))


(s/defschema UserResponse
  {:id s/Uuid
   :name s/Str
   :surname s/Str
   :age s/Int})


(s/defschema AllUsersResponse
  [[{:db/id s/Int
     :user/id s/Uuid
   :user/name s/Str
   :user/surname s/Str
   :user/age s/Int}]])
