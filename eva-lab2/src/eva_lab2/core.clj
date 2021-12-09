(ns eva-lab2.core
    (:require [clojure.core.async :refer [>! <! <!! chan go go-loop close!]]))

(defn vec-to-channel [vec]
    (let [channel (chan 10)]
        (go
            (doseq [x vec]
                (>! channel x))
            (close! channel))
        channel))

(defn redirect-filter [input-channel output-channel delta]
    (go-loop [prev nil]
        (if-let [x (<! input-channel)]
            (do
                (when-not (nil? prev)
                    (when (> (Math/abs (- x prev)) delta)
                        (>! output-channel x)))
                (recur x))
            (close! output-channel))))

(defn -main [& _]
    ;; Напишите функцию которая читает данные из одного канала
    ;; и пишет их в другой только в том случае если
    ;; новое значение отличается от предыдущего более чем на заданную величину

    (let [input-channel (vec-to-channel [-5 -1 0 8 3 2 4 1 3 7 4 7 0])
          output-channel (chan 10)]
        (redirect-filter input-channel output-channel 3)
        (<!! (go-loop []
                 (when-let [x (<! output-channel)]
                     (println x)
                     (recur))))))