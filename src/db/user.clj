(ns db.user
  (:require [schema.core :as s]
            [models.user]
            [protocols.database :as p.database]
            [components.db :as atom-db]
            [datomic.api :as d]))

; ============= utils =================

(defn select-db [_ db-type]
  (p.database/db-type db-type))

;TODO trocar ordem dos parametros aqui e nas funcoes q usam
(defn query-datomic-by-id
  [id db]
  (d/pull db '[:user/name :user/surname :user/age :user/id] [:user/id id]))

; ============= defmultis =================

;return the function! não está executando - definicao do defmult
(defmulti create-user!
  select-db)

(defmulti user-by-id!
  select-db)

(defmulti delete-user-by-id!
  select-db)


; ============= delete-user-by-id! =================
;might need to use retractEntity 
(s/defmethod delete-user-by-id! :datomic [id db]
  (let [conn (:datomic db)
        snapshot (d/db conn)
        eid (d/entid snapshot id)
        transact-result @(d/transact conn [[:db.fn/retractEntity eid]])] 
    transact-result))

#_(s/defmethod delete-user-by-id! :atom-db [id db]
  (get @(:atom-db db) id))

(s/defmethod delete-user-by-id! :default [_ params]
  (throw (IllegalArgumentException.
          (str (get params :type) "Database not supported!\n Please use :datomic or :atom-db"))))


; ============= create-user! =================

;transact retorna promise / tem q pegar a info do db after
(s/defmethod create-user! :datomic [user db]
  (let [result @(d/transact (:datomic db) [user])
        db-after (:db-after result)
        response (query-datomic-by-id (:user/id user) db-after)]
    response))

(s/defmethod create-user! :atom-db [user db]
  (last (swap! (:atom-db db) conj user)))

;default handling
(s/defmethod create-user! :default [_ params]
  (throw (IllegalArgumentException.
          (str "Select another database [datomic, in-memory]. I currently don't accept" (get params :type) " DB"))))

;TODO implement with dynamo
#_(s/defmethod create-user! :dynamo [user db]
    (last (swap! (:dynamo db) conj user)))

;TODO implement with common-datomic
#_(s/defmethod create-user! :dynamo [user db]
    (last (swap! (:dynamo db) conj user)))

; ============= user-by-id! =================

(s/defmethod user-by-id! :datomic [id db]
  (let [conn (:datomic db)
        snapshot (d/db conn)]
    (query-datomic-by-id id snapshot)))

(s/defmethod user-by-id! :atom-db [id db]
  (get @(:atom-db db) id))

(s/defmethod user-by-id! :default [_ params]
  (throw (IllegalArgumentException.
          (str (get params :type) "Database not supported!\n Please use :datomic or :atom-db"))))



; ============= update-user! =================
;update
#_(d/transact db [[:db/add [id :user/age age] ;if I know the DATOMIC ID DO ATRIBUTO
                   :db/add [[:user/id user] :user/age age] ;If I need to retriet the DATOMIC ID do atributo
                   :db/add []]])