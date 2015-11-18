(defproject mkwords "0.1.0-pre.3"
  :description "Get lists of random English words, many as you'd like."
  :url "https://mkwords.fardog.io"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.7.0"]
                 [ring-server "0.4.0"]
                 [reagent "0.5.1"]
                 [reagent-forms "0.5.13"]
                 [reagent-utils "0.1.5"]
                 [ring "1.4.0"]
                 [ring/ring-defaults "0.1.5"]
                 [ring/ring-json "0.4.0"]
                 [prone "0.8.2"]
                 [compojure "1.4.0"]
                 [hiccup "1.0.5"]
                 [environ "1.0.1"]
                 [org.clojure/clojurescript "1.7.145" :scope "provided"]
                 [secretary "1.2.3"]
                 [venantius/accountant "0.1.4"]
                 [hazard "0.3.0"]
                 [cljs-ajax "0.5.1"]
                 [endophile "0.1.2"]]

  :plugins [[lein-environ "1.0.1"]
            [lein-shell "0.4.1"]]

  :ring {:handler mkwords.handler/app
         :uberwar-name "mkwords.war"}

  :min-lein-version "2.5.0"

  :uberjar-name "mkwords.jar"

  :main mkwords.server

  :prep-tasks [["shell" "npm" "run" "build"]
               "javac" "compile"]

  :clean-targets ^{:protect false} [:target-path
                                    [:cljsbuild :builds :app :compiler :output-dir]
                                    [:cljsbuild :builds :app :compiler :output-to]]

  :source-paths ["src/clj" "src/cljc"]

  :cljsbuild {:builds {:app {:source-paths ["src/cljs" "src/cljc"]
                             :compiler {:output-to     "resources/public/js/app.js"
                                        :output-dir    "resources/public/js/out"
                                        :asset-path   "js/out"
                                        :optimizations :none
                                        :pretty-print  true}}}}

  :profiles {:dev {:repl-options {:init-ns mkwords.repl}

                   :dependencies [[ring/ring-mock "0.3.0"]
                                  [ring/ring-devel "1.4.0"]
                                  [lein-figwheel "0.4.1"]
                                  [org.clojure/tools.nrepl "0.2.11"]
                                  [com.cemerick/piggieback "0.1.5"]
                                  [pjstadig/humane-test-output "0.7.0"]]

                   :source-paths ["env/dev/clj"]
                   :plugins [[lein-figwheel "0.4.1"]
                             [lein-cljsbuild "1.1.0"]]

                   :injections [(require 'pjstadig.humane-test-output)
                                (pjstadig.humane-test-output/activate!)]

                   :figwheel {:http-server-root "public"
                              :server-port 3449
                              :nrepl-port 7002
                              :nrepl-middleware ["cemerick.piggieback/wrap-cljs-repl"
                                                 ]
                              :css-dirs ["resources/public/css"]
                              :ring-handler mkwords.handler/app}

                   :env {:dev true}

                   :cljsbuild {:builds {:app {:source-paths ["env/dev/cljs"]
                                              :compiler {:main "mkwords.dev"
                                                         :source-map true}}}}}

             :uberjar {:hooks [leiningen.cljsbuild]
                       :env {:production true}
                       :aot :all
                       :omit-source true
                       :cljsbuild {:jar true
                                   :builds {:app
                                             {:source-paths ["env/prod/cljs"]
                                              :compiler
                                              {:optimizations :advanced
                                               :pretty-print false}}}}}})
