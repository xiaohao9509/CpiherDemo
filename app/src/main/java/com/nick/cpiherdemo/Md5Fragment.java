package com.nick.cpiherdemo;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * A simple {@link Fragment} subclass.
 */
public class Md5Fragment extends Fragment implements View.OnClickListener {


    private EditText edit;
    private TextView text;
    private Button button;

    public Md5Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_md5, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edit = ((EditText) view.findViewById(R.id.md5_src));
        text = ((TextView) view.findViewById(R.id.md5_rlt));
        button = ((Button) view.findViewById(R.id.md5_commit));
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String src = edit.getText().toString();
        if (!TextUtils.isEmpty(src)) {
            try {
                //获得md5摘要算法的实例
                MessageDigest md5 = MessageDigest.getInstance("MD5");
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
    }
}
