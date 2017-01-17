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
            throw e;
        }

        return hashString;
    }
    public static String lowSodium_Hash(String id)throws NoSuchAlgorithmException, InvalidKeySpecException {
        String hashString = null;

        try{
            byte[] salt = new byte[16];
            KeySpec spec = new PBEKeySpec(id.toCharArray(), salt, 65536, 128);
            SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = f.generateSecret(spec).getEncoded();
            Base64.Encoder enc = Base64.getEncoder();

            //System.out.printf("hash: %s%n", enc.encodeToString(hash));

            hashString = enc.encodeToString(hash);
        }
        catch(NoSuchAlgorithmException | InvalidKeySpecException e){
            throw e;
        }

        return hashString;
    }
}
