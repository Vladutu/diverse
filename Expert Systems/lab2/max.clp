(deffacts numbers
        (numar 4)
        (numar 5)
        (numar 10)
)

(defrule max_nr
        (numar ?n)
        (not (numar ?x&:(> ?x ?n)))
=>
        (assert (maxim ?n))
)