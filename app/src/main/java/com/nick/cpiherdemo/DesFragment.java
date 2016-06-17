package com.nick.cpiherdemo;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;


/**
 * A simple {@link Fragment} subclass.
 */
public class DesFragment extends Fragment implements View.OnClickListener {

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
//    public static final String algorithm = "Des";
    private static final String algorithm = "DESede";
    private EditText mEditSrc;
    private EditText mEditRlt;

    public DesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_des, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEditSrc = (EditText) view.findViewById(R.id.des_src);
        mEditRlt = (EditText) view.findViewById(R.id.des_rlt);
        Button encrypt = (Button) view.findViewById(R.id.des_encrypt);
        Button decrypt = (Button) view.findViewById(R.id.des_decrypt);
        encrypt.setOnClickListener(this);
        decrypt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        try {
            //非对称工厂
//            KeyFactory factory = KeyFactory.getInstance("Des");
            //对称工厂
            SecretKeyFactory des = SecretKeyFactory.getInstance(algorithm);
            //只能放8为bit数组
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


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }

    }
}
