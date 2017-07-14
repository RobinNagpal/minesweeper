(ns bookings.subs
  (:require-macros
    [reagent.ratom :refer [reaction]])
  (:require
    [re-frame.core :as re-frame]
    [bookings.utils :as utils]
    [reagent.core :as r]))

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

(defn getAnnotatedMatrix [matrix-cells]
  (->> matrix-cells
       (map (fn [row] (replace {1 :m} row)))
       (map-indexed
         (fn [rowIndex row]
           (vec (map-indexed
                  (fn [columnIndex cell]
                    (if
                      (= :m cell)
                      :m
                      (let [counter-of-mines (r/atom 0)
                            leftCellIndex (- columnIndex 1)
                            rightCellIndex (+ columnIndex 1)

                            upRow (get matrix-cells (- rowIndex 1))
                            downRow (get matrix-cells (+ rowIndex 1))

                            upCell (get upRow columnIndex)
                            upLeftCell (get upRow leftCellIndex)
                            upRightCell (get upRow rightCellIndex)

                            downCell (get downRow columnIndex)
                            downLeftCell (get downRow leftCellIndex)
                            downRightCell (get downRow rightCellIndex)

                            isLeftCellAMine (= :m (get row leftCellIndex))
                            isRightCellAMine (= :m (get row rightCellIndex))

                            isUpCellAMine (= 1 upCell)
                            isUpperLeftAMine (= 1 upLeftCell)
                            isUpperRightAMine (= 1 upRightCell)

                            isDownCellAMine (= 1 downCell)
                            isDownLeftCellAMine (= 1 downLeftCell)
                            isDownRightCellAMine (= 1 downRightCell)]
                        (if isLeftCellAMine (swap! counter-of-mines inc) counter-of-mines)
                        (if isRightCellAMine (swap! counter-of-mines inc) counter-of-mines)
                        (if isUpCellAMine (swap! counter-of-mines inc) counter-of-mines)
                        (if isUpperLeftAMine (swap! counter-of-mines inc) counter-of-mines)
                        (if isUpperRightAMine (swap! counter-of-mines inc) counter-of-mines)

                        (if isDownCellAMine (swap! counter-of-mines inc) counter-of-mines)
                        (if isDownLeftCellAMine (swap! counter-of-mines inc) counter-of-mines)
                        (if isDownRightCellAMine (swap! counter-of-mines inc) counter-of-mines)
                        (deref counter-of-mines)))) row))
           ))
       (vec)

       ))



(re-frame/reg-sub
  :annotated-matrix-cells
  (fn [db _]
    (re-frame/subscribe [:matrix-cells]))
  (fn [matrix-cells _]
    (getAnnotatedMatrix matrix-cells)))


(re-frame/reg-sub
  :user-selection-matrix
  (fn [db _]
    (:user-selection-matrix db)))

(re-frame/reg-sub
  :game-status
  (fn [db _]
    (:game-status db)))