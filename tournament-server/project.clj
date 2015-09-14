(defproject tournament-server "0.1.0-SNAPSHOT"
  :description "7cmin7w : chap 3"
  :license {:name "GNU General Public License"
            :url "http://www.gnu.org/licenses/gpl.html"}
  :jvm-opts ["-XX:MaxPermSize=256m"]
  :main tournament-server.system
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [http-kit "2.1.18"]
                 [compojure "1.3.4"]
                 [com.stuartsierra/component "0.2.3"]
                 [cheshire "5.5.0"]] ;; json encoding
  :profiles {:dev {:plugins []
                   :dependencies [[reloaded.repl "0.1.0"]]
                   :source-paths ["dev"]}})
