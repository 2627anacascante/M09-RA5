public class RotX {

    public static final String abc = "aáàbcçdeéèfghiíìïjklmnñoóòpqrstuúùüvwxyz";

    public static char[] minuscules = abc.toCharArray();

    public static char[] mayuscules = abc.toUpperCase().toCharArray();

    public String[] missatges = { "ABC", "XYZ", "Hola, Mr. Calçot", "Perdò, per tu què és" };

    public String transformar(String text, int x) {

        StringBuffer res = new StringBuffer();

        for (char lletra : text.toCharArray()) {
            int posicio = new String(minuscules).indexOf(lletra);
            boolean esmajuscula= false;

            if (posicio < 0) {
                posicio = new String(mayuscules).indexOf(lletra);
                esmajuscula = true;
            }

    
            if (posicio >= 0) {
                int novaposicio = Math.floorMod(
                    posicio + x, 
                    abc.length()
                );
            
        
        res.append(
            esmajuscula
            ? mayuscules[novaposicio]
            : minuscules[novaposicio]
        );

    }else {
        res.append(lletra);
    }
        
    }
    return res.toString();
    }
    
    public String xifraRotX(String text, int x) {
    return transformar(text, x);
    }

    public String desXifraRotX(String text, int x) {
    return transformar(text, -x);
    }

    public String forçaBrutaRotX(String text) {
        StringBuffer res = new StringBuffer();
        for (int x = 0; x < abc.length(); x++) {
            res.append("x = " + x + ": " + transformar(text, x) + "\n");
        }
        return res.toString();
    }

    public static void main(String[] args) {
        
        var xifrat = new RotX();

        int x = 0;
            for (String missatge : xifrat.missatges) {

                System.out.println(xifrat.xifraRotX( "("+ x + ") "+ missatge, x));
              x += 2;  
            };
        
        
            var desxifrat = new RotX();
            
            int y = 0;
            for (String missatge : desxifrat.missatges) {
                System.out.println(desxifrat.desXifraRotX( "("+ y + ") "+ xifrat.xifraRotX(missatge, y), y));
              y += 2;  
            };

            int z = 0;
            var forçaBruta = new RotX();
            for (String missatge : forçaBruta.missatges) {
                System.out.println(forçaBruta.forçaBrutaRotX( "("+ z + ") "+ missatge));
              z += 1;  
            };
}

}