(ns sum.core
  (:gen-class)
  (:require [clojure.core.reducers :as reducers]))

(defn recursive-sum [numbers]
  [numbers accumulated]
  (if (empty? numbers)
    0
    (loop [accumulated 0]
      (recur (rest numbers) (+ (first numbers) accumulated)))))

(defn recursive-sum-reduce [numbers]
  (reduce + numbers))

(defn parallel-recursive-sum [numbers]
  (reducers/fold + numbers))
