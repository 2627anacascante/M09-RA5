package iticbcn.xifratge;                                    // Paquete (carpeta lógica) de la clase. 
import javax.crypto.*;                                // Clases de cifrado: Cipher, etc.
import java.security.*;                               // Seguridad: MessageDigest (hash), SecureRandom (aleatorios), NoSuchAlgorithmException
import javax.crypto.spec.IvParameterSpec;             // Objeto que envuelve el IV para pasárselo al Cipher
import javax.crypto.spec.SecretKeySpec;               // Objeto que envuelve la clave secreta para AES
import java.nio.charset.StandardCharsets;             // Codificaciones de texto (UTF_8)
import java.util.Arrays;                              // Utilidades para arrays (copyOfRange)



public class XifradorAES implements Xifrador {

    public static final String ALGORITME_XIFRAT = "AES";            // Nombre del algoritmo de cifrado

    public static final String ALGORITME_HASH = "SHA-256";          // Algoritmo de hash: convierte la contraseña en 32 bytes

    public static final String FORMAT_AES = "AES/CBC/PKCS5Padding"; // Receta: algoritmo AES / modo CBC (bloques encadenados) / relleno PKCS5

    public static final int MIDA_IV = 16;                           // Tamaño del IV en bytes (el bloque de AES mide 16)

    private static byte[] iv = new byte[MIDA_IV];                   // Variable donde se guarda el IV (16 bytes, de momento a ceros)

    private static final String CLAU = "Clau2";                     // Contraseña de prueba que usa el main
    
// CIFRAR: recibe mensaje y contraseña, devuelve [IV + mensaje cifrado]. "throws Exception" = deja pasar los errores al que lo llame
static byte[] xifraAES(String msg, String password) throws Exception{
    //Obtenir els bytes de l'String
    byte[] msgBytes = msg.getBytes(StandardCharsets.UTF_8);          // El texto se pasa a bytes (AES cifra bytes, no texto). UTF-8 respeta tildes y ñ
    //ejemplo: "Hola" -> [72, 111, 108, 97] (H=72, o=111, l=108, a=97)
    //Genera IvParameterSpec
    iv = generaIV();                                                 // Genera 16 bytes aleatorios y los guarda en iv
    IvParameterSpec ivSpec = new IvParameterSpec(iv);                // Envuelve el IV en el tipo que exige el Cipher
    //IvParameterSpec es un objeto que envuelve el IV para pasárselo al Cipher. Se crea a partir de los 16 bytes aleatorios generados.
    //por ejemplo, IV = [12, 34, 56, 78, 90, 123, 45, 67, 89, 101, 112, 131, 145, 167, 189, 200] (16 bytes)
    //Cipher es la "máquina de cifrar" que se configura con la receta AES/CBC/PKCS5Padding, la clave y el IV. Luego cifra los bytes del mensaje y devuelve el resultado cifrado.
    //por ejemplo, mensaje = "Hola" -> [72, 111, 108, 97] (4 bytes) -> cifrado = [12, 34, 56, 78, 90, 123, 45, 67, 89, 101, 112, 131, 145, 167, 189, 200] (16 bytes)

    //Genera hash
     SecretKeySpec clau = generaHash(password);                      // Convierte la contraseña en una clave AES válida (vía SHA-256)
     //SecretKeySpec es un objeto que envuelve la clave secreta para AES. Se crea a partir del hash de la contraseña.

    //Encrypt
    Cipher cipher = Cipher.getInstance(FORMAT_AES);                  // Crea la "máquina de cifrar" con la receta AES/CBC/PKCS5Padding
    cipher.init(Cipher.ENCRYPT_MODE, clau, ivSpec);                  // La configura: modo cifrar, con esta clave y este IV
    byte[] magXifrat = cipher.doFinal(msgBytes);                     // Cifra los bytes del mensaje y devuelve el resultado cifrado

    //Combinar IV i part xifrada
    byte[] resultat = new byte[MIDA_IV + magXifrat.length];          // Array vacío con espacio para el IV (16) + el mensaje cifrado
    System.arraycopy(iv, 0, resultat, 0, MIDA_IV);                   // Copia el IV al principio: origen, pos. origen, destino, pos. destino, cantidad
    System.arraycopy(magXifrat, 0, resultat, MIDA_IV, magXifrat.length); // Copia el mensaje cifrado justo después del IV
    //copia iv y magXifrat en un solo array para que el descifrado pueda extraerlos y usarlos. El IV no es secreto, pero debe ser único para cada cifrado. El mensaje cifrado es secreto, pero el IV no lo es.

    //return iv+magxifrat
    return resultat;                                                 // Devuelve [ IV (16 bytes) | mensaje cifrado ]
}

// DESCIFRAR: recibe los bytes [IV + cifrado] y la contraseña, devuelve el texto original
static String desxifraAES(byte[] bMsgXifrat, String password){
    try {                                                            // Descifrar puede lanzar muchas excepciones, las capturamos todas abajo
        //Extreure l'IV
        IvParameterSpec ivSpec = new IvParameterSpec(extreureIV(bMsgXifrat)); // Saca los primeros 16 bytes (el IV) y los envuelve

        //Extreure la part xifrada
        byte[] xifrat = getBytesXifrats(bMsgXifrat);                 // Saca todo lo que hay después del IV (el mensaje cifrado)

        //Fer hash de la clau
        SecretKeySpec clau = generaHash(password);                   // Misma contraseña => misma clave que al cifrar

        //Desxifrar
        Cipher cipher = Cipher.getInstance(FORMAT_AES);              // Crea la máquina con la misma receta que al cifrar
        //getInstance(FORMAT_AES) devuelve un objeto Cipher que sabe cifrar y descifrar con AES/CBC/PKCS5Padding. Debe ser la misma receta que al cifrar, o no funcionará.
        //por ejemplo, si al cifrar usamos AES/CBC/PKCS5Padding, al descifrar debemos usar exactamente lo mismo. Si usamos AES/ECB/NoPadding, no funcionará.
        //una instancia de Cipher puede cifrar o descifrar, pero no ambas cosas a la vez. Se debe crear una nueva instancia para cada operación.
        //un ejemplo de instancia de cipher es: Cipher[AES/CBC/PKCS5Padding, provider=SunJCE, mode=DECRYPT_MODE]
        cipher.init(Cipher.DECRYPT_MODE, clau, ivSpec);              // La configura en modo DESCIFRAR, con la clave y el IV extraído
        //init(Cipher.DECRYPT_MODE, clau, ivSpec) configura el Cipher en modo descifrar, con la misma clave y el mismo IV que se usó al cifrar. 
        // Si la contraseña es distinta, la clave será distinta y el descifrado fallará.
       //decript mode es el modo de operación del Cipher que indica que vamos a descifrar. La clave y el IV deben ser los mismos que se usaron al cifrar, o el resultado será incorrecto.
       //clau es la clave secreta que se generó a partir de la contraseña. Debe ser la misma que se usó al cifrar, o el descifrado fallará.
       //ivSpec es el objeto que envuelve el IV que se extrajo del mensaje cifrado. Debe ser el mismo IV que se usó al cifrar, o el descifrado fallará.
        byte[] desxifrat = cipher.doFinal(xifrat);                   // Descifra y devuelve los bytes originales
        //doFinal(xifrat) descifra los bytes del mensaje cifrado y devuelve los bytes originales. Si la contraseña es incorrecta, lanzará una excepción.
        //xifrat es el array de bytes que contiene el mensaje cifrado (todo lo que hay después del IV). Se pasa al método doFinal para descifrarlo.

        //Return String Desxifrar
        return new String(desxifrat, StandardCharsets.UTF_8);        // Convierte los bytes de vuelta a texto (mismo UTF-8 que al cifrar)
    } catch (Exception e) {                                          // Si algo falla (contraseña mala, datos corruptos...)
        throw new RuntimeException("Error de desxifrat: " + e.getLocalizedMessage(), e); // Lo relanza como RuntimeException (no obliga a declarar throws)
    }
}
    


public static void main(String[] args){                              // Punto de entrada del programa
    String msgs[] = {"Lorem ipsum dicet", "Hola Andrés cómo está tu cuñado", 
    "Ágora ïlla Otto"};                                              // Tres mensajes de prueba (con tildes, ñ y diéresis a propósito)

    for(int i=0; i< msgs.length; i++){                               // Recorre los mensajes uno a uno
        String msg = msgs[i];                                        // Mensaje actual

        byte[] bXifrats = null;                                      // Aquí irá el resultado cifrado (null hasta que se cifre)
        String desxifrat = "";                                       // Aquí irá el texto descifrado

        try{
            bXifrats = xifraAES(msg, CLAU);                          // Cifra el mensaje con la contraseña "Clau2"
            desxifrat = desxifraAES(bXifrats, CLAU);                 // Lo descifra de nuevo con la misma contraseña

        }catch(Exception e){                                         // Si cifrar o descifrar falla
            System.out.println("Error de xifrat"
                + e.getLocalizedMessage());                          // Muestra el mensaje de error
        }
        System.out.println("-------------------------");             // Línea separadora
        System.out.println("Msg:" +msg );                            // Muestra el mensaje original
        System.out.println("Enc" + new String(bXifrats));            // Muestra el cifrado (saldrán símbolos raros: son bytes, no texto). Si bXifrats es null, da NullPointerException
        System.out.println("DEC:" + desxifrat);                      // Muestra el descifrado: debe ser igual al original
    }
}
// ---------- Mètodes addicionals ----------

// Genera un IV aleatori amb SecureRandom
private static byte[] generaIV() {
    byte[] nouIV = new byte[MIDA_IV];                                // Array de 16 bytes vacío
    new SecureRandom().nextBytes(nouIV);                             // Lo rellena con bytes aleatorios seguros (válidos para criptografía)
    //new SecureRandom es una clase que genera números aleatorios seguros para criptografía. nextBytes rellena el array con bytes aleatorios.
    return nouIV;                                                    // Devuelve el IV
}

// Obté un SecretKeySpec a partir del password (hash SHA-256 -> clau AES de 256 bits)
private static SecretKeySpec generaHash(String password) throws NoSuchAlgorithmException {
    MessageDigest md = MessageDigest.getInstance(ALGORITME_HASH);    // Crea el calculador de hash SHA-256
    //MessageDigest es una clase que calcula el hash de un mensaje. getInstance("SHA-256") devuelve un objeto que sabe calcular SHA-256.
    //por ejemplo, "Hola" -> 0x185f8db32271fe25f561a6fc938b2e264306ec304eda518007d1764826381969 (32 bytes)
    //ALGORITME_HASH = "SHA-256" es el nombre del algoritmo de hash que queremos usar. SHA-256 siempre devuelve 32 bytes (256 bits).
    //por ejemplo, "Clau2" -> 0x3c6e0b8a9c15224a8228b9a98ca1531d0b5f7f3e4e5f6f7f8f9f0a1b2c3d4e5f (32 bytes)
    byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8)); // Pasa la contraseña a bytes y calcula su hash: siempre 32 bytes
    //digest es el método que calcula el hash de un array de bytes y devuelve otro array de bytes (el hash)
    //por ejemplo, "Clau2" -> [60, 110, 11, 138, 156, 21, 34, 74, 130, 40, 185, 169, 140, 161, 83, 29, 11, 95, 127, 62, 78, 95, 111, 127, 143, 159, 10, 27, 44, 61, 78, 95] (32 bytes)
    //getBytes(StandardCharsets.UTF_8) convierte el String a bytes usando UTF-8, que respeta tildes y ñ.
    //bytes del hash: [60, 110, 11, 138, 156, 21, 34, 74, 130, 40, 185, 169, 140, 161, 83, 29, 11, 95, 127, 62, 78, 95, 111, 127, 143, 159, 10, 27, 44, 61, 78, 95]
    return new SecretKeySpec(hash, ALGORITME_XIFRAT);                // Envuelve esos 32 bytes como clave AES (AES-256)
}

// Extreu l'IV (els primers MIDA_IV bytes-16) del missatge xifrat
private static byte[] extreureIV(byte[] bMsgXifrat) {
    return Arrays.copyOfRange(bMsgXifrat, 0, MIDA_IV);               // Copia desde la posición 0 hasta la 16 (sin incluir): el IV
    //copyOfRange es un método que copia un rango de un array a otro. En este caso, copia los primeros 16 bytes (el IV) del mensaje cifrado.
    //por ejemplo, bMsgXifrat = [12, 34, 56, 78, 90, 123, 45, 67, 89, 101, 112, 131, 145, 167, 189, 200, ...] (IV + mensaje cifrado)
}

// Extreu la part xifrada (a partir de l'IV fins al final)
private static byte[] getBytesXifrats(byte[] bMsgXifrat) {
    return Arrays.copyOfRange(bMsgXifrat, MIDA_IV, bMsgXifrat.length); // Copia desde la posición 16 hasta el final: el mensaje cifrado
    //por ejemplo, bMsgXifrat = [12, 34, 56, 78, 90, 123, 45, 67, 89, 101, 112, 131, 145, 167, 189, 200, ...] (IV + mensaje cifrado)
}

}
