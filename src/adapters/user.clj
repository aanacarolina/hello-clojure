(ns adapters.user
  (:require [wire.in.user :as w.in.user]
            [wire.out.user-response :as w.out.user]
            [schema.core :as s]
            [models.user]))

(s/defn wire-in->internal :- models.user/UserNew
  [{:keys [name surname age]} :- w.in.user/UserRequest]
  {:user/name name
   :user/surname surname
   :user/age age})

(s/defn internal->wire-out :- w.out.user/UserResponse
  [{:user/keys [id name surname age]} :- models.user/UserCreated] 
  {:id id
   :name name
   :surname surname
   :age age})

(s/defn wire-in-hello->internal :- (s/maybe models.user/user-name)
  [{:keys [name]} :- w.in.user/HelloRequest]
  name)