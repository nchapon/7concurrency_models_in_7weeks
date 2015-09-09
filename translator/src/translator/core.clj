(ns translator.core
  (:require [clojure.string :as str]
            [com.stuartsierra.component :as component]
            [compojure.core :refer :all]
            [org.httpkit.server :refer [run-server]]
            [ring.util.response :refer [response]]))

(def translations {
  "Twas brillig, and the slithy toves Did gyre and gimble in the wabe:"
    "Il brilgue, les tôves lubricilleux Se gyrent en vrillant dans le guave:"
  "All mimsy were the borogoves, And the mome raths outgrabe."
    "Enmîmés sont les gougebosqueux Et le mômerade horsgrave."
  "Beware the Jabberwock, my son!"
    "Garde-toi du Jaseroque, mon fils!"
  "The jaws that bite, the claws that catch!"
    "La gueule qui mord; la griffe qui prend!"
  "Beware the Jubjub bird, and shun The frumious Bandersnatch!"
    "Garde-toi de l'oiseau Jube, évite Le frumieux Band-à-prend!"
  "He took his vorpal sword in hand:"
    "Son glaive vorpal en main"
  "Long time the manxome foe he sought;"
    "Il va-T-à la recherche du fauve manscant;"
  "So rested he by the Tumtum tree, And stood awhile in thought."
    "Puis arrivé à l'arbre Té-Té, Il y reste, réfléchissant."
  "And, as in uffish thought he stood, The Jabberwock, with eyes of flame, Came whiffling through the tulgey wood, And burbled as it came!"
    "Pendant qu'il pense, tout uffusé, Le Jaseroque, à l'oeil flambant, Vient siblant par le bois tullegeais, Et burbule en venant."
  "One, two!"
    "Un deux!"
  "And through and through The vorpal blade went snicker-snack!"
    "Par le milieu, Le glaive vorpal fait pat-à-pan!"
  "He left it dead, and with its head He went galumphing back."
    "La bête défaite, avec sa tête, Il rentre gallomphant."
  "And, has thou slain the Jabberwock?"
    "As-tu tué le Jaseroque?"
  "Come to my arms, my beamish boy!"
    "Viens à mon coeur, fils rayonnais!"
  "O frabjous day!"
    "Ô Jour frabbejeais!"
  "Callooh!"
    "Calleau!"
  "Callay!"
    "Callai!"
  "He chortled in his joy."
    "Il cortule dans sa joie."
  "One potato, two potato, three potato, four, Five potato, six potato, seven potato, more."
    "Une pomme de terre, pomme de terre deux, trois pomme de terre, quatre, Cinq pomme de terre, pomme de terre six, sept pomme de terre, plus."})


(defroutes app
  (POST "/translate" [:as {:keys [body]}]
        (response (get translations (str/trim (slurp body)) "Untranslateable")))
  (POST "/test" {:keys [body]}
        (response (slurp body)))
  (GET "/test/:foo" [foo] ;; path param
        (response (str "Foo = " foo))))



(defn- start-server [handler port]
  (let [server (run-server handler {:port port})]
    (println (str "Start server on port " port))
    server))

(defn- stop-server [server]
  (when (server)
    (server))) ;; run-server returns a function that stops itself

(defrecord Translator []
    component/Lifecycle
    (start [this]
      (assoc this :server (start-server #'app 3001)))

    (stop [this]
      (stop-server (:server this))
      (dissoc this :server)))

(defn create-system []
  (Translator.))

(defn -main [& args]
  (start-server #'app 3001))
