(ns core.components
  (:require [com.stuartsierra.component :as component]
            [components.db :as db]
            [components.routes :as routes]
            [components.server :as server]))

;vai agir como main por enquanto no meu projeto
;system map e start aqui

(defn hello-system-map []
  (component/system-map
   :database (db/new-atomdatabase)
   :routes (routes/new-routes)
   :server (component/using (server/new-server) [:database :routes])))


(def ready-steady-go (component/start (hello-system-map))) 
;info for THIS vai estar vindo daqui
 (println ready-steady-go)

#_(def ready-steady-stop (component/stop (hello-system-map))) 

