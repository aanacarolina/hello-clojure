(ns components
  (:require [com.stuartsierra.component :as component]
            [components.db :as db]
            [components.datomic :as datomic]
            [components.routes :as routes]
            [components.server :as server]))

;vai agir como main por enquanto no meu projeto
;system map e start aqui

(defn hello-system-map []
  (component/system-map
   :database (datomic/new-datomic-db)
   :routes (routes/new-routes)
   :server (component/using (server/new-server) [:database :routes])))

;TODO: restart server aqui, na vdd - verificar se server ja existe por conta da porta

(defn ready-steady-go []
  (component/start (hello-system-map)))

#_(components.server/restart
   (:database (db/new-atomdatabase)) (:routes (routes/new-routes)))


;info for THIS vai estar vindo daqui, que foi adicionada pela funcao acima system-map, ai aqui foi de fato statarda

;(println (:database ready-steady-go))

#_(def ready-steady-stop (component/stop (hello-system-map))) 

