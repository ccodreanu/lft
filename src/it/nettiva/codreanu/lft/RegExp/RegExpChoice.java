package it.nettiva.codreanu.lft.RegExp;

import it.nettiva.codreanu.lft.NFA;

public class RegExpChoice implements RegExp {

    private RegExp e1;
    private RegExp e2;

    public RegExpChoice(RegExp e1, RegExp e2) {
        this.e1 = e1;
        this.e2 = e2;
    }

    public NFA compile() {
        NFA       a = new NFA(2);
        final int n = a.append(e1.compile());        //n è lo stato iniziale di e1
        final int m = a.append(e2.compile());        //m è lo stato iniziale di e2

        a.addMove(0, NFA.EPSILON, n);                //si aggiunge una mossa da 0 a n
        a.addMove(0, NFA.EPSILON, m);                //si aggiunge una mossa da 0 a m
        //si avrà quindi che da 0 si potrà 'scegliere' se andare in e1 o in e2
        //dagli stati finali di e1 e e2 ci si ricollega allo stato finale del NFA risultante
        for (int j = 0; j < e1.compile().numberOfStates(); j++) {
            if (e1.compile().finalState(j)) {
                a.addMove(n + j, NFA.EPSILON, 1);
            }
        }
        for (int j = 0; j < e2.compile().numberOfStates(); j++) {
            if (e2.compile().finalState(j)) {
                a.addMove(m + j, NFA.EPSILON, 1);
            }
        }
        a.addFinalState(1);
        return a;
    }
}
