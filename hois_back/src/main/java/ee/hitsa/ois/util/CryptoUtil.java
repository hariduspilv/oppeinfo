package ee.hitsa.ois.util;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import ee.hitsa.ois.exception.HoisException;

public abstract class CryptoUtil {

    public static byte[] encrypt(String encryptionKey, Object data) {
        if (data == null) {
            return null;
        }

        try {
            Key aesKey = new SecretKeySpec(encryptionKey.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);

            return cipher.doFinal(data.toString().getBytes());
        } catch (Exception e) {
            throw new HoisException(e);
        }
    }

    public static String decrypt(String encryptionKey, byte[] encryptedData) {
        if (encryptedData == null) {
            return null;
        }

        try {
            Key aesKey = new SecretKeySpec(encryptionKey.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, aesKey);

            return new String(cipher.doFinal(encryptedData));
        } catch (@SuppressWarnings("unused") Exception e) {
            return null;
        }
    }
}
