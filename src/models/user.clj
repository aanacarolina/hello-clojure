(ns models.user
  (:require [schema.core :as s]))

(s/defschema UserName "documentacao"
  (s/String name))

(s/defschema UserSurname 
  (s/String surname))

(s/defschema UserAge 
  (s/Int age))

(s/defschema UserUUID 
  (s/UUID user-uuid))

(s/defschema UserAccountUUID 
  (s/UUID account-uuid))

(s/defschema UserAccountStatus
    (s/enum :active :inactive :cancelled))
  
  (s/defschema UserAccountType
    (s/enum :checking :savings))
  
  (s/defschema UserAccountAmount
    (s/Int amount))

  (def user-skeleton 
    {:user/name {:schema UserName :required true :doc "From :json-params"}
     :user/surname {:schema UserSurname :required true :doc "From :json-params"}
     :user/age {:schema UserAge :required true :doc "From :json-params"}
     :user/uuid {:schema UserUUID :required true :doc "From :path-params"}
     :user/account-uuid {:schema UserAccountUUID :required true :doc "From :json-params"}
     :user/account-status {:schema UserAccountStatus :required true :doc "From :json-params"}
     :user/account-type {:schema UserAccountType :required true :doc "From :json-params"}
     :user/account-amount {:schema UserAccountAmount :required true :doc "From :json-params"}})
    
     

