(ns tournament-server.system
  (:require [com.stuartsierra.component :as component]
            [tournament-server.core :refer :all]
            [org.httpkit.server :refer [run-server]]))

(defn- start-server [handler port]
  (let [server (run-server handler {:port port})]
    (println (str "Start server on port " port))
    server))

(defn- stop-server [server]
  (when (server)
    (server))) ;; run-server returns a function that stops itself

(defrecord TournamentServer []
    component/Lifecycle
    (start [this]
      (assoc this :server (start-server #'app 4001)))

    (stop [this]
      (stop-server (:server this))
      (dissoc this :server)))

(defn create-system []
  (TournamentServer.))

(defn -main [& args]
  (start-server #'app 4001))
