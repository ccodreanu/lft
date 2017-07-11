package it.nettiva.codreanu.lft;

import java.util.HashSet;
import java.util.Stack;

public class TestDFA {

    public static void main(String[] args) {

        DFA aDFA = new DFA(4);

        aDFA.setMove(0, '1', 0);
        aDFA.setMove(0, '0', 1);
        aDFA.setMove(1, '1', 0);
        aDFA.setMove(1, '0', 2);
        aDFA.setMove(2, '1', 0);
        aDFA.setMove(2, '0', 3);
        aDFA.setMove(3, '1', 3);
        aDFA.setMove(3, '0', 3);

        aDFA.addFinalState(3);

        Stack<String> ls = new Stack<>();

        ls.push("010101");
        ls.push("10001");
        ls.push("10214");
        ls.push("00000");
        ls.push("010101");

        for (String s : ls) {
            System.out.println(s + " " + aDFA.scan(s));
        }
        System.out.println("Completo: " + aDFA.complete());


        DFA bDFA = new DFA(5);
        bDFA.setMove(0, '1', 2);
        bDFA.setMove(0, '0', 1);
        bDFA.setMove(1, '1', 3);
        bDFA.setMove(1, '0', 1);
        bDFA.setMove(2, '1', 2);
        bDFA.setMove(2, '0', 2);
        bDFA.setMove(3, '1', 3);
        bDFA.setMove(3, '0', 3);
        bDFA.setMove(4, '1', 2);
        bDFA.setMove(4, '0', 3);
        bDFA.addFinalState(3);

        bDFA.toDOT("bDFA");

        System.out.println("Sink: " + bDFA.sink().toString());

        DFA cNFA = new DFA(5);

        cNFA.setMove(0, '0', 0);
        cNFA.setMove(0, '1', 1);
        cNFA.setMove(1, '1', 1);
        cNFA.setMove(1, '0', 2);
        cNFA.setMove(2, '1', 3);
        cNFA.setMove(2, '0', 2);
        cNFA.setMove(3, '1', 3);
        cNFA.setMove(3, '0', 2);

        cNFA.addFinalState(1);
        cNFA.addFinalState(3);
        System.out.println("Equivalenza " + aDFA.equivalentTo(cNFA));

        DFA multiplo = new DFA(8);
        for (int i = 48; i < 58; i++) {
            multiplo.setMove(0, (char) i, 2);
        }
        multiplo.setMove(0, '+', 1);
        multiplo.setMove(0, '-', 1);
        multiplo.setMove(0, '.', 3);

        multiplo.setMove(1, '.', 3);
        for (int i = 48; i < 58; i++) {
            //System.out.println(i+" "+(char)i);
            multiplo.setMove(1, (char) i, 2);
        }

        multiplo.setMove(2, '.', 3);
        for (int i = 48; i < 58; i++) {
            multiplo.setMove(2, (char) i, 2);
        }
        multiplo.setMove(2, 'e', 5);

        for (int i = 48; i < 58; i++) {
            multiplo.setMove(3, (char) i, 4);
        }

        multiplo.setMove(4, 'e', 5);
        for (int i = 48; i < 58; i++) {
            multiplo.setMove(4, (char) i, 4);
        }

        multiplo.setMove(5, '+', 6);
        multiplo.setMove(5, '-', 6);
        for (int i = 48; i < 58; i++) {
            multiplo.setMove(5, (char) i, 7);
        }

        for (int i = 48; i < 58; i++) {
            multiplo.setMove(6, (char) i, 7);
        }

        for (int i = 48; i < 58; i++) {
            multiplo.setMove(7, (char) i, 7);
        }
        multiplo.addFinalState(2);
        multiplo.addFinalState(4);
        multiplo.addFinalState(7);
        multiplo.toDOT("multiplo");

        System.out.println("Scan 5e7 " + multiplo.scan("5e7"));
        System.out.println("Scan 5s1 " + multiplo.scan("5s1"));
        //multiplo.toJava("Ciao");
    }

}
