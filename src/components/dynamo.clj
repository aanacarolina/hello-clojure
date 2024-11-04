(ns components.dynamo
  (:require [taoensso.faraday :as far]
            [protocols.database :as p.database]
            [com.stuartsierra.component :as component]))

;on tutorial symbol is client-opts
(def ddb-client 
  {:endpoint "http://localhost:8000"})

(far/list-tables ddb-client)

;almost same return of list, but shows indexes info
(far/describe-table ddb-client :my-table)

;
#_(far/create-table ddb-client :my-table
                  [:id :n]  ; Primary key named "id", (:n => number type); it is also my partition key - a.k.a. hash-keydef

                  {:range-keydef [:sort-key :s ] ;a.k.a sort-key
                   :throughput {:read 1 :write 1} ; Read & write capacity (units/sec)
                   :block? true ; Block thread during table creation - create-table call should not return until the table is actually active
                   })


(defrecord DynamoDB []
  ;implementando o protocolo que informa o tipo de database
 p.database/Database
  (db-type [_] :ddb)
  ;implementando o protocolo que gerencia o ciclo de vida do componente 
  component/Lifecycle
  (start [this]
    (println "⛁ Starting DDB")
    (far/ensure-table ddb-client :my-table :id)
    (assoc this :ddb ddb-client))
  
  (stop [this]
    (println  "🛑⛁ Stopping DDB")
    ;no function to discon/release ddb bcos its http call, right? if idle no probs?
    ; related? Closing connections idle longer than 60000 MILLISECONDS 
    ;tem como aumentar esse tempo?
    (dissoc this :ddb)))

;cria instancia do ddb
(defn new-dynamodb []
  (map->DynamoDB {}))
;meu Deus sempre esqueco isso! ver no comp datomic... 


;use far/ensure-table
;component should check if table exists 
;if not creates 

;component so precisa do nome da tabela e do client
;dynamodb-local  | InMemory:     false onde entao?