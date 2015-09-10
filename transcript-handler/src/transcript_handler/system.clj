(ns transcript-handler.system
  (:require [org.httpkit.server :refer [run-server]]
            [com.stuartsierra.component :as component]
            [transcript-handler.web :refer :all]))

(defn- start-server [handler port]
  (let [server (run-server handler {:port port})]
    (println (str "Start server on port " port))
    server))

(defn- stop-server [server]
  (when (server)
    (server))) ;; run-server returns a function that stops itself

(defrecord TranscriptHandler []
    component/Lifecycle
    (start [this]
      (assoc this :server (start-server #'app 3000)))

    (stop [this]
      (stop-server (:server this))
      (dissoc this :server)))

(defn create-system []
  (TranscriptHandler.))


(defn -main [& args]
  (start-server #'app 3000))
