(defproject web "0.1"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/data.csv "0.1.2"]
                 [enlive "1.1.6"]]
  :main web.core
  :uberjar-name "web.jar"
  :source-paths ["src"]
  :profiles {:uberjar {:aot :all}})
