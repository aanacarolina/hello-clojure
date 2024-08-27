(ns wire.out.user-response
  (:require [schema.core :as s]))


(s/defschema UserResponse
  {:id s/Uuid
   :name s/Str
   :surname s/Str
   :age s/Int})
