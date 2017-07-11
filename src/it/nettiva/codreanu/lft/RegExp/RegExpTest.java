package it.nettiva.codreanu.lft.RegExp;

public class RegExpTest {

    public static void main(String[] args) {

        /*RegExp reg1=new RegExpSymbol('a');
        RegExp reg2=new RegExpSymbol('b');
        RegExp choice=new RegExpChoice(reg1,reg2);
        RegExp star=new RegExpStar(seq);
        choice.compile().toDOT("aa");*/

        //6.2
        //Individuare un'espressione regolare E definita sull'alfabeto f/; *; cg che generi
        //le sequenze di almeno 4 caratteri che iniziano con /*, che finiscono con */, e che contengono
        //una sola occorrenza della sequenza */, quella finale. Istanziare oggetti delle classi RegExp* per
        //rappresentare E in Java e produrre epsilon-NFA corrispondente attraverso il metodo compile.
        //     
        //              ESPRESSIONE REGOLARE
        //                 (/*)(*|c)*(*/)
        RegExp regexp1 = new RegExpSequence(
                new RegExpSequence(
                        new RegExpSequence(
                                new RegExpSymbol('/'), new RegExpSymbol('*')
                        ),
                        new RegExpStar(
                                new RegExpChoice(
                                        new RegExpSymbol('*'), new RegExpSymbol('c')
                                )
                        )
                ),
                new RegExpSequence(
                        new RegExpSymbol('*'), new RegExpSymbol('/')
                )
        );

        regexp1.compile().dfa().minimize().toDOT("regexp1");

        //Esercizio 6.3. Giustificare l'apparente stranezza del DFA ottenuto eseguendo l'istruzione:
        //new RegExpStar(new RegExpChoice(new RegExpSymbol('a'), new RegExpSymbol('b'))).compile().dfa().minimize().toDOT("");
        //Risposta: questa istruzione accetta qualsiasi strings di a e/o b 
        //compresa la stringa vuota. Inoltre lo stato iniziale e anche finale 
        //e quindi non ci sono stati d'errore.
        /* Test 6.4
         * Utilizzando tale metodo, costruire l'espressione regolare che
         * genera le costanti numeriche (Esercizio 1.2), ottenere il DFA minimo corrispondente e verificarne
         * la correttezza.
         */
        //                           ESPRESSIONE REGOLARE
        //                         
        //              (+|-|ε)(num*.num+)(ε | e( + | - | ε )num+)
        //
        System.out.println("Test Esercizio 6.4");
        RegExp num           = range('0', '3');
        RegExp numKleenePlus = new RegExpSequence(new RegExpStar(num), num);
        RegExp op            = new RegExpChoice(new RegExpChoice(new RegExpSymbol('+'), new RegExpSymbol('-')), new RegExpEpsilon());
        RegExp exp           = new RegExpSymbol('e');
        RegExp dot           = new RegExpChoice(new RegExpSymbol('.'), new RegExpEpsilon());

        RegExp regexp2 = new RegExpSequence(
                new RegExpSequence(op, new RegExpSequence(new RegExpStar(num), new RegExpSequence(dot, numKleenePlus))),
                new RegExpChoice(new RegExpSequence(exp, new RegExpSequence(op, numKleenePlus)), new RegExpEpsilon())
        );

        regexp2.compile().dfa().minimize().toDOT("dfaMinimo");

    }//end main

    /*
     Esercizio 6.4. Implementare un metodo statico
     public static RegExp range(char from, char to);
     che, dati due caratteri from e to, crea l'espressione regolare che genera tutti i caratteri nell'intervallo
     da from a to, estremi inclusi.

     */
    public static RegExp range(char from, char to) {
        RegExp exp = new RegExpSymbol(from);
        while (from < to) {
            exp = new RegExpChoice(exp, new RegExpSymbol(++from));
        }
        return exp;
    }

}
