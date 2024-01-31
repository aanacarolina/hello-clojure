(ns hello-clojure.hello
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]))

(defn respond-hello [request]
  (let [user-name (get-in request [:query-params :name])]
    (if user-name
      {:status 200 :body (str "Hello, " user-name "\n")}
      {:status 200 :body (str "Hello, stranger\n")})))

(def routes
  (route/expand-routes
   #{["/hello" :get respond-hello :route-name :hello]}))

(defn create-server []
  (http/create-server
   {::http/routes routes
    ::http/type :jetty
    ::http/port 7171
    ::http/join? false});took me ages to find about this!
  )

(defonce server (atom nil))

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

(defn reset-server []
  (stop)
  (start))

(comment
  (start)
  (stop)
  (reset-server))