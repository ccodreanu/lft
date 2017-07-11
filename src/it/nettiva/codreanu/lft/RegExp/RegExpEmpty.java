package it.nettiva.codreanu.lft.RegExp;

import it.nettiva.codreanu.lft.NFA;

public class RegExpEmpty implements RegExp {
    public NFA compile() {
        NFA a = new NFA(2);
        a.addFinalState(1);
        return a;
    }
}