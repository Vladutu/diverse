(deffacts fibonacci
        (fib 0 0)
        (fib 1 1)
        (nr_elemente 20)
        (last_2_pos 0 1)
)

(defrule fib
        ?ne_adr <- (nr_elemente ?n)
        (test (> ?n 0))

        ?last_adr <- (last_2_pos ?x ?y)
        (fib ?pos1&:(= ?pos1 ?x) ?val1)
        (fib ?pos2&:(= ?pos2 ?y) ?val2)

=>
        (assert (nr_elemente (- ?n 1)))
        (retract ?ne_adr)
        (assert (fib(+ ?y 1) (+ ?val1 ?val2)))
        (assert (last_2_pos ?y (+ ?y 1)))
        (retract ?last_adr)
)

(defrule clear
        ?a1 <- (nr_elemente ?n&:(= ?n 0))
        ?a2 <- (last_2_pos ? ?)
=>
        (retract ?a1 ?a2)
)