(ns chap03.parallel-frequencies
  (:require [clojure.core.reducers :as r]))

(defn parallel-frequencies
  "Parallel with fold"
  [coll]
  (r/fold
   (partial merge-with +)
   (fn [counts x] (assoc counts x (inc (get counts x 0))))
   coll))
