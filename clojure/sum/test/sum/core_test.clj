(ns sum.core-test
  (:require [clojure.test :refer :all]
            [sum.core :refer :all]))

(deftest test1
  (testing "Recursive sum works"
    (is (= (recursive-sum [1, 2, 3, 4]) 10))))

(deftest test2
  (testing "Recursive sum with reduce works"
    (time (is (= (recursive-sum-reduce [1, 2, 3, 4]) 10)))))

(deftest test3
  (testing "Parallel Recursive sum with reduce works"
    (time (is (= (parallel-recursive-sum [1, 2, 3, 4]) 10)))))
