(defproject translator "0.1.0-SNAPSHOT"
  :description "7 ccm in 7 weeks : Translator"
  :license {:name "GNU General Public License"
            :url "http://www.gnu.org/licenses/gpl.html"}
  :main translator.core
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [http-kit "2.1.18"]
                 [compojure "1.3.4"]
                 [com.stuartsierra/component "0.2.3"]]
  :profiles {:dev {:plugins []
                   :dependencies [[reloaded.repl "0.1.0"]]
                   :source-paths ["dev"]}})
