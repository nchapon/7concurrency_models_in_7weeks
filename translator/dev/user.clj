(ns user
  (:require [reloaded.repl :refer [system reset stop]]
            [translator.core]))

(reloaded.repl/set-init! #'translator.core/create-system)
