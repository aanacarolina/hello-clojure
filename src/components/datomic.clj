(ns components.datomic
  (:require [datomic.api :as d]
            [db.schema :as d-schema]
            [com.stuartsierra.component :as component]
            [dev.nu.morse :as morse]))

(def db-uri "datomic:dev://localhost:4334/hello")

(defrecord Datomic []
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
