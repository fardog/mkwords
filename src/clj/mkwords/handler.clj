(ns mkwords.handler
  (:require [clojure.java.io :as io]
            [compojure.core :refer [GET defroutes]]
            [compojure.route :refer [not-found resources]]
            [ring.middleware.defaults :refer [site-defaults wrap-defaults]]
            [prone.middleware :refer [wrap-exceptions]]
            [ring.middleware.reload :refer [wrap-reload]]
            [environ.core :refer [env]]
            [mkwords.api :refer [generate]]
            [mkwords.templates :refer [create-markdown-page loading-page]]))

(def api-page (io/resource "pages/api.md"))
(def about-page (io/resource "pages/about.md"))

(defroutes api
  (GET "/api/v1/generate" [] generate))

(defroutes routes
  (GET "/" [] loading-page)
  (GET "/api" [] (create-markdown-page "API" api-page))
  (GET "/api/*" [] api)
  (GET "/about" [] (create-markdown-page "About" about-page))

  (resources "/")
  (not-found "Not Found"))

(def app
  (let [handler (wrap-defaults #'routes site-defaults)]
    (if (env :dev) (-> handler wrap-exceptions wrap-reload) handler)))
