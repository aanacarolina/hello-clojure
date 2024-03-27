(ns core.server
  (:require 
   [io.pedestal.http :as http]
   [core.hello :as hello]
    [io.pedestal.http.route :as route]
   [io.pedestal.http.body-params :as body-params]))


(def common-interceptors [(body-params/body-params)])
;duvida: criar um outro def? entity-routes e add to ::http/routes
(def routes
  (route/expand-routes
   #{["/hello" :get hello/respond-hello :route-name :hello]
     ["/users" :get hello/all-users :route-name :users]
     ["/users" :post (conj common-interceptors hello/create-user!) :route-name :create-user]
     ["/users/:id" :get hello/user-by-id :route-name :user-by-id]
     ["/users/q" :get hello/query-user :route-name :query-user-by-id]
     ["/users/:id" :put (conj common-interceptors hello/update-user!) :route-name :update-user]
     ["/users/:id" :delete hello/delete-user! :route-name :delete-user]
     ;accounts starts here
     ["/accounts/:user-id" :post (conj common-interceptors hello/create-account!) :route-name :create-account]
     ["/user/account/:user-id" :get hello/user-accounts :route-name :user-accounts]
     ;need to make deposit to have a history - to display the amount just change route above with :amount instead of :type 
     #_["/users/:user-id/account/:account-id/type" :get user-deposits-by-account :route-name :user-deposits-by-account]}))


(defn create-server []
  (http/create-server
   {::http/routes routes
    ::http/type :jetty
    ::http/port 7171
    ::http/join? false}))

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