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


(re-frame/reg-event-db
  :mark-matrix-cell-as-selected
  (fn [db [_ row col]]
    (let [user-selection-matrix (:user-selection-matrix db)]
      (assoc db :user-selection-matrix (update-in user-selection-matrix [row] (fn [row] (update-in row [col] (fn [_] 1))))))))

(defn- hasSteppedOnAMine [mineAndUserSelectionsMap] (some #(= 1 %) (flatten mineAndUserSelectionsMap)))

(defn- gameLostResult [db] (assoc db :game-status "LOST"))

(defn- multiplyElementByElement [matrix1 matrix2] (map (fn [matrix-row user-selection-row] (map * matrix-row user-selection-row)) matrix1 matrix2))

(defn- countNumberOfOccurrencesInMatrix [matrix element] (count (filterv #(= element %) (flatten matrix))))

(defn- countSafeCells [matrix-cells] (countNumberOfOccurrencesInMatrix matrix-cells 0))

(defn- countUserPlays [user-selections] (countNumberOfOccurrencesInMatrix user-selections 1))

(defn- inProgressOrWonResult [db matrix-cells user-selection-matrix]
  (assoc db :game-status
            (if (= (countSafeCells matrix-cells) (countUserPlays user-selection-matrix) ) "WON" "IN_PROGRESS")))

(re-frame/reg-event-db
  :update-game-status
  (fn [db [_]]
    (let
      [user-selection-matrix (:user-selection-matrix db)
       matrix-cells (:matrix-cells db)]
      (-> (multiplyElementByElement matrix-cells user-selection-matrix)
          (hasSteppedOnAMine)
          (if,,, (gameLostResult db) (inProgressOrWonResult db matrix-cells user-selection-matrix)))
      )))
