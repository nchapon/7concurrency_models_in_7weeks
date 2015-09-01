(ns transcript-handler.web
  (:require [compojure.core :refer :all]
            [clojure.edn :as edn]
            [ring.util.response :refer [response]]))


(def snippets (repeatedly promise))

(defn accept-snippet
  "Accept snippet"
  [n text]
  (deliver (nth snippets n) text))

(future
  (doseq [snippet (map deref snippets)] ;; block if one snippet is not deliver...
     (println snippet)))

(defroutes app
  (PUT "/snippet/:n" [n :as {:keys [body]}]
       (accept-snippet (edn/read-string n) (slurp body))
       (response "OK")))
