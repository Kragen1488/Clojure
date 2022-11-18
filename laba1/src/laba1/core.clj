(ns laba1.core
  (:require [clojure.core.async :as async :refer [<! >! <!! >!! timeout chan onto-chan alts! alts!! go-loop go close!]]))


;-------------------------------------------------------------------------------------------------------|
;Lab 1
;-------------------------------------------------------------------------------------------------------|

(defn get-numbers
  ([coll] (get-numbers [] coll []))
  ([res coll pred]
   (lazy-seq
     (if-not(empty? coll)
       (if-not(= (first coll) :start)
         (if-not(= (first coll) :end)
           (if-not(= pred :end)

             (get-numbers  (conj res (first coll)) (rest coll) (first coll))


             (get-numbers res (rest coll) pred)


             )
           (cons  res
                  (get-numbers res (rest coll) (first coll))
                  )
           )
         (get-numbers [] (rest coll) (first coll))
         )
       []
       )
     )
   )
  )

(println (get-numbers [:start 1 2 :end 4 :start 5 6 :end 5 6 4 :start  7 8 9 :end]))


;-------------------------------------------------------------------------------------------------------|
;Lab 2
;-------------------------------------------------------------------------------------------------------|

(defn chtovec
  [chn]
  (go-loop [res []]
           (let [x (<!! chn)]
             (if x
               (do
                 (println x)
                 (recur (conj res x)))
               res)
             )
           )
  )

(defn foo [x, y]

  (go-loop []
           (let [message (alts! [ x   (timeout 5) ]) ]
             (if  (nil? (first message))

               (if (identical? x (last message))
                 nil
                 (do (>! y :timeout) (recur))
                 )
               (do (>! y (first message)) (recur))

               )
             )
           )


  )



(def x (chan 10))
(def y (chan 10))

(foo x y)
(>!! x 5)
(Thread/sleep 10)
(>!! x 6)
(>!! x 1)
(Thread/sleep 10)
(>!! x 9)
(close! x)
(chtovec y)

(Thread/sleep 100)
