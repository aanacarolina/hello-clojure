(ns adapters.user
  (:require [wire.in.user :as w.in.user]
            [wire.out.user-response :as w.out.user]
            [schema.core :as s]
            [models.user]))

(s/defn wire-in->internal :- models.user/User
  [{:keys [name surname age]} :- w.in.user/UserRequest]
  {:user/name name
   :user/surname surname
   :user/age age})

(s/defn wire-in-hello->internal :- models.user/User
  [{:keys [name]} :- w.in.user/UserRequest]
  {:name name})

(s/defn internal->wire-out :- w.out.user/UserResponse
  [{:user/keys [id name surname age]} :- models.user/User]
  {:id id
   :name name
   :surname surname
   :age age})