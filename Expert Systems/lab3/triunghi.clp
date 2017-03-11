(defrule init
  (not (coordonate $?))
=>
  (open "triunghi_date.txt" date "r")
  (bind ?line (readline date))
  (bind ?vals (explode$ ?line))
  (assert (coordonate ?vals))
  (close date)
)

(defrule numeric_coordinates
  (not (not_numeric))
  (coordonate $? ?e $?)
  (test (not (numberp ?e)))
=>
  (printout t "Coordonatele nu sunt numerice" crlf)
  (assert (not_numeric))
)

(defrule is_triangle
  (coordonate ?x1 ?y1 ?x2 ?y2 ?x3 ?y3)

=>

)
