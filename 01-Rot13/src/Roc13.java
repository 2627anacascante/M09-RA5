//public class Roc13{
//Diumenge

//public char [] minus = {'a','á','à','b','c','ç','e','é','è','f','g','h','i','í','ì','ï','j','k','l','m','n','ñ','o','ó','ò','p','q','r','s','t','u','ú','ù','ü','v','w','x','y','z'};

//public char [] mayus = {'A', 'Á', 'À', 'B', 'C','Ç', 'E', 'É', 'È', 'F', 'G', 'H', 'I', 'Í', 'Ì', 'Ï', 'J', 'K', 'L', 'M', 'N', 'Ñ', 'O', 'Ó', 'Ò', 'P', 'Q', 'R', 'S', 'T', 'U', 'Ú', 'Ù', 'Ü', 'V', 'W', 'X', 'Y', 'Z'};  

public class Roc13 {

    public static final String abc = "aáàbcdeéèfghiíìïjklmnñopqrstuúùüvwxyz";

    public static char [] minusc = abc.toCharArray();

    public static char [] mayusc = abc.toUpperCase().toCharArray();

    public char xifraRot13( char letra) {

        int i  = new String(minusc).indexOf(letra);
        return minusc[(i+13) % minusc.length];
        }
  
    public char desXifraRot13 (char lletra) {
        int j = new String().indexOf(lletra);
        return xifraRot13(minusc[(j-13) % minusc.length]); 
        }

    }

    public static void main(String[] args) {

    }
