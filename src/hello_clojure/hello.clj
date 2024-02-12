(ns hello-clojure.hello
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]))

(defn respond-hello [request]
  (let [user-name (get-in request [:query-params :name])]
    (if user-name
      {:status 200 :body (str "Hello, " user-name "\n")}
      {:status 200 :body (str "Hello, stranger\n")})))

(def all-user )

(defn all-users [request]
  {:status 200
   :body (str "List of all users: " (get-in request [:path-param :users])  "\n")})

(defn create-user [request]
 {:status 200 :body (str "create user")}
  )


(defn user-by-id [request]
  (if (get-in request [:path-param :id]) 
    {:status 200 :body "user by id placeholder"}
   {:status 400 :body (str "Hellouser by id")}))

(defn put-user [request]
  {:status 200 :body (str "put user")})

(defn delete-user [request]
  {:status 200 :body (str "delete user")})

(def routes
  (route/expand-routes
   #{["/hello" :get respond-hello :route-name :hello]
     ["/users" :get all-users :route-name :users]
     ["/user" :post create-user :route-name :create-users] 
     ["/user/{:id}" :get user-by-id :route-name :user-by-id]
     ["/user/{:id}" :put put-user :route-name :put-user]
     ["/user/{:id}" :delete delete-user :route-name :delete-user]}))

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