package com.nick.cpiherdemo;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


/**
 * A simple {@link Fragment} subclass.
 */
public class RsaFragment extends Fragment implements View.OnClickListener {
    //公钥:MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCSSBDpRaB+7UaAterQW1JBEJG6drgAztaHv/ok5dQI/egx2dZQ4kiaM3kWQ9Gt0/bLS+1wb4LypUbLQJPUSb+Oz61CTShbQCjt6iEtQglUyzk40GWm/m0tQ946j5evxxNG+hx3GC43oiR3wcbkPwlE/SS7DF3KZxrJ0kQf1EIy4QIDAQAB
    //密钥: MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJJIEOlFoH7tRoC16tBbUkEQkbp2uADO1oe/+iTl1Aj96DHZ1lDiSJozeRZD0a3T9stL7XBvgvKlRstAk9RJv47PrUJNKFtAKO3qIS1CCVTLOTjQZab+bS1D3jqPl6/HE0b6HHcYLjeiJHfBxuQ/CUT9JLsMXcpnGsnSRB/UQjLhAgMBAAECgYAjCVHLnZqobApz756TUxwra12MLL07rE7j6s5uIgIcOTxxHDzh2KENFgENnSbOnLNC1CfFw1+44G9JZWlC8nsHy+9yyouCG9c0Xmk4lfp3q5UDKiHUVPzCJ4RF4oaAV0fltAxZQZ4By0YhsW64MYMBWX4SSza8UHPooaLCuwfwAQJBAMOr7/o8DJv2OiYdoqLNJZVKLtVGYQqGwyhjcFbKU6pk/EBw5JBP79TrcsEJ+igL8qlMHyWC9RtpSNpVXKDNmoECQQC/YdeAiLb2olljaCrJuAHF/rdGw0BL6t1O/gl4jBPiOd0ADu/unBerbpg630yweAgZC72fsuaH83Bn99MB9qhhAkEAiR3ig8MyvtPz+aPIxTbnAE/Es9WmyP6YoaPVJCySJpSvo+S4dlxd3yHC/30jXI7K1FIwfVPguP21fLJWv6R0gQJBAKqR32n1b2w9ogGRE6GZWCtJK7vrxWBkQT5n97Ty073q6Gdm6Lz3bbki5paB8m3NbRo1dpng7sn4VBS1seCsH8ECQAJe49UUHUGtFdFuxpyAn3V8+TogOg5CM10/rJTj4aeOnXpysbyaX/PIrtZSHgho7BvAPfpbWZhYqZKSJPmn6UI=
    public static final String privateKesStr = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJJIEOlFoH7tRoC16tBbUkEQkbp2uADO1oe/+iTl1Aj96DHZ1lDiSJozeRZD0a3T9stL7XBvgvKlRstAk9RJv47PrUJNKFtAKO3qIS1CCVTLOTjQZab+bS1D3jqPl6/HE0b6HHcYLjeiJHfBxuQ/CUT9JLsMXcpnGsnSRB/UQjLhAgMBAAECgYAjCVHLnZqobApz756TUxwra12MLL07rE7j6s5uIgIcOTxxHDzh2KENFgENnSbOnLNC1CfFw1+44G9JZWlC8nsHy+9yyouCG9c0Xmk4lfp3q5UDKiHUVPzCJ4RF4oaAV0fltAxZQZ4By0YhsW64MYMBWX4SSza8UHPooaLCuwfwAQJBAMOr7/o8DJv2OiYdoqLNJZVKLtVGYQqGwyhjcFbKU6pk/EBw5JBP79TrcsEJ+igL8qlMHyWC9RtpSNpVXKDNmoECQQC/YdeAiLb2olljaCrJuAHF/rdGw0BL6t1O/gl4jBPiOd0ADu/unBerbpg630yweAgZC72fsuaH83Bn99MB9qhhAkEAiR3ig8MyvtPz+aPIxTbnAE/Es9WmyP6YoaPVJCySJpSvo+S4dlxd3yHC/30jXI7K1FIwfVPguP21fLJWv6R0gQJBAKqR32n1b2w9ogGRE6GZWCtJK7vrxWBkQT5n97Ty073q6Gdm6Lz3bbki5paB8m3NbRo1dpng7sn4VBS1seCsH8ECQAJe49UUHUGtFdFuxpyAn3V8+TogOg5CM10/rJTj4aeOnXpysbyaX/PIrtZSHgho7BvAPfpbWZhYqZKSJPmn6UI=";
    public static final String publicKesStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCSSBDpRaB+7UaAterQW1JBEJG6drgAztaHv/ok5dQI/egx2dZQ4kiaM3kWQ9Gt0/bLS+1wb4LypUbLQJPUSb+Oz61CTShbQCjt6iEtQglUyzk40GWm/m0tQ946j5evxxNG+hx3GC43oiR3wcbkPwlE/SS7DF3KZxrJ0kQf1EIy4QIDAQAB";
    private EditText mSrc;
    private EditText mRlt;

    public RsaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rsa, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSrc = (EditText) view.findViewById(R.id.rsa_src);
        mRlt = (EditText) view.findViewById(R.id.rsa_rlt);

        view.findViewById(R.id.rsa_encrypt).setOnClickListener(this);
        view.findViewById(R.id.rsa_decrypt).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
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


    }
}
