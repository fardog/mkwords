(ns mkwords.api
  (:require [clojure.java.io :as io]
            [clojure.string :as string]
            [clojure.walk :as walk]
            [ring.util.response :as ring-response]
            [ring.middleware.params :as ring-params]
            [ring.middleware.json :as ring-json]
            [hazard.core :as hazard]
            [mkwords.constants :refer [$defaults]]))

(def word-list 
  (string/split-lines (slurp (io/resource "public/vendor/mwords/113809of.fic"))))

(defn parse-int
  [x]
  (if (number? x)
    x
    (Integer/parseInt x)))

(defn- do-generate
  [request]
  (let [params (walk/keywordize-keys (:params request))
        num-words (parse-int (:num-words params (:num-words $defaults)))
        opts {:min (parse-int (:min-chars params (:min-chars $defaults)))
              :max (parse-int (:max-chars params (:max-chars $defaults)))}]
    (try
      (let [words (hazard/words word-list num-words opts)]
        {:body {:ok true
                :result words
                :candidate-count (:candidate-count (meta words))}
         :status 200})
      (catch Exception e
        {:body {:ok false
                :error (.getMessage e)}
         :status 400}))))

(def generate
  (-> do-generate
      ring-params/wrap-params
      ring-json/wrap-json-body
      ring-json/wrap-json-response))
