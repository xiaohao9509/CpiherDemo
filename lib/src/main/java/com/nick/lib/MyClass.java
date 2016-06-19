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
            //得到RSA算法
            KeyPairGenerator rsa = KeyPairGenerator.getInstance("RSA");
            //初始化为1024位的RSA算法
            rsa.initialize(1024);
            //得到公钥私钥键值对
            KeyPair keyPair = rsa.generateKeyPair();
            //得到公钥与私钥
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

            //将公钥私钥输出Encoded
            byte[] publicKeyEncoded = publicKey.getEncoded();
            byte[] privateKeyEncoded = privateKey.getEncoded();


            System.out.println("公钥: "+ Base64.encodeToString(publicKeyEncoded,Base64.NO_WRAP));
            System.out.println("密钥: "+ Base64.encodeToString(privateKeyEncoded,Base64.NO_WRAP));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
