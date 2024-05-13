(ns adapters.user
  (:require [wire.in.user :as w.in.user] 
            [schema.core :as s]
            [models.user]))

(s/defn wire-in->internal :- models.user/User
  [{:keys [name surname age]} :- w.in.user/UserRequest]
  {:user/name name
   :user/surname surname
   :user/age age})