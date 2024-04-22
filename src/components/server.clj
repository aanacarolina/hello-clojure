(ns components.server
  (:require [com.stuartsierra.component :as component]
            [io.pedestal.interceptor :as interceptor]
            [components.db :as db]
            [io.pedestal.http :as http]
            [clojure.data.json :as json]))

(defonce server (atom nil))

(defn- db-interceptor [db]
  (interceptor/interceptor {:name  :db-interceptor
                            :enter (fn [context]
                                     (update context :request assoc-in [:components :db] db))}))
(defn- parse-json
  [response]
  (-> response
      (update :body json/write-str)
      (assoc-in [:headers "Content-Type"] "Application/json")))

(defn- res->json []
  (let [response-json {:name  ::response-json
                       :leave #(update-in % [:response] parse-json)}]
    (interceptor/interceptor response-json)))

;aqui estamos criando nosso service map 
;se eu nao usar o UPDATE no ::http/interceptors eu irei sobrescrever todos os outros interceptors 
(defn- create-server [database routes]
  (http/create-server
   (-> {::http/routes (:endpoints routes)
        ::http/type :jetty
        ::http/port 7171
        ::http/join? false}
       http/default-interceptors
       (update ::http/interceptors conj (db-interceptor database))
       (update ::http/interceptors conj (res->json)))))

(defn- stop []
  (try
    (http/stop @server)
    (catch Exception e
      e)))
;tratar erro = try/catch captura, mas nao handles 
;pega a Excepetion mais alta na hierarquia na arvore das excessoes (runtime exception, etc)

;defn- private == 
;private functions help with isolating

(defn- start [database routes]
  (try
    (reset! server (http/start (create-server database routes)))
    (catch Exception e
      e)))

;ADD INTERCEPTOR DEFAULT
;pegar do contexto (request esta dentro do contexto, mas o db NAO, entao iremos coloca-lo no contexto)
;fazer o mesmo para os outros components

(defrecord Server [database routes]
  component/Lifecycle
  (start [this]
    (println "🚀 Starting Server")
    (start database routes)
    (assoc this :server nil))

  (stop [this]
    (println "🛑🚀 Stopping Server")
    (stop)
    (assoc this :server nil)))


(defn new-server []
  (map->Server {}))