(ns laba1.core)

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

(println (get-numbers [:start 1 2 :end :start 5 6 :end :start 8 9 :end]))


