(deffacts facts
  (sir 7 3 8 2 4 11 78 234 3 14)
  (calcul_elemente 0)
  (start)
  (finish)
)

(defrule start
  (declare (salience 5))
  (sir $?t)
  ?s <- (start)
=>
  (retract ?s)
  (printout t "Valoarea lui k = ")
  (assert (element = (read)))
  (assert (sir_temp $?t))
)

(defrule greater_than
  ?s_adr <- (sir_temp $?h ?e $?t)
  (element ?k)
  (test (> ?e ?k))
  ?adr <- (calcul_elemente ?n)
=>
  (assert (calcul_elemente (+ ?n 1)))
  (assert (sir_temp $?h $?t))
  (retract ?adr ?s_adr)
)
