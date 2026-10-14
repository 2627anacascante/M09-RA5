package iticbcn.xifratge;

import java.util.ArrayList;
import java.util.Collections;
import java.nio.charset.StandardCharsets;

public class XifradorMonoalfabetic implements Xifrador {

    public static char[] abc = {'A', 'Á', 'Â', 'B', 'C', 'Ç', 'D', 'E', 'É', 'È', 'F', 'G', 'H', 'I', 'Í', 'Ì', 'Ï', 'J', 'K', 'L', 'M', 'N', 'Ñ', 'O', 'Ó', 'Ò', 'P', 'Q', 'R', 'S', 'T', 'U', 'Ú', 'Ù', 'Ü', 'V', 'W', 'X', 'Y', 'Z'};

    public String[] missatges = { "àrbitre, coixí, Perímetre", "Taüll, DÍA, año", "Peça, Òrrius, Bòvila" };

    private char[] validaClau(char[] clau) {
        if (clau == null || clau.length != abc.length) {
            throw new IllegalArgumentException("La clau ha de ser una permutació completa de l'alfabet.");
        }

        char[] clauNormalitzada = new char[clau.length];
        boolean[] lletresUtilitzades = new boolean[abc.length];
        for (int i = 0; i < clau.length; i++) {
            char lletra = Character.toUpperCase(clau[i]);
            int index = new String(abc).indexOf(lletra);
            if (index < 0 || lletresUtilitzades[index]) {
                throw new IllegalArgumentException("La clau ha de ser una permutació completa de l'alfabet.");
            }
            lletresUtilitzades[index] = true;
            clauNormalitzada[i] = lletra;
        }
        return clauNormalitzada;
    }

    public char[] permutaAlfabet(char[] abc) {
        ArrayList<Character> list = new ArrayList<>();
        for (char lletra : abc) {
            list.add(lletra);
        }

        Collections.shuffle(list);

        char[] permutacio = new char[list.size()];
        for (int i = 0; i < list.size(); i++) {
            permutacio[i] = list.get(i);
        }
        return permutacio;
    }


public String xifraMonoAlfa(String missatge, char[] clau) {
    char[] clauValida = validaClau(clau);
    StringBuffer resultat = new StringBuffer();
    for (char lletra : missatge.toCharArray()) {
        if (Character.isLetter(lletra)) {
            char lletraMajusc = Character.toUpperCase(lletra);
            int index = new String(abc).indexOf(lletraMajusc);
            if (index >= 0) {
                char lletraXifrada = clauValida[index];
                if (Character.isLowerCase(lletra)) {
                    resultat.append(Character.toLowerCase(lletraXifrada));
                } else {
                    resultat.append(lletraXifrada);
                }
            } else {
                resultat.append(lletra);
            }
        } else {
            resultat.append(lletra);
        }
    }
    return resultat.toString();
}

public String desXifratMonoAlfa(String message, char[]clau ){
    char[] clauValida = validaClau(clau);
    StringBuffer sb = new StringBuffer();

    for (char let : message.toCharArray()) {
        if (Character.isLetter(let)){
            char majus = Character.toUpperCase(let);
            int index = new String(clauValida).indexOf(majus);

            if (index >= 0) {
                char lletraDesxifrada = abc[index];
                if (Character.isLowerCase(let)) {
                    sb.append(Character.toLowerCase(lletraDesxifrada));
                } else {
                    sb.append(lletraDesxifrada);
                }
            } else {
                sb.append(let);
            }
        } else {
            sb.append(let);
        }
    }
    return sb.toString(); 
}

// public static void main(String[] args) {
//     XifradorMonoalfabetic monoalfabetic = new XifradorMonoalfabetic();
//     char[] clau = monoalfabetic.permutaAlfabet(abc);
//     System.out.println("Clau de permutació: " + new String(clau));
//     for (String missatge : monoalfabetic.missatges) {
//         String xifrat = monoalfabetic.xifraMonoAlfa(missatge, clau);
//         String desxifrat = monoalfabetic.desXifratMonoAlfa(xifrat, clau);
//         System.out.println("Missatge original: " + missatge);
//         System.out.println("Missatge xifrat: " + xifrat);
//         System.out.println("Missatge Desxifrat:" + desxifrat);
//         System.out.println();
//     }
// }
@Override
public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
    try {
        return new TextXifrat(xifraMonoAlfa(msg, clau == null ? null : clau.toCharArray()), clau);
    } catch (IllegalArgumentException e) {
        throw new ClauNoSuportada("Xifratge monoalfabètic no suporta clau !=null");
    }
}

@Override
public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
    try {
        return desXifratMonoAlfa(
            new String (xifrat.getBytes(), StandardCharsets.UTF_8),
            clau == null ? null : clau.toCharArray()
        );
    } catch (IllegalArgumentException e) {
        throw new ClauNoSuportada("Xifratge monoalfabètic no suporta clau !=null");
    }
}
}