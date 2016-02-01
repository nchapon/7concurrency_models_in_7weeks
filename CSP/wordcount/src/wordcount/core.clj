(ns wordcount.core
  (:require [org.httpkit.client :as http]
            [clojure.core.async :as async :refer :all :exclude [map into reduce merge partition partition-by take]]))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))
