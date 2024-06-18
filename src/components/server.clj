(ns components.server
  (:require [com.stuartsierra.component :as component]
            [io.pedestal.interceptor :as interceptor]
            [io.pedestal.interceptor.error :as i.error]
            [components.db :as db]
            [io.pedestal.http :as http]
            [schema.core :as s]))

(defonce server (atom nil))

(defn- db-interceptor [db]
  (interceptor/interceptor {:name  :db-interceptor
                            :enter (fn [context]
                                     (update context :request assoc-in [:components :database] db))}))

#_(defn- res->json []
  (let [response-json {:name  ::response-json
                       :leave #(update-in % [:response] http/json-response)}]
    (interceptor/interceptor response-json)))

;por ser uma macro o ja vai saber lidar com os parametros, apesar da msg de erro
;http://pedestal.io/pedestal/0.6/reference/error-handling.html#_error_dispatch_interceptor
(def service-error-handler
  (i.error/error-dispatch [ctx ex]
                          [{:exception-type :clojure.lang.ExceptionInfo}]
                          (assoc ctx :response {:status 400 :body ex})
                          :else
                          (assoc ctx :response {:status 500 :body ex})))

;colocamos na entrada da rota para lidar com verificacao dos paramentros enviados na request.
(defn coerce!
  ([schema] (coerce! schema :json-params))
  ([schema param-type]
   (interceptor/interceptor {:name  :coerce-interceptor
                             :enter (fn [context]
                                      (let [param (get-in context [:request param-type])]
                                        (s/validate schema param)
                                        context))})))

;colocamos na saída da rota para lidar com verificacao dos paramentros enviados na response.
(defn externalize! [schema]
  (interceptor/interceptor {:name  :externalize-interceptor
                            :leave (fn [context]
                                     (let [body (get-in context [:response :body])]
                                       (println "???????????")
                                       (println context)
                                       (println "???????????")
                                       (s/validate schema body)
                                       context))}))

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
       (update ::http/interceptors conj service-error-handler)
       #_(update ::http/interceptors conj (res->json)))))

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

;criar restart
(defn restart [database routes]
  (stop)
  (start database routes))

;ADD INTERCEPTOR DEFAULT
;pegar do contexto (request esta dentro do contexto, mas o db NAO, entao iremos coloca-lo no contexto)
;fazer o mesmo para os outros components

(defrecord Server [database routes]
  component/Lifecycle
  (start [this]
    (println "🚀 Starting Server")
    (when @server
      (stop))
    (start database routes)
    (assoc this :server server))

  (stop [this]
    (println "🛑🚀 Stopping Server")
    (stop)
    (assoc this :server nil)))


(defn new-server []
  (map->Server {}))