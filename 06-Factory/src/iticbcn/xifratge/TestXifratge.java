package iticbcn.xifratge;
public class TestXifratge {

    public static void main(String[] args) {
        AlgorismeFactory[] aFactory = {
        new AlgorismeAES(), new AlgorismeMonoAlfabetic(), 
        new AlgorismePolialfabetic(), new AlgorismeRotX()
    };
    
    String[] aNames = {"AES", "MonoAlfabetic", "Polialfabetic", "RotX"};

    String[] msgs = {"Test 01: Àlgid, Ülrich, Vàlid", "Test 02: Caràcters esecials ¡!¿?*-123[]{}@#"};

    String[][] claus = {
        {"Clau secreta 01"},
        {"ErrorClau", null},
        {"ErrorClau2", "123456"},
        {"-1", "13", "1000", "ErrorClau"}
    };

    for(int i=0; i<aFactory.length; i++){
        AlgorismeFactory alg = aFactory[i];
        String nom = aNames[i];
        Xifrador xifrador = alg.creaXifrador();

        System.out.println("====");

        for(String msg : msgs) {
            for(String clau : claus[i]) {
                System.out.println("msg:" + msg);
                System.out.println("algorisme:" + nom);
                System.out.println("clau:" + clau);

                TextXifrat tx;
                try {
                    tx = xifrador.xifra(msg, clau);
                } catch (ClauNoSuportada e) {
                    System.out.println("Error: " + e.getLocalizedMessage());
                    System.out.println("---");
                    continue;
                }

                System.out.println("TextXifrat:" + tx);

                try {
                    String desxifrat = xifrador.desxifra(tx, clau);
                    System.out.println("Desxifrat:" + desxifrat);
                } catch (ClauNoSuportada e) {
                    System.out.println("Error: " + e.getLocalizedMessage());
                }
                System.out.println("---");
            }
        }
        
        }
    }
}