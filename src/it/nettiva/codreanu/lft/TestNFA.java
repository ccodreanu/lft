package it.nettiva.codreanu.lft;

public class TestNFA {

    public static void main(String[] args) {

        NFA aNFA = new NFA(5);
        aNFA.addMove(0, '0', 0);
        aNFA.addMove(0, '1', 1);
        aNFA.addMove(1, '1', 1);
        aNFA.addMove(1, '0', 2);
        aNFA.addMove(2, '1', 3);
        aNFA.addMove(2, '0', 2);
        aNFA.addMove(3, '1', 3);
        aNFA.addMove(3, NFA.EPSILON, 2);
        aNFA.addFinalState(3);

        NFA bNFA = new NFA(2);
        bNFA.addMove(0, '0', 1);
        bNFA.addMove(0, '1', 1);
        bNFA.addFinalState(1);

        bNFA.append(aNFA);

        aNFA.toDOT("aDFA");
        bNFA.toDOT("bDFA");

        System.out.println("E-Close: " + aNFA.epsilonClosure(3).toString());

        NFA n;
        DFA d;

        int sup = 3;
        for (int i = 0; i <= sup; i++) {
            n = NFA.nth(i);
            System.out.println("# NFA.nth(" + i + ") " + n.numberOfStates());
            d = NFA.nth(i).dfa();
            System.out.println("# NFA.nth(" + i + ").dfa() " + d.numberOfStates());
            d = NFA.nth(i).dfa().minimize();
            System.out.println("# NFA.nth(" + i + ").dfa().minimize() " + d.numberOfStates());
        }
    }
}
