package it.nettiva.codreanu.lft;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class DFA {

    /**
     * Numero degli states dell’automa. Ogni stato e‘ rappresentato da un numero
     * interno non negativo, lo stato con indice 0 e‘ lo stato iniziale.
     */
    private int numberOfStates;

    /**
     * Insieme degli states finali dell’automa.
     */
    private HashSet<Integer> finalStates;

    /**
     * Funzione di transizione dell’automa, rappresentata come una mappa da
     * mosse a states di arrivo.
     */
    private HashMap<Move, Integer> transitions;

    /**
     * Crea un DFA con un dato numero di states.
     *
     * @param n Il numero di states dell’automa.
     */
    public DFA(int n) {
        numberOfStates = n;
        finalStates = new HashSet<>();
        transitions = new HashMap<>();
    }

    public DFA() {
        finalStates = new HashSet<Integer>();
        transitions = new HashMap<Move, Integer>();
    }

    /**
     * Aggiunge uno stato all’automa.
     *
     * @return L’indice del nuovo stato creato
     */
    public int newState() {
        return numberOfStates++;
    }

    /**
     * Aggiunge una transizione all’automa.
     *
     * @param p  Lo stato di partenza della transizione.
     * @param ch Il simbolo che etichetta la transizione.
     * @param q  Lo stato di arrivo della transizione.
     * @return <code>true</code> se lo stato di partenza e lo stato di arrivo
     * sono validi, <code>false</code> altrimenti.
     */
    public boolean setMove(int p, char ch, int q) {
        if (!validState(p) || !validState(q)) {
            return false;
        }
        transitions.put(new Move(p, ch), q);
        return true;
    }

    /**
     * Aggiunge uno stato finale.
     *
     * @param p Lo stato che si vuole aggiungere a quelli finali.
     * @return <code>true</code> se lo stato e‘ valido, <code>false</code>
     * altrimenti.
     */
    public boolean addFinalState(int p) {
        if (validState(p)) {
            finalStates.add(p);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Determina se uno stato e‘ valido oppure no.
     *
     * @param p Lo stato da controllare.
     * @return <code>true</code> se lo stato e‘ valido, <code>false</code>
     * altrimenti.
     * @see #numberOfStates
     */
    public boolean validState(int p) {
        return (p >= 0 && p < numberOfStates);
    }

    /**
     * Determina se uno stato e‘ finale oppure no.
     *
     * @param p Lo stato da controllare.
     * @return <code>true</code> se lo stato e‘ finale, <code>false</code>
     * altrimenti.
     * @see #finalStates
     */
    public boolean finalState(int p) {
        return finalStates.contains(p);
    }

    /**
     * Restituisce il numero di states dell’automa.
     *
     * @return Numero di states.
     */
    public int numberOfStates() {
        return numberOfStates;
    }

    /**
     * Restituisce l’alfabeto dell’automa, ovvero l’insieme di simboli che
     * compaiono come etichette delle transizioni dell’automa.
     *
     * @return L’alfabeto dell’automa.
     */
    public HashSet<Character> alphabet() {
        HashSet<Character> alphabet = new HashSet<Character>();
        for (Move m : transitions.keySet()) {
            alphabet.add(m.ch);
        }
        return alphabet;
    }

    /**
     * Esegue una mossa dell’automa.
     *
     * @param p  Stato di partenza prima della transizione.
     * @param ch Simbolo da riconoscere.
     * @return Stato di arrivo dopo la transizione, oppure <code>-1</code> se
     * l’automa non ha una transizione etichettata con <code>ch</code> dallo
     * stato <code>p</code>.
     */
    public int move(int p, char ch) {
        Move move = new Move(p, ch);
        if (transitions.containsKey(move)) {
            return transitions.get(move);
        } else {
            return -1;
        }
    }

    /**
     * Analizza il parametro con il DFA della classe.
     *
     * @param s Una stringa in input.
     * @return True se l'automa riconosce il parametro stringa s
     */
    public boolean scan(String s) {
        int i = 0;
        int p = 0;
        while (i < s.length() && p != -1) {
            //Eseguo una mossa per ogni carattere e controllo lo stato risultato.
            p = move(p, s.charAt(i));
            if (validState(p)) {
                i++;
            } else {
                return false;
            }
        }

        // Se sono in uno stato finale, a questo punto, posso accetare la stringa.
        return finalState(p);
    }

    /**
     * Controlla se ci sono definite transizioni per ogni simbolo dell'alfabeto
     * per ogni stato.
     *
     * @return True se l'automa per ogni stato definisce transizioni per ogni
     * simbolo dell'alfabeto. False altrimenti.
     */
    public boolean complete() {
        for (Character c : alphabet()) {
            for (int i = 0; i < numberOfStates; i++) {
                if (transitions.get(new Move(i, c)) == null || validState(transitions.get(new Move(i, c)))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Stampa a video le istruzioni testuali per il tool dot di GraphViz.
     *
     * @param name Il nome da rappresentare in figura.
     */
    public void toDOT(String name) {
        System.out.println("digraph " + name + " {");
        System.out.println("  rankdir=LR;");
        System.out.println("  node [shape = doublecircle];");
        for (Integer i : this.finalStates) {
            System.out.println("  q" + i.toString() + "; ");
        }

        System.out.println("  node [shape = circle];\n");
        HashMap<String, String> storedTransitions = new HashMap<>();

        for (Move m : transitions.keySet()) {
            String from = "q" + m.start + " -> " + "q" + transitions.get(m);

            /* 
             * Se la mossa non è nel contenitore, viene aggiunta con la relativa label,
             * altrimenti viene aggiunta con il prossimo carattere di transizione.
             */
            if (!storedTransitions.containsKey(from)) {
                storedTransitions.put(from, Character.toString(m.ch));
            } else {
                storedTransitions.put(from, storedTransitions.get(from) + "," + m.ch);
            }
        }

        for (String s : storedTransitions.keySet()) {
            System.out.println("  " + s + " [ label = \"" + storedTransitions.get(s) + "\" ];");
        }
        System.out.println("}\n");
    }

    /**
     * Stampa a video il codice Java dell'automa contenuto.
     *
     * @param name Il nome della classe stampata a video.
     */
    public void toJava(String name) {
        if (numberOfStates() == 0) {
            return;
        }

        int     tmpState   = 0;
        boolean firstTrans = true;
        int     trans;

        System.out.println("public class " + name + " {");
        System.out.println("    public static boolean scan(String s) {");
        System.out.println("        int stato = 0;");
        System.out.println("        int i = 0;\n");
        System.out.println("        while(stato >= 0 && i < s.length()) {");
        System.out.println("            final char n = s.charAt(i++);");
        System.out.println("            switch (stato) {");

        while (tmpState < this.numberOfStates) {
            System.out.println("            case " + tmpState + ":");
            for (char i : alphabet()) {
                //move ritorna lo stato di arrivo
                trans = move(tmpState, i);
                if (firstTrans) {
                    System.out.println("                if(n == '" + i + "')");
                    System.out.println("                    stato = " + trans + ";");
                    firstTrans = false;
                } else if (trans != -1) {
                    System.out.println("                else if(n == '" + i + "')");
                    System.out.println("                    stato = " + trans + ";");
                }
            }
            System.out.println("                else");
            System.out.println("                    stato = -1;");
            System.out.println("            break;");
            firstTrans = true;
            tmpState++;
        }
        System.out.println("            }");
        System.out.println("        }");

        System.out.print("        return ");
        String str = "";
        for (int x : finalStates) {
            str += ("stato == " + x + " || ");
        }
        str = str.substring(0, str.length() - 4);
        System.out.print(str);
        System.out.print(";\n");
        System.out.println("    }");
        System.out.println("}\n");

    }
    /*
     Il funzionamento e simile all'algoritmo di dijkstra, 
     per ogni nodo cerca i suoi vicini e gli inserisce nella
     lista da visitare.
     */

    public HashSet<Integer> reach(int q) {
        HashSet<Integer>    s          = new HashSet<Integer>();
        LinkedList<Integer> daVisitare = new LinkedList<>();
        if (!validState(q)) {
            return s;
        }
        s.add(q);
        daVisitare.add(q);
        while (!daVisitare.isEmpty()) {
            q = daVisitare.remove(0);
            for (Move m : transitions.keySet()) {
                if (m.start == q && !s.contains(transitions.get(m))) {
                    s.add(transitions.get(m));
                    daVisitare.add(transitions.get(m));
                }
            }
        }
        //System.out.println("states raggiungibili da " + q+" : "+s.toString());
        return s;
    }

    public HashSet<Integer> reachProf(int q) {
        HashSet<Integer> s = new HashSet<Integer>();
        if (!validState(q)) {
            return s;
        }
        int     n     = this.numberOfStates();
        boolean arr[] = new boolean[n];
        arr[q] = true;

        HashSet<Character> alfabeto = alphabet();
        boolean            flag     = true;
        int                trans;
        while (flag) {
            flag = false;
            for (int i = 0; i < arr.length; i++) {
                if (arr[i]) {
                    for (char c : alfabeto) {
                        trans = move(i, c);
                        if (validState(trans)) {
                            if (!arr[trans]) {
                                arr[trans] = true;
                                flag = true;
                            }
                        }
                    }
                }
            }
        }

        for (Integer i = 0; i < arr.length; i++) {
            if (arr[i] == true) {
                s.add(i);
            }
        }

        return s;
}
    /*Raffinare l'algoritmo di raggiungibilita in modo da tenere traccia, per ogni stato ` p
     raggiungibile da q, di un esempio di stringa w che consente di raggiungere p da q, ovvero tale
     che �d(q, w) = p. Implementare un metodo samples che ritorna un insieme di stringhe accettate
     dall'automa, una per ogni stato finale dell'automa. Suggerimento: usare un vettore r di stringhe
     invece che di boolean.
     */

    public HashSet<String> samples() {
        String[]        arr;
        HashSet<String> strAccettate = new HashSet<>();
        arr = reachMod(0);
        for (int finalState : finalStates) {
            if (!arr[finalState].isEmpty()) {
                strAccettate.add(arr[finalState]);
            }
        }
        return strAccettate;
    }

    public String[] reachMod(int q) {
        if (!validState(q)) {
            System.out.println("Stato non valido");
        }
        String[] arr = new String[numberOfStates];
        arr[q] = "";
        boolean flag = true;
        while (flag) {
            flag = false;
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] != null) {
                    for (char ch : alphabet()) {
                        int trans = move(i, ch);
                        if (validState(trans) && trans != i) {
                            if (arr[trans] == null) {
                                arr[trans] = arr[i] + Character.toString(ch);
                                flag = true;
                            }
                        }
                    }
                }
            }
        }

        return arr;
    }

    public boolean empty() {
        HashSet<Integer> stati = this.reach(0);
        for (int i : stati) {
            if (finalState(i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Restituisce gli stati pozzo dell'automa.
     *
     * @return Un hashset contenente gli stati pozzo.
     */
    public HashSet<Integer> sink() {
        HashSet<Integer> states;
        HashSet<Integer> sinkStates = new HashSet<>();

        int n = numberOfStates();
        for (int i = 0; i < n; i++) {
            states = reach(i);

            boolean flag = true;
            for (int j : states) {
                if (finalState(j)) {
                    flag = false;
                }
            }
            if (flag) {
                sinkStates.add(i);
            }
        }
        return sinkStates;
    }

    /**
     * Minimizza l'oggetto DFA corrente e ne restituisce uno nuovo contenente solo gli stati distinguibili.
     *
     * @return Un DFA contenente solo gli oggetti distinguibili.
     */
    public DFA minimize() {
        boolean[][] eq = fillTable();
        int[]       m  = new int[numberOfStates];
        /*
         * Giustificazione: il vettore m mappa ogni stato i al rappresentante canonico
         * m[i] della classe di equivalenza a cui i appartiene. Scegliamo come
         * rappresentate canonico di una classe di equivalenza lo stato della classe con
         * indice più piccolo.
         */
        for (int i = 0; i < numberOfStates; i++) {
            int j = 0;
            while (!eq[i][j]) {
                j++;
            }
            m[i] = j;
        }
        /*
         * Giustificazione: l’automa B `e l’immagine dell’automa A attraverso la
         * relazione di indistinguibilit`a rappresentata da eq.
         */
        int k = Integer.MIN_VALUE;
        for (int i = 0; i < numberOfStates; i++) {
            if (k < m[i]) {
                k = m[i];
            }
        }

        DFA b = new DFA(k + 1);

        for (int i = 0; i < numberOfStates; i++) {
            for (char c : alphabet()) {
                if (validState(move(i, c))) {
                    b.setMove(m[i], c, m[move(i, c)]);
                }
            }
        }

        for (int i = 0; i < numberOfStates; i++) {
            if (finalState(i)) {
                b.addFinalState(m[i]);
            }
        }

        return b;
    }

    public boolean equivalentTo(DFA other) {
        int n         = this.numberOfStates();
        int trans1;
        int trans2;
        DFA dfaMerged = new DFA(numberOfStates + other.numberOfStates());

        for (int i = 0; i < numberOfStates; i++) {
            for (char ch : this.alphabet()) {
                trans1 = move(i, ch);
                if (validState(trans1)) {
                    dfaMerged.setMove(i, ch, trans1);
                }
            }
        }
        for (int j = 0; j < other.numberOfStates(); j++) {
            for (char ch : other.alphabet()) {
                trans2 = other.move(j, ch);
                if (other.validState(trans2)) {
                    dfaMerged.setMove(j + numberOfStates, ch, trans2 + numberOfStates);
                }
            }
        }

        for (Integer finalState : this.finalStates) {
            dfaMerged.addFinalState(finalState);
        }

        for (Integer finalState : other.finalStates) {
            dfaMerged.addFinalState(finalState + numberOfStates);
        }

        //dfaMerged.toDOT("aaa");
        boolean[][] matr = dfaMerged.fillTable();
        //si guarda il valore del primo in basso a sinistra, devono coincidere
        return matr[0][n];
    }

    /**
     * Riempie una tabella indicante gli stati indistinguibili.
     *
     * @return La matrice contenente tali stati.
     */
    private boolean[][] fillTable() {
        /*
         * Creo la classe di equivalenza 0. Quindi ho 2 insiemi contenenti stati non finali
         * e stati finali rispettivamente.
         */
        boolean[][] eq = new boolean[numberOfStates][numberOfStates];
        for (int i = 0; i < numberOfStates; i++) {
            for (int j = 0; j < numberOfStates; j++) {
                if (finalState(i) && finalState(j) || (!finalState(i) && !finalState(j))) {
                    eq[i][j] = true;
                } else {
                    eq[i][j] = false;
                }
            }
        }

        boolean flag = true;
        while (flag) {
            flag = false;
            for (int i = 0; i < numberOfStates; i++) {
                for (int j = 0; j < numberOfStates; j++) {
                    /*
                     * Giustificazione: se da due stati i e j assunti indistinguibili si raggiungono,
                     * per mezzo di transizioni etichettate con lo stesso simbolo ch, altri due
                     * stati distinguibili, allora anche i e j sono distinguibili.
                     */
                    for (char c : alphabet()) {
                        if (eq[i][j]) {
                            // Se esistono mosse per entrambi gli stati con lo stesso input
                            if (move(i, c) != -1 && move(j, c) != -1) {
                                if (!eq[move(i, c)][move(j, c)]) {
                                    eq[i][j] = false;
                                    flag = true;
                                }
                            }
                        }
                    }
                }
            }
        }

        return eq;
    }

}
