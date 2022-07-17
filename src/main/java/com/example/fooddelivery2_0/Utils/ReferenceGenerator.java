package com.example.fooddelivery2_0.Utils;

import javax.crypto.KeyGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public interface ReferenceGenerator {
    static String generateReference() throws NoSuchAlgorithmException {

        var keyGen = KeyGenerator.getInstance("DES");
        SecureRandom secRandom = new SecureRandom();
        keyGen.init(secRandom);
        var ref = Base64.getEncoder().encodeToString(keyGen.generateKey().getEncoded())
                .replace("+","V")
                .replace("/","ù")
                .replace("&","=")
                .replace("$","*");
        return ref+"$";

    }

}
