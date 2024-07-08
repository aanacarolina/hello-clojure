(ns protocols.config
  (:require [schema.core :as s]))

(defprotocol Config
  "Funcao que retorna o tipo de configuracao de acordo com a chave passada por parametro"
  (get-value [this key]))

(def IConfig (s/protocol Config))
