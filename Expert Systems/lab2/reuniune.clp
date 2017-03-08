(deffacts multimi
        (multime_1 5 3 7 4 1 6 2 2 2)
        (multime_2 9 6 1 8 5 3 1 7 3 1)
        (reuniune)
)

(defrule init
        (declare (salience 10))

        (multime_1 $? ?e $?)
        ?veche <- (reuniune $?l)
        (not (reuniune $? ?e $?))
=>
        (retract ?veche)
        (assert (reuniune $?l ?e))
)

(defrule reunion
        (multime_2 $?h ?e $?t)
        ?veche <- (reuniune $?l)
        (not (reuniune $? ?e $?))
=>
        (retract ?veche)
        (assert (reuniune $?l ?e))
)

