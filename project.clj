(defproject hello-clojure "0.0.1-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  ;para compilacao          
  :dependencies [[org.clojure/clojure "1.11.0"]
                 [io.pedestal/pedestal.service "0.6.3"]
                 [io.pedestal/pedestal.jetty "0.6.3"] ;servidor web que roda na JVM
                 [ch.qos.logback/logback-classic "1.2.10" :exclusions [org.slf4j/slf4j-api]]
                 [org.slf4j/jul-to-slf4j "1.7.35"] ;https://lambdaisland.com/blog/2020-06-12-logging-in-clojure-making-sense-of-the-mess
                 [org.slf4j/jcl-over-slf4j "1.7.35"]
                 [org.slf4j/log4j-over-slf4j "1.7.35"]
                 [com.stuartsierra/component "1.1.0"]
                 [prismatic/schema "1.4.1"]
                 [org.clojure/data.json "2.5.0"]
                 ]
  :min-lein-version "2.0.0"
  :resource-paths ["config", "resources"] 
  :profiles {:dev {:aliases {"run-dev" ["trampoline" "run" "-m" "hello-clojure.server/run-dev"]}
                   :dependencies [[io.pedestal/pedestal.service-tools "0.6.3"]]}
             :uberjar {:aot [hello-clojure.hello/start]}}
  :main ^{:skip-aot true} core.components/ready-steady-go)
