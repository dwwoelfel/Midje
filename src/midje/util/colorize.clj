;; -*- indent-tabs-mode: nil -*-

(ns midje.util.colorize
  (:require [colorize.core :as color])
  (:use [midje.util.ecosystem :only [getenv on-windows?]]))


(defn colorize-choice []
  (.toUpperCase (or (getenv "MIDJE_COLORIZE")
                    (str (not (on-windows?))))))

(defn colorizing? [] 
  (not (= (colorize-choice) "FALSE")))

(case (colorize-choice)
  "TRUE" (do
           (def fail color/red)
           (def pass color/green)
           (def note color/cyan))

  "REVERSE" (do
              (def fail color/red-bg)
              (def pass color/green-bg)
              (def note color/cyan-bg))

  (do
    (def fail identity)
    (def pass identity)
    (def note identity)))

(defn colorized? [s] 
  (.startsWith s "\033["))