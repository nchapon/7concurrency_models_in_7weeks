(ns wordcount.core
  (:require [org.httpkit.client :as http]
            [clojure.core.async :as async :refer :all :exclude [map into reduce merge partition partition-by take]]))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))


(defn http-get
  "doc-string"
  [url]
  (let [ch chan]
    (http/get url (fn [response]
                    (if (= 200 (:status response))
                      (put! ch response))))
    ch))
