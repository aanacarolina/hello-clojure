(ns components.server
  (:require [com.stuartsierra.component :as component]
            [io.pedestal.http :as http]))


(defrecord Server [db routes]
  component/Lifecycle

  (start [this]
    (println "🚀 Starting Server")

    (defonce server (atom nil))
    
    (defn create-server []
      (http/create-server
       {::http/routes (:endpoints routes)
        ::http/type :jetty
        ::http/port 7171
        ::http/join? false}))

    (defn start []
      (try
        (reset! server (http/start (create-server)))
        (catch Exception e
          e)))
    (defn stop []
      (try
        (http/stop @server)
        (catch Exception e
          e)))

    (start)
    (assoc this :server nil))

  (stop [this]
    (println "🛑🚀 Stopping Server")
        (stop)
    (assoc this :server nil)))


(defn new-server []
  (map->Server {}))