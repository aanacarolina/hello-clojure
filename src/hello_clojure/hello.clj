(ns hello-clojure.hello
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [io.pedestal.test :as test]))

(defn generate-uuid []
  (java.util.UUID/randomUUID))

(def users (atom {1 {:name "Bart" :surname "Simpson" :age 10}
                  2 {:name "Lisa" :surname "Simpson" :age 8}
                  3 {:name "Selma" :surname "Bouvier" :age 46}}))

(defn respond-hello [request]
  (let [user-name (get-in request [:query-params :name])]
    (if user-name
      {:status 200 :body (str "Hello, " user-name "\n")}
      {:status 200 :body (str "Hello, stranger\n")})))

(defn all-users [request]
  {:status 200
   :body (str "List of all users: " (vals @users))})

(defn create-user [request]
  (let [;uuid (ramdon-uuid)
        name (get-in request [:query-params :name])
        surname (get-in request [:query-params :surname])
        age  (get-in request [:query-params :age])]
    (swap! users assoc (inc (key (last @users))) {:name name :surname surname :age age})
    {:status 201 :body (str "new user created" (last @users))}))

(defn user-by-id
    [request]
    (let [user-id (Integer/parseInt (get-in request [:path-params :id]))
          user-not-found {:status  404
                          :headers {"Content-Type" "text/plain"}
                          :body    "User not found"}]
      (if (not (contains? @users user-id))
        user-not-found
        {:status 200 :body (@users user-id)})))

(defn put-user! [request]
  {:status 200 :body (str "put user")})

(defn delete-user [request]
  (let [user-id (Integer/parseInt (get-in request [:path-params :id]))]
    (swap! users dissoc user-id)
    {:status 200 :body (str "User id # " user-id ": deleted!")}))


(def routes
  (route/expand-routes
   #{["/hello" :get respond-hello :route-name :hello]
     ["/users" :get all-users :route-name :users]
     ["/users" :post create-user :route-name :create-user]
     ["/users/:id" :get user-by-id :route-name :user-by-id]
     ["/users/:id" :put put-user! :route-name :put-user]
     ["/users/:id" :delete delete-user :route-name :delete-user]}))

(defn create-server []
  (http/create-server
   {::http/routes routes
    ::http/type :jetty
    ::http/port 7171
    ::http/join? false}))

(defonce server (atom nil))

(defn test-request [verb url]
  (test/response-for (::http/service-fn @server) verb url))

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

  (reset-server)

  (println (test-request :post "/users?name=Fulaninho&surname=DeTal&age=4"))
  (println (test-request :get "/users"))
  (println (test-request :get "/users/20"))
  (println (test-request :delete "/users/1"))
  (test-request :get "/hello")
  @users

  )