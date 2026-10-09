package iticbcn.xifratge;

public interface Xifrador {
    public TextXifrat xifra(String missatge, String clau) throws ClauNoSuportada;
    public String desXifra(TextXifrat missatge, String clau) throws ClauNoSuportada;
}