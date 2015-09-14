(ns tournament-server.core
  (:require [compojure.core :refer :all]
            [ring.util.response :refer [response status]]
            [cheshire.core :as json]))


(def players (atom []))

(defn list-players
  []
  (response (json/encode @players)))

(defn create-player
  "doc-string"
  [player-name]
  (swap! players conj player-name)
  (status (response "") 201))

(defroutes app
  (GET "/players" []
       (list-players))
  (PUT "/players/:player-name" [player-name]
       (create-player player-name)))
