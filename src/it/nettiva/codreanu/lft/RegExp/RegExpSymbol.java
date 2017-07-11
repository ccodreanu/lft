package it.nettiva.codreanu.lft.RegExp;

import it.nettiva.codreanu.lft.NFA;

public class RegExpSymbol implements RegExp {
    private char ch;

    public RegExpSymbol(char ch) {
        this.ch = ch;
    }

    public NFA compile() {
        NFA a = new NFA(2);
        a.addMove(0, ch, 1);
        a.addFinalState(1);
        return a;
    }
}