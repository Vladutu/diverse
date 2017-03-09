(deffacts list
  (sir_numere 4 3 1 5 2)
)

(defrule sort
  ?adr <- (sir_numere $?h ?x ?y $?t)
  (test (> ?x ?y))
=>
  (assert (sir_numere $?h ?y ?x $?t))
  (retract ?adr)
)
