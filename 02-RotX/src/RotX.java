public class RotX{

    public static final String abc = "aáàbcdeéèfghiíìïjklmnñopqrstuúùüvwxyz";

    public static char [] minuscules = abc.toCharArray();

    public static char [] mayuscules = abc.toUpperCase().toCharArray();

    public String [] missatges = {"ABC", "XYZ", "Hola, Mr. Calçot", "Perdò, per tu què és"};

    public String xifraRotX(String text, int x) {

        String res ="";
        x = 0;
        for (char lletra : text.toCharArray()) {
            int posicio = new String(minuscules).indexOf(lletra);
            if (posicio >= 0) {
                res += minuscules[(posicio + x) % minuscules.length];
                x++;
                continue;
            }

            posicio = new String(mayuscules).indexOf(lletra);
            if (posicio >= 0) {
                res += mayuscules[(posicio + x) % mayuscules.length];
            } else {
                res += lletra;
            }
            x++;
        }
        return res;

    }
        public static void main(String[] args) {
        
        var xifrat = new RotX();

            for (String missatge : xifrat.missatges) {
                int x = missatge.charAt(0);
                System.out.println(xifrat.xifraRotX( "("+ x + ") "+ missatge, 1));
            }
}
}