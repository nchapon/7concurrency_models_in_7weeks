(ns polling.core
  (:require [clojure.core.async :as async :refer :all :exclude [map into reduce merge partition partition-by take]]))


(defn poll-fn
  "doc-string"
  [interval action]
  (let [seconds (* 1000 interval)]
    (go (while true
          (action)
          (<! (timeout seconds))))))



(defmacro poll [interval & body]
  `(let [seconds# (* ~interval 1000)]
     (go (while true
           (do ~@body)
           (<! (timeout seconds#))))))



(comment (poll 10
                    (println "Polling at :" (System/currentTimeMillis))
                    (println (<! ch))))
