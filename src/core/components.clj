(ns core.components
  (:require [com.stuartsierra.component :as component]))

;vai agir como main por enquanto no meu projeto
;system map e start aqui

(defn hello-system-map []
  (component/system-map 
   :database (database/new-database)
   :routes (routes/new-routes)
   :webapp (component/using (webapp/new-webapp) [:config :database :routes])
   :pedestal (component/using (component.pedestal/new-pedestal) [:config :database :routes :webapp])))


(defn main [] (component/start (hello-system-map)))
