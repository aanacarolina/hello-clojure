(ns components.dynamo
  (:require [taoensso.faraday :as far]
            [protocols.database :as p.database]
            [com.stuartsierra.component :as component]))

;on tutorial symbol is client-opts
(def ddb-client 
  {:endpoint "http://localhost:8000"})

;manual creation
#_(far/create-table ddb-client :my-table
                  [:id :n]  ; Primary key named "id", (:n => number type); it is also my partition key - a.k.a. hash-keydef

                  {:range-keydef [:sort-key :s ] ;a.k.a sort-key
                   :throughput {:read 1 :write 1} ; Read & write capacity (units/sec)
                   :block? true ; Block thread during table creation - create-table call should not return until the table is actually active
                   })
(comment
  (far/delete-table ddb-client :my-table)

  (far/get-item ddb-client :my-table {:user/id "123" :user/name "carol"})
  (far/scan ddb-client :my-table)

  (far/put-item ddb-client :my-table {:user/name "faraday" :user/surname "cage" :user/id "222" :user/age 35} {:return :all-old})

  ;lazy-seq?
(def list-tables (far/list-tables ddb-client))
;(println (do list-tables))

;almost same return of list, but shows indexes info
(far/describe-table ddb-client :my-table)
  )

(defrecord DynamoDB []
  ;implementando o protocolo que informa o tipo de database
 p.database/Database
  (db-type [_] :ddb)
  ;implementando o protocolo que gerencia o ciclo de vida do componente 
  component/Lifecycle
  (start [this]
    (println "⛁ Starting DDB")
    (far/ensure-table ddb-client :my-table [:user/id :s] {:range-keydef [:user/name :s] 
                                                     :throughput {:read 1 :write 1}
                                                     :block? true 
                                                     })
    (assoc this :ddb {:client-ops ddb-client :table :my-table }))
  
  (stop [this]
    (println  "🛑⛁ Stopping DDB")
    ;no function to discon/release ddb bcos its http call, right? if idle no probs?
    ; related? Closing connections idle longer than 60000 MILLISECONDS 
    ;tem como aumentar esse tempo?
    (dissoc this :client-ops)))

;cria instancia do ddb
(defn new-dynamodb []
  (map->DynamoDB {}))
;meu Deus sempre esqueco isso! ver no comp datomic... 


;use far/ensure-table
;component should check if table exists 
;if not creates 

;component so precisa do nome da tabela e do client
;dynamodb-local  | InMemory:     false onde entao?