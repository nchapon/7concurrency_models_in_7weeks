(ns chap03.philosophers-stm)


(def philosophers (into [] (repeatedly 5 #(ref :thinking))))

(defn think
  [n]
  (println (format "%s thinking" n)) ;; in cider to see output open nrepl-server buffer
  (Thread/sleep (rand 1000)))

(defn eat
  [n]
  (println (format "%s eating" n))
  (Thread/sleep (rand 1000)))

(defn- claim-chopsticks [philosopher left right]
  (dosync
   (when (and (= (ensure  left) :thinking) (= (ensure right) :thinking)) ;; ensure plus consistent que deref : valeur non changée par une autre transaction
     (ref-set philosopher :eating))))

(defn- release-chopsticks [philosopher]
  (dosync
   (ref-set philosopher :thinking)))

(defn philosopher-thread [n]
  (Thread.
   #(let [philosopher (philosophers n)
          left (philosophers (mod (- n 1) 5))
          right (philosophers (mod ( + n 1) 5))]
      (while true
        (think n)
        (when (claim-chopsticks philosopher left right)
          (eat n)
          (release-chopsticks philosopher))))))

(defn run []
  (let [threads (map philosopher-thread (range 5))]
    (doseq [thread threads] (.start thread))
    (doseq [thread threads] (.join thread))))
