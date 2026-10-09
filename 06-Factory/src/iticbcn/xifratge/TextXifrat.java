package iticbcn.xifratge;

public class TextXifrat {
    private String textXifrat;
    private String clau;

    public TextXifrat(String textXifrat, String clau) {
        this.textXifrat = textXifrat;
        this.clau = clau;
    }

    @Override
    public String toString() {
        return textXifrat;
    }

    public byte[] getBytes() {
        return textXifrat.getBytes();
    }
    
}
