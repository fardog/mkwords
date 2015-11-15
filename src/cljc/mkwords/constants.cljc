(ns mkwords.constants
  (:require [clojure.set :refer [rename-keys]]))

(def rel-chars-map
  ^{:private true}
  {:min-chars :min :max-chars :max})


(def rel-words-map
  ^{:private true}
  {:min-words :min :max-words :max})

(def $strings
  {:num-words "Number of Words"
   :min-chars "Minimum Characters"
   :max-chars "Maximum Characters"
   :loading   "loading wordlist"})

(def $limits
  {:min-words 1
   :max-words 10
   :min-chars 1
   :max-chars 21})

(def $defaults
  {:min-chars 5
   :max-chars 8
   :num-words 4})

(def $default-chars
  (rename-keys $defaults rel-chars-map))

(def $limit-chars
  (rename-keys $limits rel-chars-map))

(def $limit-words
  (rename-keys $limits rel-words-map))
