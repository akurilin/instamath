(ns math-interp.core
  (:require [instaparse.core :as insta]
            [clojure.math.numeric-tower :as tower]))

; To Investigate:
; * whitespace preprocessing vs whitespace in grammar

; grammar is stored in a separate file to avoid character escaping headaches
(def math (insta/parser (slurp "grammar.ebnf")))

(defn pick-op
  "Convert operation terminal to corresponding Clojure function"
  [op]
  (case op
    "+" +
    "-" -
    "*" *
    "/" /
    "^" tower/expt
    "√" tower/sqrt))

(defn operation
  ([a] a)
  ([op a] (op a))
  ([a op b] (op a b)))

(defn interpolate
  "Lookup variable value in env map"
  [env variable]
  ((keyword variable) env))

(defn transform-options
  "How to transform each production head"
  [env]
  {:expr operation
   :term operation
   :factor operation
   :addsub pick-op
   :multdiv pick-op
   :exp pick-op
   :sqrt pick-op
   :variable (partial interpolate env)
   :number operation
   :integer #(Long/parseLong %)
   :floating #(Double/parseDouble %)})

(defn math-eval
  "Main eval function. One of the arities takes an env map"
  ([source] (math-eval source {}))
  ([source env]
   (->> (clojure.string/replace source #"\s+" "")
        math
        ; ((fn [tree] (do (clojure.pprint/pprint tree) tree)))
        ; ((fn [tree] (do (insta/visualize tree :output-file "out.png") tree)))
        (insta/transform (transform-options env))
        ; ((fn [res] (do (clojure.pprint/pprint res) res)))
        )))
