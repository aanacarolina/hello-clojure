(ns integration.aux.db
  (:require [state-flow.api :refer [flow get-state return match?]]
            [io.pedestal.http :as http]
            [io.pedestal.test :as test]
            [datomic.api :as d]
            [db.user ]))


(def fetch-datomic
  (comp :datomic :database :system)) 
  ;similar to with-resource + with-db? 
  ;private https://github.dev/nubank/state-flow-helpers/blob/436730c1d9dde688e251d6b7611ea6d07285dd9a/src/state_flow/helpers/core.clj#L58-L59



;(def fetch-atom-db)
;(defn query-atom-db)

(defn datomic-query 
  ([id]
   (flow "Query Datomic"
         [datomic (get-state fetch-datomic)] 
         (return (db.user/query-datomic-by-id id (d/db datomic))))))


(defn datomic-query-all
  ([]
   (flow "Query Datomic"
         [datomic (get-state fetch-datomic)]
         (return (db.user/datomic-get-all-users (d/db datomic))))))