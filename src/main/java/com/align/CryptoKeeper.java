package com.align;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
/**
 Aes encryption
 */
//public class AES
public class CryptoKeeper
{

    private static SecretKeySpec secretKey ;
    private static byte[] key ;

    private static String decryptedString;
    private static String encryptedString;

    public static void setKey(String myKey){


        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");

            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16); // use only first 128 bit

            secretKey = new SecretKeySpec(key, "AES");


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    public static String getDecryptedString() {
        return decryptedString;
    }
    public static void setDecryptedString(String decryptedString) {
        CryptoKeeper.decryptedString = decryptedString;
    }
    public static String getEncryptedString() {
        return encryptedString;
    }
    public static void setEncryptedString(String encryptedString) {
        CryptoKeeper.encryptedString = encryptedString;
    }
    public static String encrypt(String id){



        try
        {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

            cipher.init(Cipher.ENCRYPT_MODE, secretKey);


            setEncryptedString(Base64.encodeBase64String(cipher.doFinal(id.getBytes("UTF-8"))));

        }
        catch (Exception e)
        {

            System.out.println("Error while encrypting: "+e.toString());
        }
        return null;
    }
    public static String decrypt(String strToDecrypt)
    {
        try
        {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");

            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            setDecryptedString(new String(cipher.doFinal(Base64.decodeBase64(strToDecrypt))));

        }
        catch (Exception e)
        {

            System.out.println("Error while decrypting: "+e.toString());
        }
        return null;
    }
}



/*
package com.align;

import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.*;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by john on 1/1/17.
 */

/*

public class CryptoKeeper {

    public CryptoKeeper(){

    }

    public String hashMaker(String id) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String hashString = null;
        try {
            Random random = new Random();
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            KeySpec spec = new PBEKeySpec(id.toCharArray(), salt, 65536, 128);
            SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = f.generateSecret(spec).getEncoded();
            Base64.Encoder enc = Base64.getEncoder();
            System.out.printf("salt: %s%n", enc.encodeToString(salt));
            System.out.printf("hash: %s%n", enc.encodeToString(hash));

            hashString = enc.encodeToString(hash);
        }
        catch(NoSuchAlgorithmException | InvalidKeySpecException e){
            e.printStackTrace();
            throw e;
        }

        return hashString;
    }
    public static String lowSodium_Hash(String id)throws NoSuchAlgorithmException, InvalidKeySpecException {
        String hashString = null;

        try{
            byte[] salt = new byte[16];
            KeySpec spec = new PBEKeySpec(id.toCharArray(), salt, 65536, 128);
            //SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            SecretKeyFactory f = SecretKeyFactory.getInstance("AES");
            byte[] hash = f.generateSecret(spec).getEncoded();
            Base64.Encoder enc = Base64.getEncoder();

            hashString = enc.encodeToString(hash);
        }
        catch(NoSuchAlgorithmException | InvalidKeySpecException e){
            e.printStackTrace();
            throw e;
        }

        return hashString;
    }
}

*/