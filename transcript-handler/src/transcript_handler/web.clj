(ns transcript-handler.web
  (:require [compojure.core :refer :all]
            [clojure.edn :as edn]
            [clj-http.client :as client]
            [ring.util.response :refer [response]]
            [transcript-handler.sentences :refer :all]))

(def translator "http://localhost:3001/translate")

(defn translate
  "Translate TEXT"
  [text]
  (future
    (:body (client/post translator {:body text}))))

(def snippets (repeatedly promise))

(def translations
  (delay
   (map translate (strings->sentences (map deref snippets)))))

(defn accept-snippet
  "Accept snippet"
  [n text]
  (deliver (nth snippets n) text))

(defn get-translation
  ""
  [n]
  @(nth n @translations))



(defroutes app
  (PUT "/snippet/:n" [n :as {:keys [body]}]
       (accept-snippet (edn/read-string n) (slurp body))
       (response "OK"))
  (GET "/translation/:n" [n]
       (response (get-translation (edn/read-string n)))))
