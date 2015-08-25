(ns chap03.core
  (:require [clojure.core.reducers :as r]))

;; 7-concurrency
(def numbers (into [] (range 0 10000000)))

(defn parallel-sum
  [numbers]
  (r/fold + numbers))

(defn sum
  [numbers]
  (reduce + numbers))

(defn word-frequencies
  "Word frequencies custom implem"
  [words]
  (reduce
   (fn [counts word] (assoc counts word (inc (get counts word 0))))
   {}
   words))


(def p-pages ["one potato two potato three potato four"
            "five potato six potato seven potato more"])

(defn get-words [text]
  (re-seq #"\w+" text))

(defn count-words-parallel
  "doc-string"
  [pages]
  (reduce
   (partial merge-with +)
   (pmap #(frequencies (get-words %)) pages)))
