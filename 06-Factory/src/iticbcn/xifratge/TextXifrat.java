package iticbcn.xifratge;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

public class TextXifrat {
    private final byte[] textXifrat;
    private String clau;

    public TextXifrat(byte[] textXifrat, String clau) {
        this.textXifrat = Objects.requireNonNull(textXifrat, "textXifrat").clone();
        this.clau = clau;
    }

    public TextXifrat(String textXifrat, String clau) {
        this(Objects.requireNonNull(textXifrat, "textXifrat").getBytes(StandardCharsets.UTF_8), clau);
    }
    //Objects.requirenNonNull es cuan vols assegurar que un objecte no sigui null, i si ho és, llença una excepció amb un missatge personalitzat. 
    // En aquest cas, s'utilitza per assegurar-se que el paràmetre textXifrat no sigui null abans de clonar-lo.

    @Override
    public String toString() {
        return Base64.getEncoder().encodeToString(textXifrat);
    }
    //Base64.getEncoder() convertirà l'array de bytes en una cadena de text codificada en Base64, que és una representació segura per a la transmissió de dades binàries com a text.
    //encodeToString(textXifrat) converteix l'array de bytes textXifrat en una cadena de text codificada en Base64.

    public byte[] getBytes() {
        return textXifrat.clone();
    }
    
}
