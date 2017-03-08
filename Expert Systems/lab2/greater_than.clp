(deffacts facts
        (sir 7 3 8 2 4 11 78 234 3 14)
        (calcul_elemente 0)
        (start)
        (finish)
)

(defrule start
        (declare (salience 5))        
        ?s <- (start)
=>
        (retract ?s)
        (printout t "Valoarea lui k = ") 
        (assert (element = (read))) 
)

(defrule greater_than
        (sir $? ?e $?)
        (element ?k&:(> ?e ?k))
        ?adr <- (calcul_elemente ?n)
=>
        (assert (calcul_elemente (+ ?n 1)))
        (retract ?adr)
)