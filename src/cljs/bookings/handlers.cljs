(ns bookings.handlers
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-event-fx
  :set-matrix-size
  (fn [db _]
    (:active-panel db)))
