
(ns diplomat.http-server
  (:require
   [io.pedestal.http.body-params :as body-params]
   [controllers.user :as user]
   [diplomat.http-in.user :as d.http-in.user]))

(def common-interceptors [(body-params/body-params)])

(def endpoints #{["/hello" 
                  :get user/respond-hello 
                  :route-name :hello] 
                 
                 ["/users" 
                  :get user/all-users 
                  :route-name :users]
                 
                 ["/users" 
                  :post (conj common-interceptors 
                              d.http-in.user/create-user!) 
                  :route-name :create-user]
                 
                 ["/users/:id" 
                  :get user/user-by-id 
                  :route-name :user-by-id]
                 
                 ["/users/q" 
                  :get user/query-user 
                  :route-name :query-user-by-id]
                 
                 ["/users/:id" 
                  :put (conj common-interceptors 
                             user/update-user!) 
                  :route-name :update-user]
                 
                 ["/users/:id" 
                  :delete user/delete-user! 
                  :route-name :delete-user]
                 
                 ;accounts starts here
                        ;["/accounts/:user-id" :post (conj common-interceptors hello/create-account!) :route-name :create-account]
                        ;["/user/account/:user-id" :get hello/user-accounts :route-name :user-accounts]
                    ;need to make deposit to have a history - to display the amount just change route above with :amount instead of :type 
                 #_["/users/:user-id/account/:account-id/type" :get user-deposits-by-account :route-name :user-deposits-by-account]})

