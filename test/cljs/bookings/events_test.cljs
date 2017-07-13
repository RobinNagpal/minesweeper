(ns bookings.events-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [bookings.core :as core]
            [bookings.events :as events]))

(deftest multiply-element-by-element-test
  (testing "multiply-element-by-element"
    (is
      (= (events/multiply-element-by-element '((1 0) (1 0)) '((1 0) (1 1))) '((1 0) (1 0))))))


(deftest count-number-of-occurrences-in-matrix-test
  (testing
    (is (= (events/count-number-of-occurrences-in-matrix '((1 0) (1 1)) 1) 3)))
  )

