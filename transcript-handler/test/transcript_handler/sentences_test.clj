(ns transcript-handler.sentences-test
  (:require [clojure.test :refer :all]
            [transcript-handler.sentences :refer :all]))


(deftest is-sentence-test
  (testing "returns nil when not a sentence"
    (is (= nil (is-sentence? "Not a sentence")))
    (is (= (is-sentence? "A sentence.") "A sentence." ))))


(def strings ["A" "sentence." "And another." "Last" "sentence."])

(deftest strings->sentences-test
  (is (= ("A sentence." "And another." "Last sentence.")
         (strings->sentences strings))))
