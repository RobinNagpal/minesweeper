(ns bookings.views
  (:require [re-frame.core :as re-frame]
            [reagent.core :as r])
  )

(defn size-selection [step]
  (let [matrix-size (r/atom 0)]
    [:div [:h2 "Select the size of matrix you want to play with"]
     [:div {:class "input-group" :style {:margin "40px 0px 0px 0px"}}
      [:span {:class "input-group-addon"} "Size"]
      [:input {:class "form-control" :type "number" :on-change #(reset! matrix-size (-> % .-target .-value))}]]
     [:button {:class    "btn btn-primary"
               :style    {:margin "40px 0px 0px 0px"}
               :on-click #(do (re-frame/dispatch [:set-matrix-size @matrix-size])
                              (reset! step "game"))} "Start"]]))

(defn play-game []
  (let [matrix-size (re-frame/subscribe [:matrix-size])]
    [:div {:class "container"}
     [:h2 "Select the squares that don't have mines"]
     [:div {:style {:background-color "red" :padding "30px 30px 30px 30px"}}
      [:div {:style {:width "600px" :height "600px"}}
       (for [cell (range (* matrix-size matrix-size))]
         [:div
          {:style {:width (str (/ 100 matrix-size) "%") :height (str (/ 100 matrix-size) "%") :background-color "yellow"}}]
         )]]]))



(defn home-panel []
  (let [name (re-frame/subscribe [:name])
        step (r/atom "size-selection")]
    (fn []
      [:div {:class "container" :style {:margin "100px 0px 0px 0px" :text-align "center"}}
       (condp = @step
         "size-selection" [size-selection step]
         "game" [play-game]
         )])))


;; about

(defn about-panel []
  (fn []
    [:div "This is the About Page."
     [:div [:a {:href "#/"} "go to Home Page"]]]))


;; main

(defn- panels [panel-name]
  (case panel-name
    :home-panel [home-panel]
    :about-panel [about-panel]
    [:div]))

(defn show-panel [panel-name]
  [panels panel-name])

(defn main-panel []
  (let [active-panel (re-frame/subscribe [:active-panel])]
    (fn []
      [show-panel @active-panel])))
