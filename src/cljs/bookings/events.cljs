(ns bookings.events
  (:require [re-frame.core :as re-frame]
            [bookings.db :as db]))

(re-frame/reg-event-db
  :initialize-db
  (fn [_ _]
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

(re-frame/reg-event-db
  :set-matrix-cells
  (fn [db [_ matrix-cells]]
    (assoc db :matrix-cells matrix-cells)))


(re-frame/reg-event-fx
  :initialize-user-selection-matrix
  (fn [db [_ matrix-size]]
    {:dispatch [:set-user-selection-matrix (vec (repeat matrix-size (vec (repeat matrix-size 0))))]}))


(re-frame/reg-event-db
  :set-user-selection-matrix
  (fn [db [_ user-selection-matrix]]
    (assoc db :user-selection-matrix user-selection-matrix)))


(re-frame/reg-event-fx
  :mark-matrix-cell-as-selected
  (fn [db [_ row col]]
    (let [user-selection-matrix (:user-selection-matrix (:db db))]
      {:dispatch
       [:set-user-selection-matrix
        (update-in user-selection-matrix [row] (fn [row] (update-in row [col] (fn [_] 1) )))]})))