(ns bookings.subs
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
 :name
 (fn [db]
   (:name db)))

(re-frame/reg-sub
 :active-panel
 (fn [db _]
   (:active-panel db)))

(re-frame/reg-sub
 :matrix-size
 (fn [db _]
   (:matrix-size db)))


(re-frame/reg-sub
  :game-step
 (fn [db _]
   (:game-step db)))


(re-frame/reg-sub
  :matrix-cells
 (fn [db _]
   (:matrix-cells db)))

(re-frame/reg-sub
  :user-selection-matrix
 (fn [db _]
   (:user-selection-matrix db)))

(re-frame/reg-sub
  :game-status
 (fn [db _]
   (:game-status db)))