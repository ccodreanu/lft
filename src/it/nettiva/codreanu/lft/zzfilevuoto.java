package it.nettiva.codreanu.lft;

public class zzfilevuoto {

    public static boolean scan(String s) {

        int stato = 0;
        int i     = 0;
        while (stato >= 0 && i < s.length()) {

            final char n = s.charAt(i++);
            switch (stato) {

                case 0:

                    if (n == '0') {
                        stato = 1;
                    } else if (n == '1') {
                        stato = 0;
                    } else {
                        stato = -1;
                    }

                    break;

                case 1:

                    if (n == '0') {
                        stato = 2;
                    } else if (n == '1') {
                        stato = 0;
                    } else {
                        stato = -1;
                    }

                    break;

                case 2:

                    if (n == '0') {
                        stato = 3;
                    } else if (n == '1') {
                        stato = 0;
                    } else {
                        stato = -1;
                    }

                    break;

                case 3:

                    if (n == '0') {
                        stato = 3;
                    } else if (n == '1') {
                        stato = 3;
                    } else {
                        stato = -1;
                    }

                    break;

            }
        }
        return stato == 3;
    }

}
