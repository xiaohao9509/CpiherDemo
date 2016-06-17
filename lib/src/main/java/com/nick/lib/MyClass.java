package com.nick.lib;


import android.util.Base64;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * Created by Administrator on 2016/4/16.
 * User: Nick
 * Date: 2016/4/16
 * Email: 305812387@qq.com
 * Project: CpiherDemo
 */
public class MyClass {
    public static void main(String[] args) {
        try {
            KeyPairGenerator rsa = KeyPairGenerator.getInstance("RSA");
            rsa.initialize(1024);
            KeyPair keyPair = rsa.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

            byte[] publicKeyEncoded = publicKey.getEncoded();
            byte[] privateKeyEncoded = privateKey.getEncoded();

            System.out.println("公钥: "+ Base64.encodeToString(publicKeyEncoded,Base64.NO_WRAP));
            System.out.println("密钥: "+ Base64.encodeToString(privateKeyEncoded,Base64.NO_WRAP));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
