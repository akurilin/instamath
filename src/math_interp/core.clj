(ns math-interp.core
  (:require [instaparse.core :as insta]))

; Questions:
; * whitespace preprocessing vs whitespace in grammar

(def math (insta/parser (slurp "grammar.ebnf")))

(defn pick-op
  [op]
  (case op
    "+" +
    "-" -
    "*" *
    "/" /
    )
  )

(def transform-options
  {:integer #(Long/parseLong %)
   :floating #(Double/parseDouble %)
   :number identity
   :op pick-op
   :operation (fn [a op b] (op a b))
   :expr identity})

(defn math-eval
  [source & options]
  (->> (clojure.string/replace source #"\s+" "")
       ((fn [x] (if (:show-ambiguity (first options))
                 (insta/parses math x)
                 (insta/parse math x))))
       ; ((fn [tree] (do (clojure.pprint/pprint tree) tree)))
       (insta/transform transform-options)
       ; ((fn [res] (do (clojure.pprint/pprint res) res)))
       
       ))

; (math-eval "1+1")
; (math-eval "1")
