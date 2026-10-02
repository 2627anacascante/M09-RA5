import java.util.Random;
import java.util.random.*;
import java.util.ArrayList;
import java.util.Collections;

public class Polialfabetic {

    public static char[] abc = {'A', 'Á', 'À', 'Â', 'B', 'C', 'Ç', 'D', 'E', 'É', 'È', 'F', 'G', 'H', 'I', 'Í', 'Ì', 'Ï', 'J', 'K', 'L', 'M', 'N', 'Ñ', 'O', 'Ó', 'Ò', 'P', 'Q', 'R', 'S', 'T', 'U', 'Ú', 'Ù', 'Ü', 'V', 'W', 'X', 'Y', 'Z'};

    private static final long clauSecreta = 1L;
    private static Random random;

    private static char[] permutaAlfabet(char[] abc, Random random){

        ArrayList<Character> list = new ArrayList<>();
        for (char lletra : abc) {
            list.add(lletra);
            
        }
        Collections.shuffle(list, random);

        char[] permutacio = new char[list.size()];
        for (int i = 0; i < list.size(); i++) {
            permutacio[i] = list.get(i);
        }
        return permutacio;
    }

    public static void initRandom(long seed){
        random = new Random(seed);
    }
    

    public static String xifraPoliAlfa(String msg){
        char[] res = new char[msg.length()];

        for (int i = 0; i < msg.length(); i++) {

            char[] permutacio = permutaAlfabet(abc, random);

            char minus = msg.charAt(i);

            char majus = Character.toUpperCase(minus);

            int posicio = -1;

            for (int j = 0; j < abc.length; j++) {
                if (abc[j] == majus) {
                    posicio = j;
                    break;
                }
            }

            if (posicio == -1) {
                res[i] = minus;
            } else {
                char xifrat = permutacio[posicio];
                res[i] = Character.isLowerCase(minus)
                        ? Character.toLowerCase(xifrat)
                        : xifrat;
            }

        }
        return new String(res);


    }

    public static String desxifraPoliAlfa(String msgxifrat){

        char[] res2 = new char[msgxifrat.length()];

        for (int i = 0; i < msgxifrat.length(); i++) {

            char[] permutacio = permutaAlfabet(abc, random);

            char minus = msgxifrat.charAt(i);

            char mayus = Character.toUpperCase(minus);

            int posicio = -1;

            for (int j = 0; j < permutacio.length; j++) {
                if (permutacio[j] == mayus) {
                    posicio = j;
                    break;
                }
            }

            if (posicio == -1) {
                res2[i] = minus;
            } else {
                char noxifrat = abc[posicio];
                res2[i] = Character.isLowerCase(minus)
                        ? Character.toLowerCase(noxifrat)
                        : noxifrat;
            }

        }
        return new String(res2);
        }

    

    public static void main(String[] args){
        String msgs[] = {"Test 01 àrbitre, coixí, Perímetre",
                "Test 02 Taüll, DÍA, año",
                "Test 03 Peça, Òrrius, Bòvila"};

        String[] msgsXifrats = new String[msgs.length];

        System.out.println("Xifratge");
        for(int i = 0; i<msgs.length; i++){
            initRandom(clauSecreta);
            msgsXifrats[i] = xifraPoliAlfa(msgs[i]);
            System.out.printf("%34s-> %s%n", msgs[i], msgsXifrats[i]);
        }

        System.out.println("Desxifratge");
        for(int i = 0; i<msgs.length; i++){
            initRandom(clauSecreta);
            String msg = desxifraPoliAlfa(msgsXifrats[i]);
            System.out.printf("%34s-> %s%n", msgsXifrats[i], msg);
        }
    }
    
    
}
