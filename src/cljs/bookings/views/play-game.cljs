(ns bookings.views.play_game
  (:require [re-frame.core :as re-frame]
            [reagent.core :as r])
  )


(defn setUserSelection
  [row, col]
  (re-frame/dispatch [:mark-matrix-cell-as-selected row col]))


(defn zeroOrOneForGame
  []
  (int (/ (rand-int 10) 6)))

(defn zeroOrOneForUserSelection
  []
  (if (= 1 (int (/ (rand-int 8) 6))) :o 0))


(defn setGameMatrix [matrix]
  (re-frame/dispatch [:set-matrix-cells matrix]) matrix)

(defn getGameMatrix [matrix-size zeroOrOneFn randomChar]
  (->>
    (vec (range matrix-size))
    (map (fn [_] 1),,,)
    (map (fn [_] (vec (for [x (range matrix-size)] (zeroOrOneFn)))),,,)
    (vec)))

(defn initializeUserMatrix
  [matrix-size]
  (re-frame/dispatch
    [:set-user-selection-matrix (getGameMatrix matrix-size zeroOrOneForUserSelection :o)]))


(defn createGameRow [rowIndex row matrix-size user-selection-matrix annotated-matrix-cells]
  (map-indexed
    (fn [columnIndex cell]
      (let
        [
         userSelection (get (get user-selection-matrix rowIndex) columnIndex)
         annotatedCellValue (get (get annotated-matrix-cells rowIndex) columnIndex)
         isCellSelected (or (= 1 userSelection) (= :o userSelection))
         isMineCell (= :m annotatedCellValue)
         annotatedCell (if isCellSelected (vector :span (if isMineCell "" annotatedCellValue)) (vector :span))]
        ^{:key (str columnIndex rowIndex "mine-cell-" cell "-val")}
        [:div {:style
                         {:width  (str (/ 100 matrix-size) "%")
                          :height (str (/ 100 matrix-size) "%")}
               :class    (if isCellSelected
                           (if isMineCell
                             "dangerous-mine-box-selected"
                             "safe-mine-box-selected")
                           "mine-box")
               :on-click #(do (setUserSelection rowIndex columnIndex)
                              (re-frame/dispatch [:update-game-status]))
               } annotatedCell])) row))


(defn play-game []
  (let [matrix-size (re-frame/subscribe [:matrix-size])
        matrix-cells (re-frame/subscribe [:matrix-cells])
        user-selection-matrix (re-frame/subscribe [:user-selection-matrix])
        annotated-matrix-cells (re-frame/subscribe [:annotated-matrix-cells])]
    (r/create-class
      {
       :component-did-mount
       (fn []
         (
           let
           [deref-matrix-size @matrix-size
            deref-matrix-cells @matrix-cells]
           (setGameMatrix (getGameMatrix deref-matrix-size zeroOrOneForGame 1))
           (initializeUserMatrix deref-matrix-size)))

       :reagent-render
       (fn []
         (let [deref-matrix-size @matrix-size
               deref-matrix-cells @matrix-cells
               deref-user-selection-matrix @user-selection-matrix
               deref-annotated-matrix-cells @annotated-matrix-cells
               ]
           [:div {:class "container"}
            [:h2 "Select the squares that don't have mines"]
            [:div {:class "col-lg-2 col-md-2"}]
            [:div {:class "col-lg-8 col-md-8"
                   :style {
                           :width       "70%"
                           :padding-top "70%"
                           :position    "relative"
                           }}

             [:div {:style {
                            :position "absolute"
                            :height   "100%"
                            :top      0
                            :left     0
                            :bottom   0
                            :right    0
                            }}
              (map-indexed
                #(createGameRow %1 %2 deref-matrix-size deref-user-selection-matrix deref-annotated-matrix-cells)
                deref-matrix-cells)]]
            [:div {:class "col-lg-2 col-md-2"}]])
         )
       })))

