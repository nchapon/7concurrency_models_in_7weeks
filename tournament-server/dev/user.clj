(ns user
  (:require [reloaded.repl :refer [system reset stop]]
            [tournament-server.system]))

(reloaded.repl/set-init! #'tournament-server.system/create-system)
