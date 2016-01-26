(ns polling.core
  (:require [clojure.core.async :as async :refer :all :exclude [map into reduce merge partition partition-by take]]))




(defn poll-fn
  "doc-string"
  [interval action]
  (let [seconds (* 1000 interval)]
    (go (while true)
        (action)
        (<! (timeout seconds)))))


(def c (to-chan (iterate inc 0)))


(poll-fn 10 #(println "Read " (<!! c)))











