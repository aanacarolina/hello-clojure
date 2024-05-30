(ns components.datomic
  (:require [com.stuartsierra.component :as component]
            [db.schema :as d-schema]
            [datomic.api :as d]
            [protocols.database :as p.database]))

;crio a string de conexao para o datomic
(def db-uri "datomic:dev://localhost:4334/hello")

;retorna a conexao com datomic
(defrecord Datomic []
  ;implementando o protocolo que informa o tippo de database
  p.database/Database
  (db-type [_] :datomic)
  ;Implementa o ciclo de vida the Lifecycle protocol
  component/Lifecycle
  ;this é um mapa dos componentes que temos temos do system 
  (start [this]
         (println "🛢️ Starting Datomic")
    ;creates DB     
    (d/create-database db-uri)
    (let [conn (d/connect db-uri)]
      ;(morse/launch-in-proc)
      (d-schema/create-schema conn)
      (assoc this :datomic conn)))

  (stop [this]
    (println "🛑🛢️ Stopping Datomic")
    (when (:datomic this)
      (d/release (:datomic this)))
    (dissoc this :datomic)))

;factory method - cria instancias
(defn new-datomic-db []
  (->Datomic))
