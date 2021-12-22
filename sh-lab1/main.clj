
;; Опишите функцию, лениво вычисляющую дисперсию внутри 
;; плавающего окна в бесконечной числовой последовательности

(defn sliding-dispersion [arr n]
    (let [subarr (take n arr)]
        (when-not (< (count subarr) n)
            (let [avg (double (/ (reduce + subarr) n))]
                (lazy-seq 
                    (cons 
                        (/ (reduce + (map #(Math/pow (- avg %) 2) subarr)) n)
                        (sliding-dispersion (rest arr) n)))))))

(println (sliding-dispersion '(2 5 2 1 3 5 0) 3))
(println take 10 (sliding-dispersion (cycle '(0 1 2)) 3))) ;<- тест на бесконечность
