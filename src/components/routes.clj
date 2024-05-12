(ns components.routes
  (:require [com.stuartsierra.component :as component]
            [io.pedestal.http.route :as route]
            [diplomat.http-server :as http-server]))


(defrecord Routes []
  component/Lifecycle
  (start [this]
    (println "🔀 Starting Routes")

    (def routes
      (route/expand-routes http-server/endpoints ))
    (assoc this :endpoints routes))

  (stop [this]
    (println  "🛑🔀 Stopping Routes")
    (assoc this :endpoints nil)))

(defn new-routes []
  (->Routes))

