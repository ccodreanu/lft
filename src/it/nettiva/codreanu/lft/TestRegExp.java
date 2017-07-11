package it.nettiva.codreanu.lft;

import it.nettiva.codreanu.lft.RegExp.*;

public class TestRegExp {

    public static void main(String[] args) {

//        RegExp aRegExp = new RegExpSequence(new RegExpSequence(new RegExpSequence(
//                new RegExpSymbol('/'),
//                new RegExpSymbol('*')),
//                new RegExpStar(
//                    new RegExpChoice(
//                            new RegExpChoice(new RegExpSymbol('/'), new RegExpSymbol('c')),
//                            new RegExpSequence(
//                                    new RegExpStar(new RegExpSymbol('*')),
//                                    new RegExpSymbol('c')
//                            )))
//        ),
//                new RegExpSequence(
//                        new RegExpSequence(
//                                new RegExpStar(new RegExpSymbol('*')),
//                                new RegExpSymbol('*')
//                        ),
//                        new RegExpSymbol('/')
//                )
//        );
//
//        aRegExp.compile().toDOT("nfa");
//        aRegExp.compile().dfa().toDOT("dfa");
//        aRegExp.compile().dfa().minimize().toDOT("minDfa");
//
//        // Automa strano
//        new RegExpStar(new RegExpChoice(new RegExpSymbol('a'), new RegExpSymbol('b'))).compile().dfa().minimize().toDOT("strangeDfa");

        RegExp aRE = new RegExpSymbol('a');
        RegExp bRE = new RegExpSymbol('b');

        aRE.compile().toDOT("aRE");
        bRE.compile().toDOT("bRE");
        RegExp rg = new RegExpSequence(aRE, bRE);
//        RegExp rg = new RegExpChoice(aRE, bRE);
        rg.compile().toDOT("abRE");

    }
}
