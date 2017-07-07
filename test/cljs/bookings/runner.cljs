(ns bookings.runner
    (:require [doo.runner :refer-macros [doo-tests]]
              [bookings.core-test]))

(doo-tests 'bookings.core-test)
