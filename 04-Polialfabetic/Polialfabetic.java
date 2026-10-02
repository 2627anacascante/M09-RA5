import java.util.Random;
import java.util.random.*;
import java.util.ArrayList;
import java.util.Collections;

public class Polialfabetic {

    public static char[] abc = {'A', 'Á', 'Â', 'B', 'C', 'Ç', 'D', 'E', 'É', 'È', 'F', 'G', 'H', 'I', 'Í', 'Ì', 'Ï', 'J', 'K', 'L', 'M', 'N', 'Ñ', 'O', 'Ó', 'Ò', 'P', 'Q', 'R', 'S', 'T', 'U', 'Ú', 'Ù', 'Ü', 'V', 'W', 'X', 'Y', 'Z'};


    public static char[] permutaAlfabet(char[] abc){

        ArrayList<Character> list = new ArrayList<>();
        for (char lletra : abc) {
            list.add(lletra);
            Collections.shuffle(list);
        }

        char[] permutacio = new char[list.size()];
        for (int i = 0; i < list.size(); i++) {
            permutacio[i] = list.get(i);
        }
        return permutacio;
    }
    

    public static String xifraPoliAlfa(String msg){

        
        String res = "";
        for(char c : msg.toCharArray()) {
            permutaAlfabet(abc);
            Character.toString(c);
            res += c;
        }
        return res;


    }

    public static String desxifraPoliAlfa(String msgxifrat){
        
        String res1="";
        for (char t: msgxifrat.toCharArray()){
            permutaAlfabet(abc);
            Character.toString(t);
            res1 += t;
        }
        return res1;

    }

    private static void initRandom(Random random){
        random = new Random();
    }


    public static void main(String[] args){

        Random clauSecreta = new Random();

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
            System.out.printf("%34-> %s%n", msgsXifrats[i], msg);
        }
    }
    
    
}
