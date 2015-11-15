(ns mkwords.util)

(defn candidate-count
  [word-list {:keys [min max]}]
  (count (filter #(and (>= (count %) min) (<= (count %) max)) word-list)))
