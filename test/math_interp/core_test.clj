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
  (is (= 5 (math-eval "3 * 2 - 1 + 3 / 3 - 1")))
  (is (= 19 (math-eval "1 + 2 * 3 ^ 2")))
  (is (= 13 (math-eval "3 * 4 + √4^3 + 3 - 10"))))

(deftest unary-operators
  (is (= -1 (math-eval "-1")))
  (is (= -0.1 (math-eval "-0.1")))
  (is (= 0 (math-eval "1+-1"))))

(deftest parentheses
  (is (= 3 (math-eval "1 + (1 + 1)")))
  (is (= 16 (math-eval "(4 + 4) * 2")))
  (is (= 5 (math-eval "(1 + (1 + 1 + (1 + 1)))"))))

(deftest exponent
  (is (= 8 (math-eval "2^3")))
  (is (= 8 (math-eval "(1+1)^(2+1)")))
  (testing "right-associativity of ^"
    (is (= 512 (math-eval "2^3^2")))))

; TODO: √ and ^ associativity will be really confusing to most people
; and needs to be fixed by mandating the use of parentheses.
(deftest square-root
  (is (= 4 (math-eval "√(16)")))
  (is (= 4 (math-eval "√(4*4)")))
  (testing "nesting of square roots"
    (is (= 2 (math-eval "√√16"))))
  (testing "square root and exp associativity"
    (is (= 65536 (math-eval "√4^4^2")))))

(deftest variables
  (is (= 13 (math-eval "x + 7" {:x 6})))
  (is (= 7 (math-eval "x + y" {:x 4 :y 3}))))
