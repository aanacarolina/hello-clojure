(ns integration.aux.init
  "Init functions for state-flow tests."
  (:refer-clojure :exclude [run!])
  (:require [components] 
            [schema.core :as s]
            [state-flow.cljtest] ;; for defflow
            [state-flow.core :as state-flow]))

(defn init! [] 
  {:system (components/run-dev!)})

;define checagem de type tbm 
(defn- run-flow
  [flow state]
  (s/with-fn-validation
    (state-flow/run* {:init (constantly state)}
                     flow)))


(defmacro defflow
  "recebe nome do defflow e os flows - retorna codigo a ser executado para cada flow"
  [name & flows]
  `(state-flow.cljtest/defflow ~name {:init   init!
                                      :runner #'run-flow}
     ~@flows))


;~ evita que  simbolo seja executado
;flow nesse caso é um atomo - retorna um estado
;ver https://clojure.org/reference/reader#syntax-quote
;convenção: funcoes com WITH geralmente são macros

