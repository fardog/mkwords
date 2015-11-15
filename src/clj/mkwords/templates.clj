(ns mkwords.templates
  (:require [hiccup.core :refer [html]]
            [hiccup.page :refer [include-js include-css]]
            [markdown.core :refer [md-to-html-string]]
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
     (include-css "vendor/bijou/css/bijou.min.css"
                  (if (env :dev) "css/site.css" "css/site.min.css"))]
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

(defn api-page
  [_]
  (page-shell
    [:div.container.condensed
     [:div.grid
      [:div.row
       [:div.span.twelve
        [:h4 "API"]
        [:p "mkwords contains an API for generating passwords.
            The API will be documented on this page."]]]]]))

(defn create-markdown-page
  [title mkd-file]
  (let [mkd (slurp mkd-file)
        mkd-string (md-to-html-string mkd :reference-links? true)]
    (fn [_]
      (page-shell
        [:div.container.condensed
         [:div.grid
          [:div.row
           [:div.span.twelve 
            [:div.markdown mkd-string]]]]]))))
