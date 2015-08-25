(ns chap03.reducers
  (:require [clojure.core.protocols :refer [CollReduce coll-reduce]]
            [clojure.core.reducers :refer [CollFold coll-fold]]))

(defn my-fold
  ([reducef coll]
   (my-fold reducef reducef coll))
  ([combinef reducef coll]
   (my-fold 512 combinef reducef coll))
  ([n combinef reducef coll]
   (coll-fold coll n combinef reducef)))

(defn my-reduce
  ([f coll] (coll-reduce coll f))
  ([f init coll] (coll-reduce coll f init)))

(defn make-reducer [reducible transformf]
  (reify
    CollFold
    (coll-fold [_ n combinef reducef]
      (coll-fold reducible n combinef (transformf reducef)))
    CollReduce
    (coll-reduce [_ f1]
      (coll-reduce reducible (transformf f1) (f1)))
    (coll-reduce [_ f1 init]
      (coll-reduce reducible (transformf f1) init))))

(defn my-map [mapf reducible]
  "My map is a reducer so we can chain it..."
  (make-reducer reducible
                (fn [reducef]
                  (fn [acc v]
                    (reducef acc (mapf v))))))
