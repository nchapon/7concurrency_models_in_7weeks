(ns user
  (:require [reloaded.repl :refer [system reset stop]]
            [tournament-server.main]))

(reloaded.repl/set-init! #'tournament-server.main/create-system)
