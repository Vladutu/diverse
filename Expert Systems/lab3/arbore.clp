(deftemplate persoana
  (slot nume)
  (multislot copii)
)

(deffacts arbore
  (persoana (nume Liviu) (copii Vlad Maria Nelu))
  (persoana (nume Vlad) (copii Oana Mircea))
  (persoana (nume Maria) (copii Dan))
  (persoana (nume Nelu) (copii Paul Radu Sorin))
  (start)
)

(defrule start
  (declare (salience 5))
  ?s <- (start)
=>
  (retract ?s)tom
  (printout t "Numele cautat este = ")
  (bind ?raspuns (read))
  (assert (nume_cautat ?raspuns))
)

(defrule veri
  (nume_cautat ?n)
  (persoana (nume ?parinte) (copii $? ?n $?))
  (persoana (nume ?bunic) (copii $?l ?parinte $?r))
  (persoana (nume ?bunic) (copii $? ?cop $?))
  (test (neq ?parinte ?cop))
  ;in ?cop avem unchii celui cautat
  (persoana (nume ?cop) (copii $?veri))
=>
  (printout t "Veri = " $?veri crlf)
)
