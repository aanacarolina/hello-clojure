(ns db.user
  (:require [schema.core :as s]
            [models.user]
            [protocols.database :as p.database]
            [components.db :as atom-db]
            [datomic.api :as d]))

(defn select-db [_ db-type]
  (p.database/db-type db-type)
  )

(defmulti create-user! 
  select-db)
;return the function! não está executando - definicao do defmult



;params is not used, so we could have used [_]
(s/defmethod create-user! :datomic [user db]
  (println "DATOMIC")
  (println "user" user)
  (println "!!!!!!")
  (let [result @(d/transact (:datomic db) [user])
        db-after (:db-after result)]
    (println "db-after result" db-after)
    (pull db-after info)))

;transact retorna promise / tem q pegar a info do db after
;como fazer - query pull vai ser tbm pra user-by-id 

;update
#_(d/transact db [[:db/add [id :user/age age] ;if I know the DATOMIC ID DO ATRIBUTO
                 :db/add [[:user/id user] :user/age age] ;If I need to retriet the DATOMIC ID do atributo
                 :db/add []]])

(s/defmethod create-user! :atom-db [user db]
  (last (swap! (:atom-db db) conj user)))

;TODO implement with dynamo
#_(s/defmethod create-user! :dynamo [user db]
  (last (swap! (:dynamo db) conj user)))

;TODO implement with common-datomic
#_(s/defmethod create-user! :dynamo [user db]
  (last (swap! (:dynamo db) conj user)))

;;default handling
(s/defmethod create-user! :default [_ params]
  (throw (IllegalArgumentException.
          (str "Select another database [datomic, in-memory]. I currently don't accept" (get params :type) " DB"))))

;(create-user! {:user "carol"} (atom-db/new-atomdatabase))

;(p.database/db-type (atom-db/new-atomdatabase))