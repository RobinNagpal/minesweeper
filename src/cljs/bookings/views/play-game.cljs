(ns bookings.views.play_game
  (:require [re-frame.core :as re-frame]
            [reagent.core :as r])
  )


(defn setUserSelection
  [row, col]
  (println row col)
  (re-frame/dispatch [:mark-matrix-cell-as-selected row col]))


(defn zeroOrOne
  []
  (int (/ (rand-int 10) 6)))


(defn setGameMatrix [matrix]
  (re-frame/dispatch [:set-matrix-cells matrix]) matrix)


(defn initializeUserMatrix
  [matrix-size]
  (re-frame/dispatch
    [:initialize-user-selection-matrix (int matrix-size)]))



(defn createGameRow [rowIndex row matrix-size]
  (map-indexed (fn [columnIndex cell] ^{:key (str columnIndex rowIndex "mine-cell-" cell "-val")}
  [:div {:style    {:width       (str (/ 100 matrix-size) "%")
                    :padding-top (str (/ 100 matrix-size) "%")}
         :class    "mine-box"
         :on-click #(setUserSelection rowIndex columnIndex)
         } cell]) row))


(defn play-game []
  (let [
        matrix-size (re-frame/subscribe [:matrix-size])
        matrix-cells (re-frame/subscribe [:matrix-cells])]
    (r/create-class
      {
       :component-did-mount
       (fn []
         (
           let
           [deref-matrix-size @matrix-size
            deref-matrix-cells @matrix-cells]
           (->>
             (range deref-matrix-size)
             (map (fn [_] 1),,,)
             (map (fn [_] (for [x (range deref-matrix-size)] (zeroOrOne))),,,)
             (setGameMatrix)
             )
           (initializeUserMatrix deref-matrix-size)))

       :reagent-render
       (fn []
         (let
           [deref-matrix-size @matrix-size
            deref-matrix-cells @matrix-cells]
           [:div {:class "container"}
            [:h2 "Select the squares that don't have mines"]
            [:div {:class "col-lg-2 col-md-2"}]
            [:div {:class "col-lg-8 col-md-8" :style {:padding "50px 0px 50px 0px"}}
             [:div (map-indexed #(createGameRow %1 %2 deref-matrix-size) deref-matrix-cells)]]
            [:div {:class "col-lg-2 col-md-2"}]])
         )
       })))

