(deffacts facts
  (list 6 4 7 3 1 3 6 3 5)
)

(defrule sum
  (list $?vals)
=>
  (bind ?r 0)
  (loop-for-count (?i 1 (length$ $?vals))
    (bind ?r (+ ?r (nth$ ?i $?vals)))
  )
  (printout t "Suma este " ?r crlf)
)
