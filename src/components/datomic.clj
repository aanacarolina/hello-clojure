(ns components.datomic
  (:require [com.stuartsierra.component :as component]
            [db.schema :as d-schema]
            [datomic.api :as d]
            [protocols.database :as p.database]
            [protocols.config :as c-pro]))




;retorna a conexao com datomic
(defrecord Datomic [config]
  ;implementando o protocolo que informa o tippo de database
  p.database/Database
  (db-type [_] :datomic)
  ;Implementa o ciclo de vida the Lifecycle protocol
  component/Lifecycle
  ;this é um mapa dos componentes que temos temos do system 
  (start [this]
         (println "🛢️ Starting Datomic")
    ;creates DB using the connection string (db-uri)     
    (let [db-uri (c-pro/get-value config :database-uri)] 
     (d/create-database db-uri)
    (let [conn (d/connect db-uri)]
      ;(morse/launch-in-proc)
      ;schema sao datoms - ja startamos com essa transaction - nao necessariamente uma boa prática
      (d-schema/create-schema conn)
      (assoc this :datomic conn))))

  (stop [this]
    (println "🛑🛢️ Stopping Datomic")
    (when (:datomic this)
      (d/release (:datomic this)))
    (dissoc this :datomic)))

;factory method - cria instancias
(defn new-datomic-db []
  (map->Datomic {}))
