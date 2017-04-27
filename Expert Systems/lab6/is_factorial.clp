(defrule read_number
  (declare (salience 10))
=>
  (printout t "Introduceti numarul: x = ")
  (bind ?nr (read))
  (assert (number ?nr))
)

(deffunction is_factorial(?n)
  (bind ?temp ?n)
  (bind ?i 2)
  (while (and (> ?temp 1) (= (mod ?temp ?i) 0))
    (bind ?temp (/ ?temp ?i))
    (bind ?i (+ ?i 1))
  )
  (if (= ?temp 1)
    then
      (printout t "Numarul este factorial" crlf)
    else
      (printout t "Numarul nu este factorial" crlf)
  )
)

(defrule main
  (number ?nr)
=>
  (is_factorial ?nr)
)
