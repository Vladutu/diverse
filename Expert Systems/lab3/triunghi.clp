(defrule init
  (declare (salience 5))
=>
  (open "triunghi_date.txt" date "r")
  (bind ?line (readline date))
  (bind ?vals (explode$ ?line))
  (assert (coordonate ?vals))
  (close date)
)

(defrule numeric_coordinates
  (declare (salience 4))
  (not (not_numeric))
  (coordonate $? ?e $?)
  (test (not (numberp ?e)))
=>
  (printout t "Coordonatele nu sunt numerice" crlf)
  (assert (not_numeric))
)

(defrule calculate_length
  (declare (salience 3))
  (coordonate ?x1 ?y1 ?x2 ?y2 ?x3 ?y3)
  (not (not_numeric))
=>
  (bind ?a (** (+ (** (- ?x2 ?x1) 2) (** (- ?y2 ?y1) 2)) 0.5))
  (bind ?b (** (+ (** (- ?x3 ?x1) 2) (** (- ?y3 ?y1) 2)) 0.5))
  (bind ?c (** (+ (** (- ?x2 ?x3) 2) (** (- ?y2 ?y3) 2)) 0.5))
  (assert (a ?a) (b ?b) (c ?c))
)

(defrule is_triangle
  (declare (salience 2))
  (a ?a) (b ?b) (c ?c)
  (or (test (>= ?a (+ ?b ?c))) (test (>= ?b (+ ?a ?c))) (test (>= ?c (+ ?a ?b))))
=>
  (printout t "Coordonatele nu reprezinta un triunghi" crlf)
  (assert (not_triangle))
)

(defrule aria
  (declare (salience 1))
  (not (not_triangle))
  (not (not_numeric))
  (a ?a) (b ?b) (c ?c)
=>
  (bind ?p (/ (+ (+ ?a ?b) ?c) 2))
  (bind ?aria (** (* (* (* ?p (- ?p ?a)) (- ?p ?b)) (- ?p ?c)) 0.5))
  (assert (aria ?aria))
  (printout t "Aria este " ?aria crlf)
)
