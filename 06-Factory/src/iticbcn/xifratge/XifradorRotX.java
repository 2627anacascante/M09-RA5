package iticbcn.xifratge;

public class XifradorRotX implements Xifrador {

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

//     public static void main(String[] args) {
        
//         var xifrat = new XifradorRotX();

//         int x = 0;
//             for (String missatge : xifrat.missatges) {

//                 System.out.println(xifrat.xifraRotX( "("+ x + ") "+ missatge, x));
//               x += 2;  
//             };
        
        
//             var desxifrat = new XifradorRotX();
            
//             int y = 0;
//             for (String missatge : desxifrat.missatges) {
//                 System.out.println(desxifrat.desXifraRotX( "("+ y + ") "+ xifrat.xifraRotX(missatge, y), y));
//               y += 2;  
//             };

//             int z = 0;
//             var forçaBruta = new XifradorRotX();
//             for (String missatge : forçaBruta.missatges) {
//                 System.out.println(forçaBruta.forçaBrutaRotX( "("+ z + ") "+ missatge));
//               z += 1;  
//             };
// }
@Override 

public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
    int x;
    try {
        x = Integer.parseInt(clau);
    } catch (NumberFormatException e) {
        throw new ClauNoSuportada("La clau de RotX ha de ser un número sencer de 0-40.");
    }
    return new TextXifrat(xifraRotX(msg, x).getBytes(), clau);
}

    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        int x;
        try {
            x = Integer.parseInt(clau);
        } catch (NumberFormatException e) {
            throw new ClauNoSuportada("La clau de RotX ha de ser un número sencer de 0 a 40.");
        }
        return desXifraRotX(new String(xifrat.getBytes()), x);
    }

}