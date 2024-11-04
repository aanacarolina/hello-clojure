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

(defn datomic-get-all-users
  [db]
  (d/q '[:find (pull ?e [*])
         :where [?e :user/id]] db))

; ============= defmultis =================

;return the function! não está executando - definicao do defmult
(defmulti create-user!
  select-db)

(defmulti user-by-id!
  select-db)

(defmulti delete-user-by-id!
  select-db)

(defmulti query-all-users
  select-db)

; ============= delete-user-by-id! =================
;might need to use retractEntity 
(s/defmethod delete-user-by-id! :datomic [id db]
  (let [conn (:datomic db)
        snapshot (d/db conn)
        eid (d/entid snapshot [:user/id id])
        transact-result @(d/transact conn [[:db/retractEntity eid]])]
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

; ============= get-all-users =================

(s/defmethod query-all-users :datomic [db]
  (let [conn (:datomic db)
        snapshot (d/db conn)]
     (datomic-get-all-users snapshot)))

(s/defmethod query-all-users :default [_ params]
  (throw (IllegalArgumentException.
          (str (get params :type) "Database not supported!\n Please use :datomic or :atom-db"))))

(comment 
  
  (datomic-get-all-users (d/db (d/connect "datomic:dev://localhost:4334/hello")))

  (datomic-get-all-users (d/db (d/connect "datomic:mem://hello")))
#_[[{:db/id 17592186045457,
   :user/id #uuid "9de267bc-fdf1-4da0-bb90-e04a9caa0bb4",
   :user/name "aaaaa oier",
   :user/surname "ai dy",
   :user/age 13}]
 [{:db/id 17592186045465,
   :user/id #uuid "d826d6b8-9f05-49d3-9860-4db621675ea3",
   :user/name "ze",
   :user/surname "ng",
   :user/age 77}]]
  
  )
; ============= update-user! =================
;update
#_(d/transact db [[:db/add [id :user/age age] ;if I know the DATOMIC ID DO ATRIBUTO
                   :db/add [[:user/id user] :user/age age] ;If I need to retriet the DATOMIC ID do atributo
                   :db/add []]])