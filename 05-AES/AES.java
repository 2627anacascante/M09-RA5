
import javax.crypto.*;
import java.security.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;



public class AES {

    public static final String ALGORITME_XIFRAT = "AES";

    public static final String ALGORITME_HASH = "SHA-256";

    public static final String FORMAT_AES = "AES/CBC/PKCS5Padding";

    public static final int MIDA_IV = 16;

    private static byte[] iv = new byte[MIDA_IV];

    private static final String CLAU = "Clau2";
    
static byte[] xifraAES(String msg, String password) throws Exception{
    //Obtenir els bytes de l'String
    byte[] msgBytes = msg.getBytes(StandardCharsets.UTF_8);

    //Genera IvParameterSpec
    iv = generaIV();
    IvParameterSpec ivSpec = new IvParameterSpec(iv);

    //Genera hash
     SecretKeySpec clau = generaHash(password);

    //Encrypt
    Cipher cipher = Cipher.getInstance(FORMAT_AES);
    cipher.init(Cipher.ENCRYPT_MODE, clau, ivSpec);
    byte[] magXifrat = cipher.doFinal(msgBytes);

    //Combinar IV i part xifrada
    byte[] resultat = new byte[MIDA_IV + magXifrat.length];
    System.arraycopy(iv, 0, resultat, 0, MIDA_IV);
    System.arraycopy(magXifrat, 0, resultat, MIDA_IV, magXifrat.length);

    //return iv+magxifrat
    return resultat;
}

static String desxifraAES(byte[] bMsgXifrat, String password){
    try {
        //Extreure l'IV
        IvParameterSpec ivSpec = new IvParameterSpec(extreureIV(bMsgXifrat));

        //Extreure la part xifrada
        byte[] xifrat = getBytesXifrats(bMsgXifrat);

        //Fer hash de la clau
        SecretKeySpec clau = generaHash(password);

        //Desxifrar
        Cipher cipher = Cipher.getInstance(FORMAT_AES);
        cipher.init(Cipher.DECRYPT_MODE, clau, ivSpec);
        byte[] desxifrat = cipher.doFinal(xifrat);

        //Return String Desxifrar
        return new String(desxifrat, StandardCharsets.UTF_8);
    } catch (Exception e) {
        throw new RuntimeException("Error de desxifrat: " + e.getLocalizedMessage(), e);
    }
}
    


public static void main(String[] args){
    String msgs[] = {"Lorem ipsum dicet", "Hola Andrés cómo está tu cuñado", 
    "Ágora ïlla Otto"};

    for(int i=0; i< msgs.length; i++){
        String msg = msgs[i];

        byte[] bXifrats = null;
        String desxifrat = "";

        try{
            bXifrats = xifraAES(msg, CLAU);
            desxifrat = desxifraAES(bXifrats, CLAU);

        }catch(Exception e){
            System.out.println("Error de xifrat"
                + e.getLocalizedMessage());
        }
        System.out.println("-------------------------");
        System.out.println("Msg:" +msg );
        System.out.println("Enc" + new String(bXifrats));
        System.out.println("DEC:" + desxifrat);
    }
}
// ---------- Mètodes addicionals ----------

// Genera un IV aleatori amb SecureRandom
private static byte[] generaIV() {
    byte[] nouIV = new byte[MIDA_IV];
    new SecureRandom().nextBytes(nouIV);
    return nouIV;
}

// Obté un SecretKeySpec a partir del password (hash SHA-256 -> clau AES de 256 bits)
private static SecretKeySpec generaHash(String password) throws NoSuchAlgorithmException {
    MessageDigest md = MessageDigest.getInstance(ALGORITME_HASH);
    byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
    return new SecretKeySpec(hash, ALGORITME_XIFRAT);
}

// Extreu l'IV (els primers MIDA_IV bytes)
private static byte[] extreureIV(byte[] bMsgXifrat) {
    return Arrays.copyOfRange(bMsgXifrat, 0, MIDA_IV);
}

// Extreu la part xifrada (a partir de l'IV fins al final)
private static byte[] getBytesXifrats(byte[] bMsgXifrat) {
    return Arrays.copyOfRange(bMsgXifrat, MIDA_IV, bMsgXifrat.length);
}

}
