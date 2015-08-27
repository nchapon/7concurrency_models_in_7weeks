(ns transcript-handler.system
  (:require [org.httpkit.server :refer [run-server]]
            [com.stuartsierra.component :as component]))



(defn- start-server [handler port]
  (let [server (run-server handler {:port port})]
    (println (str "Start server on port " port))
    server))

(defn- stop-server [server]
  (when (server)
    (server))) ;; run-server returns a function that stops itself


(defn app [req]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "Hello Transcript !"})


(defrecord TranscriptHandler []
    component/Lifecycle
    (start [this]
      (assoc this :server (start-server #'app 9009)))

    (stop [this]
      (stop-server (:server this))
      (dissoc this :server)))


(defn create-system []
  (TranscriptHandler.))
