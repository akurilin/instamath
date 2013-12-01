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

(defn operation
  ([a] a)
  ([a op b]
   (op a b)))

(def transform-options
  {:expr operation
   :term operation
   :addsub pick-op
   :multdiv pick-op
   :number identity
   :integer #(Long/parseLong %)
   :floating #(Double/parseDouble %)})

(defn math-eval
  [source & options]
  (->> (clojure.string/replace source #"\s+" "")
       ((fn [x] (if (:show-ambiguity (first options))
                 (insta/parses math x)
                 (insta/parse math x))))
       ; ((fn [tree] (do (clojure.pprint/pprint tree) tree)))
       ; ((fn [tree] (do (insta/visualize tree :output-file "out.png") tree)))
       (insta/transform transform-options)
       ; ((fn [res] (do (clojure.pprint/pprint res) res)))
       ))

; (math-eval "1+1")
; (math-eval "1+1+1+1/2*2")
