(defrule read_numbers
  (declare (salience 10))
=>
  (printout t "Introduceti numarul de elemente: n = ")
  (bind ?n (read))
  (printout t "A[1] = ")
  (bind ?e (read))
  (bind $?vals ?e)
  (loop-for-count (?i 2 ?n)
    (printout t "A[" ?i "] = ")
    (bind ?e (read))
    (bind $?vals (create$ $?vals ?e))
  )
  (assert (array $?vals))
)

(defrule reverse
  (array $?vals)
=>
  (printout t "Sirul inversat ")
  (bind ?i (length$ $?vals))
  (while (> ?i 0)
    (printout t (nth$ ?i $?vals) " ")
    (bind ?i (- ?i 1))
  )
  (printout t crlf)
)
