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
import android.widget.TextView;

import java.io.UnsupportedEncodingException;


/**
 * A simple {@link Fragment} subclass.
 */
public class Base64Fragment extends Fragment implements View.OnClickListener {


    private EditText mEdit;
    private TextView mTextView;

    public Base64Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_base64, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEdit = (EditText) view.findViewById(R.id.base64_src);
        mTextView = (TextView) view.findViewById(R.id.base64_rlt);
        view.findViewById(R.id.base64_encode).setOnClickListener(this);
        view.findViewById(R.id.base64_decode).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
        }
    }
}
