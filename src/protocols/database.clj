(ns protocols.database
  (:require [schema.core :as s]))

(defprotocol Database 
  "Funcao que retorna o tipo de database"
  (db-type [this]))

(def IDatabase (s/protocol Database))
