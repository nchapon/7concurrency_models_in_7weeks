(ns chap03.philosophers-nostm)

(def philosophers (atom (into [] (repeat 5 :thinking))))

(defn think
  [n]
  (println (format "%s thinking" n)) ;; in cider to see output open nrepl-server buffer
  (Thread/sleep (rand 1000)))

(defn eat
  [n]
  (println (format "%s eating" n))
  (Thread/sleep (rand 1000)))

(defn- claim-chopsticks! [p left right]
  (swap! philosophers
         (fn [ps]
           (if (and (= (ps left) :thinking) (= (ps right) :thinking))
             (assoc ps p :eating)
             ps)))
  (= (@philosophers p) :eating))

(defn- release-chopsticks! [p]
  (swap! philosophers assoc p :thinking))

(defn philosopher-thread [p]
  (Thread.
   #(let [left (mod (- p 1) 5)
          right (mod (+ p 1) 5)]
      (while true
        (think p)
        (when (claim-chopsticks! p left right)
          (eat p)
          (release-chopsticks! p))))))

(defn run []
  (let [threads (map philosopher-thread (range 5))]
    (doseq [thread threads] (.start thread))
    (doseq [thread threads] (.join thread))))
