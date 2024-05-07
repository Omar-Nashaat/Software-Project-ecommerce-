package com.usersevice.user.Services.Implementation;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.security.spec.KeySpec;
public class EncryptImpl {
    private static final String SECRET_KEY = "9mng65v8jf4lxn93nabf981m"; 
    private static final String ENCRYPTION_SCHEME = "DESede";

    public static String encrypt(String plainText) {
        try {
            byte[] keyBytes = SECRET_KEY.getBytes();
            KeySpec keySpec = new DESedeKeySpec(keyBytes);
            SecretKeyFactory factory = SecretKeyFactory.getInstance(ENCRYPTION_SCHEME);
            SecretKey key = factory.generateSecret(keySpec);
            Cipher cipher = Cipher.getInstance(ENCRYPTION_SCHEME);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
            } 
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String decrypt(String encryptedText) {
        try {
            byte[] keyBytes = SECRET_KEY.getBytes();
            KeySpec keySpec = new DESedeKeySpec(keyBytes);
            SecretKeyFactory factory = SecretKeyFactory.getInstance(ENCRYPTION_SCHEME);
            SecretKey key = factory.generateSecret(keySpec);

            Cipher cipher = Cipher.getInstance(ENCRYPTION_SCHEME);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));

            return new String(decryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
}
    
}
