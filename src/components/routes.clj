(ns components.routes
  (:require [com.stuartsierra.component :as component]
            [io.pedestal.http.route :as route]
            [core.http-server :as endpoints]))

;

(defrecord Routes []
  component/Lifecycle
  (start [this]
    (println "🔀 Starting Routes")

    (def routes
      (route/expand-routes endpoints/endpoints))
    (assoc this :endpoints routes))

  (stop [this]
    (println  "🛑🔀 Stopping Routes")
    (assoc this :endpoints nil)))

(defn new-routes []
  (->Routes))

