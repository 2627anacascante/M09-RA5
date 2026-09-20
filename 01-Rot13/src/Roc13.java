//Act 1 - Diumenge

public class Roc13 {

    public static final String abc = "aáàbcdeéèfghiíìïjklmnñopqrstuúùüvwxyz";

    public static char [] minuscules = abc.toCharArray();

    public static char [] mayuscules = abc.toUpperCase().toCharArray();

    public String xifraRot13(String text) {
        StringBuilder resultat = new StringBuilder();

        for (char lletra : text.toCharArray()) {
            int posicio = new String(minuscules).indexOf(lletra);
            if (posicio >= 0) {
                resultat.append(minuscules[(posicio + 13) % minuscules.length]);
                continue;
            }

            posicio = new String(mayuscules).indexOf(lletra);
            if (posicio >= 0) {
                resultat.append(mayuscules[(posicio + 13) % mayuscules.length]);
            } else {
                resultat.append(lletra);
            }
        }

        return resultat.toString();
    }

    public String desXifraRot13(String text) {
        StringBuilder resultat = new StringBuilder();

        for (char lletra : text.toCharArray()) {
            int posicio = new String(minuscules).indexOf(lletra);
            if (posicio >= 0) {
                resultat.append(minuscules[Math.floorMod(posicio - 13, minuscules.length)]);
                continue;
            }

            posicio = new String(mayuscules).indexOf(lletra);
            if (posicio >= 0) {
                resultat.append(mayuscules[Math.floorMod(posicio - 13, mayuscules.length)]);
            } else {
                resultat.append(lletra);
            }
        }

        return resultat.toString();

    }

    public static void main(String[] args) {

        var xifrat = new Roc13();
        System.out.println(xifrat.xifraRot13("ABC"));
        System.out.println(xifrat.xifraRot13("XYZ"));
        System.out.println(xifrat.xifraRot13("Hola, Mr. Calçot"));
        System.out.println(xifrat.xifraRot13("Perdò, per tu què és"));

        var desxifrat = new Roc13();
        System.out.println(desxifrat.desXifraRot13(xifrat.xifraRot13("ABC")));
        System.out.println(desxifrat.desXifraRot13(xifrat.xifraRot13("XYZ")));
        System.out.println(desxifrat.desXifraRot13(xifrat.xifraRot13("Hola, Mr. Calçot")));
        System.out.println(desxifrat.desXifraRot13(xifrat.xifraRot13("Perdò, per tu què és")));

    }
}
