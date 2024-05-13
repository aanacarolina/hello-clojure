(ns components.db
  (:require [com.stuartsierra.component :as component]))

;protocolo nesse caso vai procurar pela funcao que de fato implementa a maneira com que o ciclo de vida do componente vai ser gerenciado.
(defrecord AtomDatabase []
  ;Implementa o ciclo de vida the Lifecycle protocol
  component/Lifecycle
  ;this é um mapa dos componentes que temos temos do system 
  (start [this]
    (println "🛢️ Starting Atom-DB")
    (assoc this :atom-database
           (atom {#uuid "11e735a5-feaa-458a-8c62-449ba5aa60dc" {:name "Chaves", :surname "S." :age 8 
                                                                :accounts [
                                                                           {:account-uuid #uuid "f061f807-fd80-4536-9242-7d084a6c2b81", :status "Active", :type "checking", :amount 0} 
                                                                           {:account-uuid #uuid "0f17da9d-9a0c-4332-9b7f-9a7f965f0299", :status "Active", :type "savings", :amount 0}]}
                  #uuid "e291f340-7e1b-4f78-9cd6-22afeb04eebc" {:name "Quico" :surname "S." :age 7}
                  #uuid "4b96037f-f9a3-490f-99f3-5f9b32006edc" {:name "Chiquinha" :surname "S." :age 7}
                  #uuid "fb3281be-11c4-4907-83d0-722df301032f" {:name "Seu Madruga" :surname "S." :age 42}
                  #uuid "457d6d45-0d79-45c8-a743-892170ce356d" {:name "Dona Florinda" :surname "S." :age 35}})))

  (stop [this]
    (println "🛑🛢️ Stopping Atom-DB")
    (assoc this :atom-database nil)))

;factory method - cria instancias
(defn new-atomdatabase []
  (->AtomDatabase))

;-> ==  .  == new - criando uma nova instancia do Record

;opcional, mas é util pois podemos ter varias maneiras de criar um record de DB.
;alem de ser melhor para criar um unico ponto para o record - que centralizar o comportamento.


(comment 
  (defn delivery-date-by-source 
           [number]
           (when (> number 5)
             (cond
               (= number 6) (println "[:card/delivery-problem :sent-at]")
               (= number 7) (println "[:card/card-tracking :delivery-problem-solution :sent-at]")
               :else  (println ":issued-at"))))
  
  (delivery-date-by-source 1)
  (delivery-date-by-source 6)
  (delivery-date-by-source 7)
  (delivery-date-by-source 10)

  )