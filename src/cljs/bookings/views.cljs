(ns bookings.views
  (:require [re-frame.core :as re-frame]
            [reagent.core :as r])
  )


(defn home-panel []
  (let [name (re-frame/subscribe [:name])]

    (let [matrix-size (r/atom 0)]
      (fn []
        [:div {:class "container" :style {:margin "100px 0px 0px 0px" :text-align "center"}}
         [:div [:h2 "Select the size of matrix you want to play with"]
          [:div {:class "input-group" :style {:margin "40px 0px 0px 0px"}}
           [:span {:class "input-group-addon"} "Size"]
           [:input {:class "form-control" :type "number" :on-change #(reset! matrix-size (-> % .-target .-value))}]]
          [:button {:class "btn btn-primary" :style {:margin "40px 0px 0px 0px"} :on-click #(re-frame/dispatch [:set-matrix-size @matrix-size])} "Start"]
          ]]))))


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
