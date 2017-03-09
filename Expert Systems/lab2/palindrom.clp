(deftemplate numar
  (slot valoare_numar)
)

(deffacts facts
  (numar (valoare_numar 122321))
  (vector)
)

(defrule split_nr
  (declare (salience 5))
  ?n_adr <- (numar (valoare_numar ?val&:(> ?val 0)))
  ?v_adr <- (vector $?v)
=>
  (assert (vector (mod ?val 10) $?v))
  (assert (numar (valoare_numar (div ?val 10))))
  (retract ?n_adr ?v_adr)
)

(defrule remove_eq_elems
  ?v_adr <- (vector ?e $?middle ?e)
=>
  (assert (vector $?middle))
  (retract ?v_adr)
)

(defrule is_pal
  ?v_adr <- (vector $?v)
  (test (< (length$ $?v) 2))
=>
  (printout t "Is palindrom" crlf)
  (retract ?v_adr)
)

(defrule is_not_pal
  ?v_adr <- (vector $?v)
  (test (>= (length$ $?v) 2))
=>
  (printout t "Is not palindrom" crlf)
  (retract ?v_adr)
)
