(ns mkwords.core
  (:require [reagent.core :as r]
            [reagent.session :as session]
            [clojure.string :as string]
            [clojure.set :refer [rename-keys]]
            [secretary.core :as secretary :include-macros true]
            [hazard.core :as hazard]
            [ajax.core :as ajax]
            [accountant.core :as accountant]
            [mkwords.constants :refer [$strings $limits $defaults
                                      $limit-chars $limit-words
                                      $default-chars]]))

(defonce state (r/atom $defaults))

(defn- generate-words!
  [& args]
  (try
    (let [words (apply hazard/words args)]
      (swap! state assoc :candidate-count (:candidate-count (meta words)))
      (swap! state assoc :words words)
      (swap! state assoc :error nil))
    (catch js/Error e
      (swap! state assoc :error e))))

;; -------------------------
;; Components

(defn number-input [param value {:keys [min max]}]
  [:input {:type "number" :value value :min min :max max
           :on-change (fn [e]
                        (swap! state assoc param (int (.-target.value e))))}])

(defn options-view []
  (let [show-options (r/atom false)]
    (fn [num-words min-chars max-chars]
      [:span
       (if @show-options
         [:button {:class "button small"
                   :on-click #(reset! show-options false)} "Hide Options"]
         [:button {:class "button small"
                   :on-click #(reset! show-options true)} "Show Options"])
       (when @show-options
         [:div#options
          [:div
           [:label {:for :num} (:num-words $strings)]
           [number-input :num-words num-words $limit-words]]
          [:div
           [:label {:for :min} (:min-chars $strings)]
           [number-input :min-chars min-chars $limit-chars]]
          [:div
           [:label {:for :max} (:max-chars $strings)]
           [number-input :max-chars max-chars $limit-chars]]])])))

(defn word-view []
  (let [{:keys [num-words min-chars max-chars
                word-list candidate-count words error]} @state
        show-options (r/atom false)]
    [:div {:class "container"}
     [:div {:class "grid"}
      [:div {:class "banner row"}
       [:div {:class "span twelve"}
        (if (not (nil? error))
          [:h4 (str error)]
          [:h4 (string/join " " words)
           [:small (str "generated from " candidate-count " candidate words")]])
        [:div
         [:button {:class "button primary small"
                   :on-click #(generate-words! word-list num-words
                                               {:min min-chars
                                                :max max-chars})} "Generate"]
         [options-view num-words min-chars max-chars]]]]]]))

;; -------------------------
;; Views

(defn home-page []
  (let [{:keys [word-list]} @state]
    (if (nil? word-list)
      [:div {:class "banner"} [:em (:loading $strings)]]
      [word-view])))

(defn error-page []
  [:div [:h2 "Error"]
   [:div "An error occurred; sorry about that."]])

(defn current-page []
  [:div [(session/get :current-page)]])

;; -------------------------
;; Routes

(secretary/defroute "/" []
  (session/put! :current-page #'home-page))

;; -------------------------
;; Initialize app

(defn- on-words [response]
  (let [word-list (string/split-lines (str response))
        max-length (apply max (map count word-list))]
    ; create a default result
    (generate-words! word-list (:num-words $defaults) $default-chars)
    ; set the word list
    (swap! state assoc :word-list word-list)))

(defn- on-error [response]
  (session/put! :current-page #'error-page))

(defn mount-root []
  (r/render [current-page] (.getElementById js/document "app")))

(defn init! []
  (accountant/configure-navigation!)
  (accountant/dispatch-current!)
  (mount-root)
  (ajax/GET "/vendor/mwords/113809of.fic" {:handler on-words
                                           :error-handler on-error}))
