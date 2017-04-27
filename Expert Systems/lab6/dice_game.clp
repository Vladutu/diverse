(defrule read_number
  (declare (salience 10))
=>
  (printout t "Introduceti numarul de jocuri: x(impar) = ")
  (bind ?nr (read))
  (assert (games ?nr))
)

(deffunction roll_dice()
  (bind ?roll (+ (mod (random) 6) 1))
  (return ?roll)
)

(defrule play_game
  (games ?n)
=>
  (bind ?i ?n)
  (bind ?round 1)
  (bind ?c_score 0)
  (bind ?u_score 0)
  (while (> ?i 0)
    (printout t "Runda " ?round crlf)
    (bind ?comp (roll_dice))
    (bind ?usr (roll_dice))
    (printout t "Calculatorul a dat " ?comp crlf)
    (printout t "Tu ai dat " ?usr crlf)

    (if (= ?comp ?usr)
      then
        (printout t "Egalitate. Runda se repeta." crlf)
      else
        (bind ?i (- ?i 1))
        (bind ?round (+ ?round 1))
        (if (> ?comp ?usr)
          then
            (bind ?c_score (+ ?c_score 1))
            (printout t "Calculatorul a castigat runda" crlf)
          else
            (bind ?u_score (+ ?u_score 1))
            (printout t "Tu ai castigat runda" crlf)
        )
    )
  )
  (printout t "Scor final" crlf)
  (printout t "Tu:" ?u_score "-" ?c_score ":Calculator" crlf)
)
