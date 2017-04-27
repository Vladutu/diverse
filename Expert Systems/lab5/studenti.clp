(deftemplate student
  (slot address)
  (slot name)
  (slot age)
  (multislot grades)
)

(deffacts FDB
  (subjects AIR MAPC AI MAD DSA)
  (student (address Craiova) (name Ionel) (age 23) (grades -1 5 10 10 4))
  (student (address Craiova) (name Gigel) (age 21) (grades -1 -1 6 5 4))
  (student (address Pitesti) (name Popescu) (age 23) (grades 10 10 9 10 8))
  (student (address Caracal) (name Adriana) (age 22) (grades 6 -1 8 9 9))
  (student (address Bucuresti) (name Andreea) (age 23) (grades 4 5 8 10 4))
  (student (address Craiova) (name Marius) (age 23) (grades 7 8 8 7 9))
)
