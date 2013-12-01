(ns math-interp.core-test
  (:require [clojure.test :refer :all]
            [math-interp.core :refer :all]))

(deftest integers
  (is (= 1 (math-eval "1")))
  (is (= java.lang.Long (type (math-eval "1")))))

(deftest doubles
  (let [res (math-eval "0.333")]
    (is (= 0.333 res))
    (is (= java.lang.Double (type res)))))

(deftest addition
  (is (= 2 (math-eval "1 + 1"))))

(deftest coercion
  (let [res (math-eval "1 + 0.55")]
    (is (== (+ 1 0.55) res))
    (is (= java.lang.Double (type res)))))

(deftest whitespace
  (is (= 2 (math-eval "1+1")))
  (is (= 2 (math-eval "   1    +    1  "))))

(deftest addition-multiple-operands)
