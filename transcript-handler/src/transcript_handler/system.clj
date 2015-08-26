(ns transcript-handler.system
  (:require [org.httpkit.server :refer [run-server]]))


(defn app [req]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "Hello HTTP!"})

(defn -main [& args]
  (run-server app {:port 8080}))
