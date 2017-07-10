(ns bookings.events
  (:require [re-frame.core :as re-frame]
            [bookings.db :as db]))

(re-frame/reg-event-db
 :initialize-db
 (fn  [_ _]
   db/default-db))

(re-frame/reg-event-db
 :set-active-panel
 (fn [db [_ active-panel]]
   (assoc db :active-panel active-panel)))

(re-frame/reg-event-db
  :set-matrix-size
  (fn [db [_ matrix-size]]
    (assoc db :matrix-size matrix-size)))

(re-frame/reg-event-db
  :set-game-step
  (fn [db [_ game-step]]
    (assoc db :game-step game-step)))