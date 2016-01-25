(ns sieve.core
  (:require [clojure.core.async :as async :refer :all
             :exclude [map into reduce merge partition partition-by take]]
            [clojure.edn :as edn]))







;; to-chan returns a channel containing the contents of a sequence
(defn factor? [x y]
  (zero? (mod y x)))

;; Works only with   core async 0.1.267.0-0d7780-alpha
(defn get-primes []
  (let [primes (chan)
        numbers (to-chan (iterate inc 2))]
    (go-loop [ch numbers]
      (when-let [prime (<! ch)]
        (>! primes prime)
        (recur (remove< (partial factor? prime) ch)))
      (close! primes))
    primes))


(defn -main [seconds]
  (let [primes (get-primes)
        limit (timeout (* (edn/read-string seconds) 1000))]
    (loop []
      (alt!! :priority true
        limit nil
        primes ([prime] (println prime) (recur))))))
