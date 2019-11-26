package helper;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;


public class Encryptor {

    private static final String ALGORITHM = "AES";

    private static final String KEY = "1Hbfh667adfDEJ78";

    public  String encrypting(String value) throws Exception
    {
        Key key = keyGenerator();
        Cipher cipher = Cipher.getInstance(Encryptor.ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte [] encryptedByteValue = cipher.doFinal(value.getBytes("utf-8"));
        String encryptedValue64 = new BASE64Encoder().encode(encryptedByteValue);
        return encryptedValue64;

    }

    public  String decrypting(String value) throws Exception
    {
        Key key = keyGenerator();
        Cipher cipher = Cipher.getInstance(Encryptor.ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte [] decryptedValue64 = new BASE64Decoder().decodeBuffer(value);
        byte [] decryptedByteValue = cipher.doFinal(decryptedValue64);
        String decryptedValue = new String(decryptedByteValue,"utf-8");
        return decryptedValue;

    }


    private Key keyGenerator()
    {
        Key key = new SecretKeySpec(Encryptor.KEY.getBytes(),Encryptor.ALGORITHM);
        return key;
    }
}
