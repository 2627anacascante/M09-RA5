

public class Monoalfabetic {

    public static char[] abc = {'A', 'Á', 'Â', 'B', 'C', 'Ç', 'D', 'E', 'É', 'È', 'F', 'G', 'H', 'I', 'Í', 'Ì', 'Ï', 'J', 'K', 'L', 'M', 'N', 'Ñ', 'O', 'Ó', 'Ò', 'P', 'Q', 'R', 'S', 'T', 'U', 'Ú', 'Ù', 'Ü', 'V', 'W', 'X', 'Y', 'Z'};

    public String[] missatges = { "àrbitre, coixí, Perímetre", "Taüll, DÍA, año", "Peça, Òrrius, Bòvila" };


    public char[] permutaAlfabet(char[] abc) {
        char[] permutacio = abc.clone();
        for (int i = permutacio.length - 1; i > 0; i--) {
            int randomIndex = (int) (Math.random() * (i + 1));
            char temp = permutacio[i];
            permutacio[i] = permutacio[randomIndex];
            permutacio[randomIndex] = temp;
        }
        return permutacio;
    }


public String xifraMonoAlfa(String missatge, char[] clau) {
    StringBuffer resultat = new StringBuffer();
    for (char lletra : missatge.toCharArray()) {
        if (Character.isLetter(lletra)) {
            char lletraMajusc = Character.toUpperCase(lletra);
            int index = new String(abc).indexOf(lletraMajusc);
            if (index >= 0) {
                char lletraXifrada = clau[index];
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
    StringBuffer sb = new StringBuffer();

    for (char let : message.toCharArray()) {
        if (Character.isLetter(let)){
            char majus = Character.toUpperCase(let);
            int index = new String(clau).indexOf(majus);

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

public static void main(String[] args) {
    Monoalfabetic monoalfabetic = new Monoalfabetic();
    char[] clau = monoalfabetic.permutaAlfabet(abc);
    System.out.println("Clau de permutació: " + new String(clau));
    for (String missatge : monoalfabetic.missatges) {
        String xifrat = monoalfabetic.xifraMonoAlfa(missatge, clau);
        String desxifrat = monoalfabetic.desXifratMonoAlfa(xifrat, clau);
        System.out.println("Missatge original: " + missatge);
        System.out.println("Missatge xifrat: " + xifrat);
        System.out.println("Missatge Desxifrat:" + desxifrat);
        System.out.println();
    }
}
}