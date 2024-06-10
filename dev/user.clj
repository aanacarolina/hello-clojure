(ns user 
  (:require [components.datomic :as datomic]
            [datomic.api :as d]
            [db.user ]))

  (comment

    ;nao tem problema conectar no transactor q a aplicacao está usando
    (def conn (d/connect "datomic:dev://localhost:4334/hello"))
    (def snapshot (d/db conn))
    ;Precisamos entender o retorno -> https://docs.datomic.com/query/query-data-reference.html#find-specs
    (db.user/query-datomic-by-id (parse-uuid "761544f5-4ab2-44a8-9f68-42768db815dd") snapshot) 

  ;pull returns an array of arrays, but if we change the pattern https://docs.datomic.com/query/query-pull.html#pull-pattern-grammar

;(d/pull db '[:artist/name :artist/startYear] led-zeppelin)
#_[[{:db/id 17592186045466,
     :user/name "Thales",
     :user/surname "Martins",
     :user/age 38,
     :user/id #uuid "aaf24722-b776-4de6-92bf-2e074d800bdf"}]]  
    ;query with first
    #_{:db/id 17592186045466,
 :user/name "Thales",
 :user/surname "Martins",
 :user/age 38,
 :user/id #uuid "aaf24722-b776-4de6-92bf-2e074d800bdf"}
;query without first
#_[{:db/id 17592186045466,
  :user/name "Thales",
  :user/surname "Martins",
  :user/age 38,
  :user/id #uuid "aaf24722-b776-4de6-92bf-2e074d800bdf"}]
    )
