(ns bookings.events-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [bookings.core :as core]
            [bookings.events :as events]))

(def matrix-to-test
  [
   [ 0  1  0  1  0]
   [ 0  0  0  1  1]
   [ 0  0  0  1  0]
   [ 1  1  0  0  0]
   [ 0  0  1  1  0]
   ])


(def user-selection-matrix-to-test
  [
   [ 0  1  0  1  0]
   [ 0  0  0  0  1]
   [ 0  0  0 :o  0]
   [ 0  0  0  1  0]
   [ 1  0 :o  0  0]
   ])

(def result-of-multiply
  [
   [ 0  1  0  1  0]
   [ 0  0  0  0  1]
   [ 0  0  0  0  0]
   [ 0  0  0  0  0]
   [ 0  0  0  0  0]
   ])

(deftest multiply-element-by-element-test
  (testing "multiply-element-by-element"
    (is
      (= (events/multiply-element-by-element
           matrix-to-test
           user-selection-matrix-to-test)
         result-of-multiply))))


(deftest count-number-of-occurrences-in-matrix-test
  (testing
    (is (= (events/count-number-of-occurrences-in-matrix '((1 0) (1 1)) 1) 3)))
  )

