(ns components.dynamo
  (:require [taoensso.faraday :as far]))

(def ddb-client 
  {:endpoint "http://localhost:8000"})

(far/list-tables ddb-client)


(far/create-table ddb-client :my-table
                  [:id :n]  ; Primary key named "id", (:n => number type); it is also my partition key 

                  {:range-keydef [:sort-key "" ]
                   :throughput {:read 1 :write 1} ; Read & write capacity (units/sec)
                   :block? true ; Block thread during table creation
                   })

;primary key = partition + sort key
;SABER PADROES DE ACESSO NA CRIACAO!!!! Muda tudo!
;cada partition é um serivdor
;queries can only be made by primary key 

;component should check if table exists 
;if not creates

;component so precisa do nome da tabela e do client
