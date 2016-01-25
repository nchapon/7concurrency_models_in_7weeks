(ns channels.core
  (:require [clojure.core.async :as async :refer :all
             :exclude [map into reduce merge partition partition-by take]]
            [clojure.edn :as edn]))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(defn readall!! [ch]
  (loop [coll []]
    (if-let [x (async/<!! ch)]
      (recur (conj coll x))
      coll)))

(defn writeall!! [ch coll]
  (doseq [x coll]
    (async/>!! ch x))
  (async/close! ch))

(defn go-add [x y]
  (<!! (nth (iterate #(go (inc (<! %))) (go x)) y)))



;; to-chan returns a channel containing the contents of a sequence
(defn factor? [x y]
  (zero? (mod y x)))


(defn get-primes [limit]
  (let [primes (chan)
        numbers (to-chan (range 2 limit))]
    (go-loop [ch numbers]
      (when-let [prime (<! ch)]
        (>! primes prime)
        (recur (remove< (partial factor? prime) ch))))
    (close! primes)
    primes))


(defn -main [limit]
  (let [primes (get-primes (edn/read-string limit))]
    (loop []
      (when-let [prime (<!! primes)]
        (println prime)
        (recur)))))
