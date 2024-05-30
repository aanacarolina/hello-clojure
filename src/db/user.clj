(ns db.user
  (:require [schema.core :as s]
            [models.user]
            [protocols.database :as p.database]
            [components.db :as atom-db]
            [datomic.api :as d]))

(defn select-db [_ db-type]
  (p.database/db-type db-type))

(defmulti create-user!
  select-db)
;return the function! não está executando - definicao do defmult

;params is not used, so we could have used [_]
(s/defmethod create-user! :datomic [user db]
  (d/transact (:datomic db) [user]))

;update
#_(d/transact db [[:db/add [id :user/age age] ;if I know the DATOMIC ID DO ATRIBUTO
                 :db/add [[:user/id user] :user/age age] ;If I need to retriet the DATOMIC ID do atributo
                 :db/add []]])

(s/defmethod create-user! :atom-db [user db]
  (last (swap! db conj user)))

;;default handling
(s/defmethod create-user! :default [_ params]
  (throw (IllegalArgumentException.
          (str "Select another database [datomic, in-memory]. I currently don't accept" (get params :type) " DB"))))

;then can use this like this:
(def english-map {"id" "1", "language" "English"})
(def french-map  {"id" "2", "language" "French"})
(def spanish-map {"id" "3", "language" "Spanish"})

(create-user! {:user "carol"} (atom-db/new-atomdatabase))

(p.database/db-type (atom-db/new-atomdatabase))