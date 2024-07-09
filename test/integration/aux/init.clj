(ns integration.aux.init
   (:require [schema.core :as s]
             [state-flow.api]
             [state-flow.helpers.runners :as runners]
             [components]))
  
  ;redefines
  (defn init! []
    (s/with-fn-validation
      {:system (components/run-dev!)}))
  
  (defmacro defflow
    [name & forms]
    (let [default-parameters   {:init       init!
                                :runner     runners/run-with-fn-validation}
          [parameters & flows] (if (map? (first forms))
                                 (let [[override & rem] forms]
                                   (cons (merge default-parameters override) rem))
                                 (cons default-parameters forms))]
      `(state-flow.api/defflow ~name ~parameters ~@flows)))