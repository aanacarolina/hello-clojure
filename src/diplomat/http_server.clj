(ns diplomat.http-server
  (:require
   [io.pedestal.http.body-params :as body-params]
   [controllers.user :as user]
   [diplomat.http-in.user :as d.http-in.user]
   [components.server :as c.server]
   [wire.in.user :as w.in.user]
   [wire.out.user-response :as w.out.user]))

(def common-interceptors [(body-params/body-params)])

(def endpoints #{["/hello"
                  :get
                  (conj [(c.server/coerce! w.in.user/HelloRequest :query-params)]
                        d.http-in.user/respond-hello)
                  :route-name :hello]

                 ["/users"
                  :get
                  (conj []
                        d.http-in.user/all-users
                        #_(c.server/externalize! w.out.user/AllUsersResponse))
                  :route-name :all-users]

                 ["/users"
                  :post (conj common-interceptors
                              (c.server/coerce! w.in.user/UserRequest)
                              d.http-in.user/create-user!
                              (c.server/externalize! w.out.user/UserResponse))
                  :route-name :create-user]

                 ["/users/:id"
                  :put (conj common-interceptors
                             user/update-user!)
                  :route-name :update-user]

                 ["/users/:id"
                  :get
                  (conj [(c.server/coerce-path-uuid! w.in.user/UserById)]
                        d.http-in.user/user-by-id
                        (c.server/externalize! w.out.user/UserResponse))
                  :route-name :user-by-id]
                 
                 ["/users/q"
                  :get
                  (conj []
                        user/query-user)
                  :route-name :query-user-by-id]

                 ["/users/:id"
                  :delete
                  (conj [(c.server/coerce-path-uuid! w.in.user/UserById)]
                        d.http-in.user/delete-user!)
                  :route-name :delete-user]

                 
                 
                 ;accounts starts here
                        ;["/accounts/:user-id" :post (conj common-interceptors hello/create-account!) :route-name :create-account]
                        ;["/user/account/:user-id" :get hello/user-accounts :route-name :user-accounts]
                    ;need to make deposit to have a history - to display the amount just change route above with :amount instead of :type 
                 #_["/users/:user-id/account/:account-id/type" :get user-deposits-by-account :route-name :user-deposits-by-account]})

