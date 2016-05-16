package xyz.appint.union.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;

public class DESUtil {

    private final static String DES = "DES";

    public static void main(String[] args) throws Exception {


        String data = "133";
        String key = "ACTIVITYSHARE";
        String mima = encrypt(data, key);
        System.err.println(mima);
        System.err.println("http://192.168.1.47:8080/web/card/" + encrypt("665", key) + "/" + mima);
//		System.err.println(decrypt("Z6KulD9N06Q=", key));


    }

    public static String encrypt(String data, String salt) throws Exception {
        byte[] bt = encrypt(data.getBytes(), salt.getBytes());
        String strs = new String(Base64.encodeBase64(bt, true, true));
        return strs;
    }


    public static String decrypt(String data, String salt) throws Exception {
        if (data == null)
            return null;
        byte[] buf = Base64.decodeBase64(data.getBytes());
        byte[] bt = decrypt(buf, salt.getBytes());
        return new String(bt);
    }


    private static byte[] encrypt(byte[] data, byte[] salt) throws Exception {
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(salt);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance(DES);

        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);

        return cipher.doFinal(data);
    }


    private static byte[] decrypt(byte[] data, byte[] salt) throws Exception {
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(salt);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance(DES);
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
        return cipher.doFinal(data);
    }

}