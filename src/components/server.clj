(ns components.server
  (:require [com.stuartsierra.component :as component]
            [io.pedestal.http :as http]))

(defonce server (atom nil))

(defn- create-server [routes]
  (http/create-server
   {::http/routes (:endpoints routes)
    ::http/type :jetty
    ::http/port 7171
    ::http/join? false
    ;::http/interceptors ADD 
    }))

(defn- stop []
  (try
    (http/stop @server)
    (catch Exception e
      e)))
;tratar erro = try/catch captura, mas nao handles 
;pega a Excepetion mais alta na hierarquia na arvore das excessoes (runtime exception, etc)

;defn- private == 
;private functions help with isolating

(defn- start [routes]
  (try
    (reset! server (http/start (create-server routes)))
    (catch Exception e
      e)))

;ADD INTERCEPTOR DEFAULT
;pegar o contexto (request esta dentro do contexto, mas o db NAO, entao iremos coloca-lo no contexto)
;fazer o mesmo para os outros components

(defrecord Server [database routes]
  component/Lifecycle
  (start [this]
         (println "🚀 Starting Server")
         (start routes)
         (assoc this :server nil))

  (stop [this]
    (println "🛑🚀 Stopping Server")
    (stop)
    (assoc this :server nil)))


(defn new-server []
  (map->Server {}))