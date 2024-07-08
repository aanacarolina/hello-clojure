(ns components.config
  (:require [com.stuartsierra.component :as component]
            [clojure.java.io :as io]
            [clojure.edn :as edn]
            [protocols.config :as c-pro]))


(defn read-config [env]
  (let [config-file-name (format "config-%s.edn" env)
        data-file (io/resource config-file-name)
        string-edn (slurp data-file)]
    (edn/read-string string-edn)))

(defrecord Config [env]
  ;implementando o protocolo que informa a configuracao a ser seguida
  c-pro/Config
  (get-value [this key] (get-in this [:config key]))

  component/Lifecycle
  (start [this]
         (println "🔀 Starting Config")
         (assoc this :config (read-config env)))

  (stop [this]
        (println  "🛑 Stopping Config")
        (assoc this :config nil)))

(defn new-config [env]
  (->Config env))