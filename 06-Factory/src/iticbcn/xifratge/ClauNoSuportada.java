package iticbcn.xifratge;

public class ClauNoSuportada extends Exception {

    public ClauNoSuportada() {
        super("La clau proporcionada no es correcte");
    }

    public ClauNoSuportada(String missatge) {
        super(missatge);
    }
    
}
