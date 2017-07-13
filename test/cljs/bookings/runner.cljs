(ns bookings.runner
    (:require [doo.runner :refer-macros [doo-tests]]
              [bookings.core-test]
              [bookings.events-test]))

(doo-tests 'bookings.core-test
           'bookings.events-test)