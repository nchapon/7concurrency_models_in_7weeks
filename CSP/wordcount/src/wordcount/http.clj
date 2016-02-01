(ns wordcount.http
  (:require [org.httpkit.client :as http]
            [clojure.core.async :as async :refer :all :exclude [map into reduce merge partition partition-by take]])
  (:import [java.net URL]))


(defn report-error
  "doc-string"
  [response]
  (println "Error " (:status response) "retrieving URL " (get-in response [:opts :url])))

(defn http-get
  "doc-string"
  [url]
  (let [ch (chan)]
    (http/get url (fn [response]
                    (if (= 200 (:status response))
                      (put! ch response)
                      (do (report-error response) (close! ch)))))
    ch))
