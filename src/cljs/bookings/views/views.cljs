(ns bookings.views
  (:require [re-frame.core :as re-frame]
            [reagent.core :as r]
            [bookings.views.play_game :as play-game])
  )


(defn size-selection []
  (let [matrix-size (r/atom 0)]

    [:div {:class "container"}
     [:div {:class "col-lg-2 col-md-2"}]
     [:div {:class "col-lg-8 col-md-8"} [:h2 "Select the size of matrix you want to play with"]
      [:div {:class "input-group" :style {:margin "40px 0px 0px 0px"}}
       [:span {:class "input-group-addon"} "Size"]
       [:input {:class     "form-control" :type "number"
                :on-change #(reset! matrix-size (-> % .-target .-value))}]]
      [:button {:class    "btn btn-primary"
                :style    {:margin "40px 0px 0px 0px"}
                :on-click #(do (re-frame/dispatch [:set-matrix-size @matrix-size])
                               (re-frame/dispatch [:set-game-step "game"]))} "Start"]]
     [:div {:class "col-lg-2 col-md-2"}]]))

(defn game-results []
  (let [game-status (re-frame/subscribe [:game-status])]
    [:div {:class "container"}
     [:div {:class "col-lg-2 col-md-2"}]
     [:div {:class "col-lg-8 col-md-8"}
      (if (= "WON" @game-status)
        [:h2 "WOW! you have won"]
        [:h2 "Sorry to say but you have lost"])
      [:div {:class "input-group" :style {:margin "40px 0px 0px 0px"}}]]
     [:div {:class "col-lg-2 col-md-2"}]]))



(defn home-panel []
  (let [name (re-frame/subscribe [:name])
        step (re-frame/subscribe [:game-step])]
    (fn []
      [:div {:style {:margin "50px 0px 0px 0px" :text-align "center"}}
       (condp = @step
         "size-selection" [size-selection]
         "game" [play-game/play-game]
         "game-results" [game-results]
         )]
      )))

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
