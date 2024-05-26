(ns db.schema
  (:require [datomic.api :as d]
            [dev.nu.morse :as morse]))


(def user [{:db/ident :user/name
            :db/valueType :db.type/string
            :db/cardinality :db.cardinality/one
            :db/doc "User first name"}
            {:db/ident :user/surname
            :db/valueType :db.type/string
            :db/cardinality :db.cardinality/one
            :db/doc "User family name"}
            {:db/ident :user/age
            :db/valueType :db.type/long
            :db/cardinality :db.cardinality/one
            :db/doc "User age"}])


#_(defn create-schema [conn]
  (morse/inspect @(d/transact conn user :io-context "olar")))