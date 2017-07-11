package it.nettiva.codreanu.lft.RegExp;

import it.nettiva.codreanu.lft.NFA;

public class RegExpStar implements RegExp {

    private RegExp e;

    public RegExpStar(RegExp e) {
        this.e = e;
    }

    public NFA compile() {
        NFA       a    = new NFA(2);
        NFA       eNFA = e.compile();
        final int n    = a.append(eNFA);            //n è lo stato iniziale del NFA di e
        a.addMove(0, NFA.EPSILON, n);
        for (int j = 0; j < eNFA.numberOfStates(); j++) {
            if (eNFA.finalState(j)) {
                a.addMove(n + j, NFA.EPSILON, 1);
                a.addMove(n + j, NFA.EPSILON, n);
            }
        }
        a.addMove(0, NFA.EPSILON, 1);
        a.addFinalState(1);
        return a;
    }
}
