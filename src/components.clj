(ns components
  (:require [com.stuartsierra.component :as component]
            [components.db :as db]
            [components.datomic :as datomic]
            [components.dynamo :as ddb]
            [components.routes :as routes]
            [components.server :as server]
            [components.config :as config]
            [io.pedestal.http.route :as route]
            [protocols.config :as c-pro]))

;vai agir como main por enquanto no meu projeto
;system map e start aqui

(defn hello-system-map [initial-state]
  (component/system-map
   :config (config/new-config (:env initial-state "local"))
   :routes (routes/new-routes)
   :database (component/using (datomic/new-datomic-db) [:config])
  ;:database (component/using (ddb/new-dynamodb))
   :server (component/using (server/new-server) [:database :routes])))

(defn ready-steady-go!
  ([] (ready-steady-go! {}))
  ([initial-state]
   (component/start (hello-system-map initial-state))))

(defn run-local! [](ready-steady-go!))
(defn run-dev! [](ready-steady-go! {:env "test"}))


(comment 
  (run-local!)
  (run-dev!) 
  

  (def c (:config (run-dev!)))
  (c-pro/get-value c :database-uri)
  )


#_(components.server/restart
   (:database (db/new-atomdatabase)) (:routes (routes/new-routes)))


;info for THIS vai estar vindo daqui, que foi adicionada pela funcao acima system-map, ai aqui foi de fato statarda

;(println (:database ready-steady-go))

#_
(def ready-steady-stop (component/stop (hello-system-map))) 

