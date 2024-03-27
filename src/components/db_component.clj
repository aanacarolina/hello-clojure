(ns components.db-component
  (:require [com.stuartsierra.component :as component]))

;protocolo nesse caso vai procurar pela funcao que de fato implementa a maneira com que o ciclo de vida do componente vai ser gerenciado.
(defrecord AtomDatabase []
  ;Implementa o ciclo de vida the Lifecycle protocol
   component/Lifecycle
  ;this é um mapa dos componentes que temos temos de componente. 
  (start [this] 
    (assoc this :atom-database  
           (atom {#uuid "11e735a5-feaa-458a-8c62-449ba5aa60dc" {:name "Chaves", :surname "S." :age 8}
                                       #uuid "e291f340-7e1b-4f78-9cd6-22afeb04eebc" {:name "Quico" :surname "S." :age 7}
                                       #uuid "4b96037f-f9a3-490f-99f3-5f9b32006edc" {:name "Chiquinha" :surname "S." :age 7}
                                       #uuid "fb3281be-11c4-4907-83d0-722df301032f" {:name "Seu Madruga" :surname "S." :age 42}
                                       #uuid "457d6d45-0d79-45c8-a743-892170ce356d" {:name "Dona Florinda" :surname "S." :age 35}})))
  

  (stop [this]
    (assoc this :atom-database nil))
 )


(defn new-atomdatabase []
  (map->AtomDatabase))

