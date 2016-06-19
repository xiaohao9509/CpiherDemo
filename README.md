# CpiherDemo
##这是我的一个关于加密解密的Demo
##MD5,Base64,Des,Rsa以及签名
###1.算法简述
* 1.MD5摘要算法
消息摘要算法第五版(Message Digest Algorithm)，是一种单向加密算法，只能加密、无法解密。
然而MD5加密算法已经被中国山东大学王小云教授成功破译，但是在安全性要求不高的场景下，
MD5加密算法仍然具有应用价值。
* 2.Base64
使用BASE64算法通常用作对二进制数据进行加密，加密之后的数据不易被肉眼识别。
严格来说，经过BASE64加密的数据其实没有安全性可言，因为它的加密解密算法都是公开的，
典型的防菜鸟不防程序猿的呀。 经过标准的BASE64算法加密后的数据， 
通常包含`/`,`+`,`=`等特殊符号，不适合作为url参数传递
* 3.DES对称加密/解密
数据加密标准算法(Data Encryption Standard)，和BASE64最明显的区别就是有一个工作密钥，
该密钥既用于加密、也用于解密，并且要求密钥是一个长度至少大于8位的字符串。
使用DES加密、解密的核心是确保工作密钥的安全性。
* 4.Rsa非对称加密/解密
RSA算法是非对称加密算法的典型代表，既能加密、又能解密。
和对称加密算法比如DES的明显区别在于用于加密、解密的密钥是不同的。
使用RSA算法，只要密钥足够长(一般要求1024bit)，加密的信息是不能被破解的。  用户通过https协议访问服务器时，就是使用非对称加密算法进行数据的加密、解密操作的。
服务器发送数据给客户端时使用私钥（private key）进行加密，
并且使用加密之后的数据和私钥生成数字签名（digital signature）并发送给客户端。
客户端接收到服务器发送的数据会使用公钥（publickey）对数据来进行解密,
并且根据加密数据和公钥验证数字签名的有效性，防止加密数据在传输过程中被第三方进行了修改。
客户端发送数据给服务器时使用公钥进行加密，服务器接收到加密数据之后使用私钥进行解密。
###2.算法的使用
####1.MD5摘要算法
MD5是一种摘要算法,如同他的名字一样,它会将byte数组经过运算后得到一段摘要byte数组,但是并不能通过这个byte数组解密,
它通常用来验证数据的完整性,代码如下:
```javascript
 String src = edit.getText().toString();
        if (!TextUtils.isEmpty(src)) {
            try {
                //获得md5摘要算法的实例
                MessageDigest md5 = MessageDigest.getInstance("MD5");
                //运算得到摘要byte数组
                byte[] digest = md5.digest(src.getBytes("UTF-8"));
                StringBuilder builder = new StringBuilder();
                for (byte b : digest) {
                    //将每一个byte转换成两位十六进制
                    builder.append(String.format("%02x", b));
                }
                text.setText(builder.toString());

            } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
```
####2.Base64算法
Base64严格来说不是一个加密算法,它的加密解密算法在网络上是公开的,它通常在当数据在网络上传播时加密一下,
让肉眼无法一下分辨
代码如下:
```javascript
case R.id.base64_encode:
                String src = mEdit.getText().toString();
                if (!TextUtils.isEmpty(src)) {
                    try {
                        byte[] bytes = src.getBytes("UTF-8");
                        //NO_WRAP 不包含/n的形式  URL 只能编码字符串
                        String rlt = Base64.encodeToString(bytes, Base64.NO_WRAP);
                        mTextView.setText(rlt);
////                        直接转bit数组 以%分割
//                        String rlt = URLEncoder.encode(src, "UTF-8");
//                        mTextView.setText(rlt);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.base64_decode:
                String rlt = mTextView.getText().toString();
                if (!TextUtils.isEmpty(rlt)) {
                    try {
                        byte[] decode = Base64.decode(rlt, Base64.NO_WRAP);
                        mEdit.setText(new String(decode, "UTF-8"));
//                        String decode = URLDecoder.decode(rlt, "utf-8");
//                        mTextView.setText(decode);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                break;
```
####3.DES对称加密/解密
DES是一个种对称加密,解密的算法,对称的意思是,它解密加密的密钥是一个长度大于8位的字符串,不过原版DES容易被暴力破解,
后来出了3DES,现在比较安全的对称加密算法是AES,通常大量数据传输时用DES,3DES或者AES对数据进行加密.
代码如下:

```javascript
//    private static final byte[] key = {1, 2, 3, 4, 5, 6, 7, 8};
    private static final byte[] key = {
            1, 2, 3, 4, 5, 6, 7, 8,
            1, 2, 3, 4, 5, 6, 7, 8,
            1, 2, 3, 4, 5, 6, 7, 8
    };
    private static final String TAG = DesFragment.class.getSimpleName();
    /**
     * 算法名,
     * Des 密钥为8位
     * 3Des(DESede)  密钥为24位
     * AES 密钥为32位
     */
     //DES
//    public static final String algorithm = "Des";
    //3DES
    private static final String algorithm = "DESede";
try {
            //非对称工厂
//            KeyFactory factory = KeyFactory.getInstance("Des");
            //对称工厂
            SecretKeyFactory des = SecretKeyFactory.getInstance(algorithm);
            //只能放8位bit数组
            //获得Des
//            SecretKey secretKey = des.generateSecret(new DESKeySpec(key));
            //获得DESede
//            SecretKey secretKey = des.generateSecret(new DESedeKeySpec(key));
            //通过所给的名字获得对应加密算法
            SecretKey secretKey = des.generateSecret(new SecretKeySpec(key, algorithm));
            //两个算法名字必须一样
            Cipher cipher = Cipher.getInstance(algorithm);

            switch (v.getId()) {
                case R.id.des_encrypt:
                    String src = mEditSrc.getText().toString();
                    if (!TextUtils.isEmpty(src)) {
                        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                        byte[] bytes = cipher.doFinal(src.getBytes("UTF-8"));
                        //用base64变成字符串
                        Log.d(TAG, "onClick: " + bytes.length);
                        mEditRlt.setText(Base64.encodeToString(bytes, Base64.NO_WRAP));
//                        mEditRlt.setText(new String(bytes,"utf-8"));
                    }
                    break;
                case R.id.des_decrypt:
                    String rlt = mEditRlt.getText().toString();
                    if (!TextUtils.isEmpty(rlt)) {
                        cipher.init(Cipher.DECRYPT_MODE, secretKey);
                        byte[] bytes = cipher.doFinal(Base64.decode(rlt, Base64.NO_WRAP));
//                        byte[] bytes = cipher.doFinal(rlt.getBytes("utf-8"));
                        mEditRlt.setText(new String(bytes, "utf-8"));
                    }
                    break;
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException | NoSuchPaddingException | BadPaddingException | UnsupportedEncodingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
```
####4.RSA非对称加密/解密
RSA非对称算法,是一个相当而言比较安全的算法,非对称的意思是此算法有一个公钥与一个私钥,可以用公钥加密,私钥解密,
或者私钥加密,公钥加密,举个通俗的例子:
***
小明想秘密给小英发送消息

小英手里有一个盒子（public key），这个盒子只有小英手里的钥匙（private key）才打得开

小英把盒子送给小明（分发公钥）

小明写好消息放进盒子里，锁上盒子（公钥加密）

小明把盒子寄给小英（密文传输）

小英用手里的钥匙打开盒子，得到小明的消息（私钥解密）

假设小刚劫持了盒子，因为没有小英的钥匙，他也打不开
***
所以我们首先需要获得对应的公钥和私钥,代码如下
```javascript
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
```
得到公钥私钥后对数据进行加解密,代码如下:
```javascript
try {
            //初始化key工厂为RSA
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            //用X509协议获得到公钥
            PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(Base64.decode(publicKesStr, Base64.NO_WRAP)));
            //用PKCS协议获得到私钥
            PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(Base64.decode(privateKesStr, Base64.NO_WRAP)));
            //得到RSA算法
            Cipher cipher = Cipher.getInstance("RSA");
            switch (v.getId()) {
                case R.id.rsa_encrypt:
                    String src = mSrc.getText().toString();
                    if (!TextUtils.isEmpty(src)) {
                        //初始化为加密模式,用私钥加密
                        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
                        //得到加密后的byte数组
                        byte[] bytes = cipher.doFinal(src.getBytes("UTF-8"));
                        //将密文输出成Base64的字符串
                        mRlt.setText(Base64.encodeToString(bytes, Base64.NO_WRAP));
                    }
                    break;
                case R.id.rsa_decrypt:
                    String rlt = mRlt.getText().toString();
                    if (!TextUtils.isEmpty(rlt)) {
                        //初始化为解密模式,用公钥解密
                        cipher.init(Cipher.DECRYPT_MODE, publicKey);
                        //将转换成Base64的字符串还原成最开始的密文
                        byte[] bytes = cipher.doFinal(Base64.decode(rlt, Base64.NO_WRAP));
                        //得到解密后的铭明文
                        mRlt.setText(new String(bytes, "utf-8"));
                    }
                    break;
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | UnsupportedEncodingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
```
##总结
数据加密：

为了保证数据的安全性，不会泄露数据的真是内容

数据摘要：

为了确保数据中途没有被篡改过，数据的内容时完整的正确的

数据签名：

为了保证数据是对方过来的，而不是从别的地方来的


在真实的互联网应用中，加密场景非常重要

1、对数据加密

2、对密文生成摘要

3、对摘要进行签名

4、将密文和签名一起发送

这样就保证了数据的安全性

这里还有一个唯一的漏洞就是接收方的公钥被人替换了，所以就出现了证书认证中心

确保公钥的安全性，在发送数据的时候加上数字证书。
