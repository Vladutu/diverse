(deftemplate persoana
        (slot nume)
        (multislot prenume)
)

(deffacts Persoane
        (persoana (nume Popescu) (prenume Marian Ionescu))
        (persoana (nume Vladutu) (prenume Georgian Alexandru))
        (persoana (nume Urdinineacu) (prenume Andrei Georgian))
)