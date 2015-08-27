(ns user
  (:require [reloaded.repl :refer [system reset stop]]
            [transcript-handler.system]))

(reloaded.repl/set-init! #'transcript-handler.system/create-system)
