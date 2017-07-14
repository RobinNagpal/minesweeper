(ns bookings.subs-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [bookings.core :as core]
            [bookings.subs :as subs]))

(def matrix-to-test
  [
   [ 0  1  0  1  0]
   [ 0  0  0  1  1]
   [ 0  0  0  1  0]
   [ 1  1  0  0  0]
   [ 0  0  1  1  0]
   ])

(def expected-matrix
  [
   [ 1 :m  3 :m  3]
   [ 1  1  4 :m :m]
   [ 2  2  3 :m  3]
   [:m :m  4  3  2]
   [ 2  3 :m :m  1]
   ])

(deftest annotated-matrix-test
  (testing "annotated-,atrix"
    (is
      (= (subs/getAnnotatedMatrix matrix-to-test) expected-matrix))))
