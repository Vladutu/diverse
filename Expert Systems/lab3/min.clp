(deffacts facts
  (sir 1 7 4 2 9 3)
)

(defrule min
  (sir $? ?e $?)
  (not (sir $? ?x&:(> ?e ?x) $?))
  =>
  (assert (min ?e))
)