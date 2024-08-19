(ns wire.in.user
  (:require [schema.core :as s])
  (:import [java.util UUID]))

;especificar o que vamos receber
;sanitization - TODO ler mais
;pedestal usa default interceptor que já parseia para edn, enviamos json na req
;ainda nao é o user e sim solicitação de criacao de USer
;user pode ter conta, mas para criar usuario a conta nao eh obrigatorio
(s/defschema UserRequest
  {:name s/Str
   :surname s/Str
   :age s/Int})


(s/defschema HelloRequest
  (s/maybe {(s/optional-key :name) s/Str}))

(s/defschema UserById
  {:id s/Uuid})

;gera exception, se for o caso
(s/validate UserRequest
            {:name "aaa"
             :surname "bbbb"
             :age 1})


;nao gera exception apenas mostra que nao é igual, no caso.
(s/check UserRequest
         {:name "aaa"
          :surname "bbbb"
          :age 1})


(s/check UserById 
         {:id #uuid "b6883c0a-0342-4007-9966-bc2dfa6b109e"})

;Printa o schema definido 
(s/explain UserRequest)
;(s/validate UserById {:id "b6883c0a-0342-4007-9966-bc2dfa6b109e"})
