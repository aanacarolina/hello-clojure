(ns wire.in.user
  (:require [schema.core :as s]))

;especificar o que vamos receber
;sanitization - TODO ler mais
;pedestal usa default interceptor que já parseia para edn, enviamos json na req
;ainda nao é o user e sim solicitação de criacao de USer
(s/defschema UserRequest
  {:name s/Str
   :surname s/Str
   :age s/Int})

;user pode ter conta, mas para criar usuario a conta nao eh obrigatorio
;(s/validate)


(s/check UserRequest
         {:name "aaa"
          :surname "bbbb"
          :age "1"})

;usado para mostrar o 
(s/explain UserRequest)