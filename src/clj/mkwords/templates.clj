(ns mkwords.templates
  (:require [hiccup.core :refer [html]]
            [hiccup.page :refer [include-js include-css]]
            [endophile.core :refer [mp to-clj html-string]]
            [endophile.hiccup :refer [to-hiccup]]
            [environ.core :refer [env]]))

(defn- page-shell
  ([content]
   (page-shell content false))
  ([content include-app?]
  (html
   [:html
    [:head
     [:meta {:charset "utf-8"}]
     [:meta {:name "viewport"
             :content "width=device-width, initial-scale=1"}]
     [:title "mkwords"]
     (include-css "css/site.css")]
    [:body
     [:div.navbar
      [:div.container
       [:a {:href "/"}
        [:h4.pull-left "mkwords"]]
       [:ul.pull-right
        [:li [:a {:href "/"} "Generate"]]
        [:li [:a {:href "/api"} "API"]]
        [:li [:a {:href "/about"} "About"]]]]]
     content
     (if include-app?
       (include-js "js/app.js"))]])))

(def mount-target
  ^{:private true}
  [:div#app
   [:div {:class "banner"} [:em "loading application"]]])

(defn loading-page
  [_]
  (page-shell
    [:div.container
     [:div.grid
      [:div.row
       [:div.span.twelve
        mount-target]]]]
    true))

(defn create-markdown-page
  [title mkd-file]
  (let [mkd (slurp mkd-file)
        mkd-parsed (mp mkd)]
    (fn [_]
      (page-shell
        [:div.container.condensed
         [:div.grid
          [:div.row
           [:div.span.twelve 
            [:div.markdown (to-hiccup mkd-parsed)]]]]]))))
