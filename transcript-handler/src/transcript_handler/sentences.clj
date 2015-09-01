(ns transcript-handler.sentences
  (:require [clojure.string :as str]))



(defn sentence-split
  "Returns sentences from text."
  [text]
  (map str/trim (re-seq #"[^\.!\?:;]+[\.!\?:;]*" text)))

(defn is-sentence?
  "Returns sentence or nil if not a sentence."
  [text]
  (re-matches #"^.*[\.!\?:;]$" text))


(defn sentence-join
  "Join sentence."
  [x y]
  (if (is-sentence? x)
    y
    (str x " " y)))


(defn strings->sentences
  "Convert strings to sentences"
  [strings]
  (filter is-sentence?
          (reductions sentence-join
                      (mapcat sentence-split strings))))
