package 05-AES;



public class AES {

    public static final String ALGORITME_XIFRAT = "AES";

    public static final String ALGORITME_HASH = "SHA-256";

    public static final String FORMAT_AES = "AES/CBC/PKCS5Padding";

    public static final int MIDA_IV = 16;

    private static byte[] iv = new byte[MIDA_IV];

    private static final String CLAU = "Clau2";
    
byte[] xifraAES(String msg, String password) throws Exception{
    //Obtenir els bytes de l'String

    for (char  c : password.toCharArray()){

    }

    //Genera IvParameterSpec

    //Genera hash

    //Encrypt

    //Combinar IV i part xifrada

    //return iv+magxifrat
}

String desxifraAES(byte[] bMsgXifrat, String password){

    //Extreure l'IV

    //Extreure la part xifrada

    //Fer hash de la clau

    //Desxifrar

    //Return String Desxifrar
}

public static void main(String[] args){
    String msgs[] = {"Lorem ipsum dicet", "Hola Andrés cómo está tu cuñado", "Ágora ïlla Otto"};

    for(int i=0; i< msgs.length; i++){
        String msg = msgs[i];

        byte[] 
    }
}

}
