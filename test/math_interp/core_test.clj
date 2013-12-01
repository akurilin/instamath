(ns math-interp.core-test
  (:refer-clojure :exclude [doubles])
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

(deftest subtraction
  (is (= 8 (math-eval "10 - 2")))
  (is (= -2 (math-eval "4 - 6"))))

(deftest division
  (is (= 2 (math-eval "8 / 4"))))

(deftest multiplication
  (is (= 10 (math-eval "5 * 2"))))

(deftest coercion
  (let [res (math-eval "1 + 0.55")]
    (is (== (+ 1 0.55) res))
    (is (= java.lang.Double (type res)))))

(deftest whitespace
  (is (= 2 (math-eval "1+1")))
  (is (= 2 (math-eval "   1    +    1  "))))

(deftest addition-multiple-operands
  (is (= 3 (math-eval "1 + 1 + 1"))))

(deftest operator-precedence
  (is (= 3 (math-eval "2 + 2 / 2")))
  (is (= 6 (math-eval "2 + 2 * 2")))
  (is (= 5 (math-eval "2 / 2 + 2 * 2")))
  (is (= 5 (math-eval "3 * 2 - 1 + 3 / 3 - 1"))))

(deftest unary-operators
  (is (= -1 (math-eval "-1")))
  (is (= -0.1 (math-eval "-0.1")))
  (is (= 0 (math-eval "1+-1"))))

(deftest parentheses
  (is (= 3 (math-eval "1 + (1 + 1)")))
  (is (= 16 (math-eval "(4 + 4) * 2")))
  (is (= 5 (math-eval "(1 + (1 + 1 + (1 + 1)))"))))

(deftest exponent)
(deftest square-root)
(deftest variables)
