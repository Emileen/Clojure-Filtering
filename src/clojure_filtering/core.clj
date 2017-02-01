(ns clojure-filtering.core
  (:require [clojure.string :as str])
  (:require [clojure.data.json :as json])
  (:gen-class))

(defn -main []
  (println "Pick a Cateogry:")
  (println "Alcohol")
  (println "Furniture")
  (println "Shoes")
  (println "Jewelry")
  (println "Toiletries")
  (println "Food")

  (let [purchases (slurp "purchases.csv") ;take it all in an store it in purchases
        purchases (str/split-lines purchases); makes it into each line containing vector
        purchases (map (fn [line]
                         (str/split line #",")) ; splits at the vector and produces a line without ,
                       purchases)
        header (first purchases) ;separates the header from the rest of the line
        purchases (rest purchases) ; redefines the purchases
        purchases (map (fn [line]  ; makes a hashmap with category and item
                         (zipmap header line))
                       purchases)
        category (read-line) ; filter by category
        purchases (filter (fn [line]
                            (= (get line "category") category))
                          purchases)
        file-json (json/write-str purchases)]
    (spit (str "filtered_purchases_" category ".json") file-json) category)) ; writes the file to json
