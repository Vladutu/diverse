(defrule read_number
  (declare (salience 10))
=>
  (printout t "Introduceti numarul: x = ")
  (bind ?nr (read))
  (assert (number ?nr))
)

(deffunction factorial(?number)
  (bind ?result 1)
  (loop-for-count (?i 1 ?number)
    (bind ?result (* ?result ?i))
  )
  (printout t "Factorial of " ?number " is " ?result crlf)
)

(defrule main
  (number ?nr)
=>
  (factorial ?nr)
)
