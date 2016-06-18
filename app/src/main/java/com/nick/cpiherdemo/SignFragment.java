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
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignFragment extends Fragment implements View.OnClickListener {

    public static final String publicKesStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCSSBDpRaB+7UaAterQW1JBEJG6drgAztaHv/ok5dQI/egx2dZQ4kiaM3kWQ9Gt0/bLS+1wb4LypUbLQJPUSb+Oz61CTShbQCjt6iEtQglUyzk40GWm/m0tQ946j5evxxNG+hx3GC43oiR3wcbkPwlE/SS7DF3KZxrJ0kQf1EIy4QIDAQAB";
    public static final String privateKesStr = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJJIEOlFoH7tRoC16tBbUkEQkbp2uADO1oe/+iTl1Aj96DHZ1lDiSJozeRZD0a3T9stL7XBvgvKlRstAk9RJv47PrUJNKFtAKO3qIS1CCVTLOTjQZab+bS1D3jqPl6/HE0b6HHcYLjeiJHfBxuQ/CUT9JLsMXcpnGsnSRB/UQjLhAgMBAAECgYAjCVHLnZqobApz756TUxwra12MLL07rE7j6s5uIgIcOTxxHDzh2KENFgENnSbOnLNC1CfFw1+44G9JZWlC8nsHy+9yyouCG9c0Xmk4lfp3q5UDKiHUVPzCJ4RF4oaAV0fltAxZQZ4By0YhsW64MYMBWX4SSza8UHPooaLCuwfwAQJBAMOr7/o8DJv2OiYdoqLNJZVKLtVGYQqGwyhjcFbKU6pk/EBw5JBP79TrcsEJ+igL8qlMHyWC9RtpSNpVXKDNmoECQQC/YdeAiLb2olljaCrJuAHF/rdGw0BL6t1O/gl4jBPiOd0ADu/unBerbpg630yweAgZC72fsuaH83Bn99MB9qhhAkEAiR3ig8MyvtPz+aPIxTbnAE/Es9WmyP6YoaPVJCySJpSvo+S4dlxd3yHC/30jXI7K1FIwfVPguP21fLJWv6R0gQJBAKqR32n1b2w9ogGRE6GZWCtJK7vrxWBkQT5n97Ty073q6Gdm6Lz3bbki5paB8m3NbRo1dpng7sn4VBS1seCsH8ECQAJe49UUHUGtFdFuxpyAn3V8+TogOg5CM10/rJTj4aeOnXpysbyaX/PIrtZSHgho7BvAPfpbWZhYqZKSJPmn6UI=";
    private EditText edit_src;
    private EditText edit_rlt;

    public SignFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edit_src = ((EditText) view.findViewById(R.id.sign_src));
        edit_rlt = ((EditText) view.findViewById(R.id.sign_rlt));
        view.findViewById(R.id.sign_sign).setOnClickListener(this);
        view.findViewById(R.id.sign_verify).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
            //
            PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(Base64.decode(publicKesStr, Base64.NO_WRAP)));
            PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(Base64.decode(privateKesStr, Base64.NO_WRAP)));
            //摘要算法With加密算法  SHA1 SHA256
            Signature signature = Signature.getInstance("MD5WithRSA");
            String src = edit_src.getText().toString();
            if (!TextUtils.isEmpty(src)) {

                switch (v.getId()) {
                    case R.id.sign_sign:
                        signature.initSign(privateKey);
                        signature.update(src.getBytes("UTF-8"));
                        byte[] bytes = signature.sign();
                        edit_rlt.setText(Base64.encodeToString(bytes, Base64.NO_WRAP));
                        break;
                    case R.id.sign_verify:
                        String rlt = edit_rlt.getText().toString();
                        if (!TextUtils.isEmpty(rlt)) {
                            signature.initVerify(publicKey);
                            signature.update(src.getBytes("UTF-8"));
                            boolean verify = signature.verify(Base64.decode(rlt, Base64.NO_WRAP));
                            if (verify) {
                                Toast.makeText(getActivity(), "数据正确", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "数据错误", Toast.LENGTH_SHORT).show();
                            }
                        }
                        break;
                }
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException | SignatureException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}
