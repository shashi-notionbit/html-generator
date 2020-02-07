(ns web.core
  (:require [clojure.data.csv :as csv]
            [net.cgrand.enlive-html :as enl]
            [clojure.java.io :as io]
            [clojure.string :as str])
  (:gen-class))

(defn parse-csv-file
  [filename]
  (-> (slurp filename)
      csv/read-csv))

(defn find-csv-files
  []
  (->> (io/file "resources/website/_assets/")
       file-seq
       (remove #(.isDirectory %))))

(defn load-csvs
  []
  (map parse-csv-file (find-csv-files)))

(defn generate-rows
  [csv-row]
  (let [[title hours] csv-row
        title-words (str/split title #" ")
        [course-number & full-title-words] title-words
        full-title (str/join " " full-title-words)]
    [:tr
     [:td [:strong (str course-number " ")] full-title]
     [:td hours]]))

(defn generate-table
  [csv-rows]
  (let [[header & rows] csv-rows]
    [:table {:border 0 :cellspacing 0 :cellpadding 0}
     [:tr (for [h header] [:th h])]
     [:tbody (for [row rows]
               (generate-rows row))]]))

(enl/deftemplate add-table "website/programs/graphic-design.htm"
                 [table-html-data]
                 [:article#mainContent] (enl/append (enl/html table-html-data)))

(defn render
  []
  (->> (load-csvs)
       (map generate-table)
       add-table
       (apply str)))

(defn -main
  []
  (spit "resources/website/programs/output.html" (render))
  (println "Generated HTML"))