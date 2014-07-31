package com.xian.xingyu.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.xian.xingyu.R;

public class LoginDialog extends Dialog implements android.view.View.OnClickListener {

    private Context mContext;
    private Button mQqBtn, mWeiboBtn;


    public LoginDialog(Context context, int theme) {
        super(context, theme);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog);
        mQqBtn = (Button) findViewById(R.id.login_qq_btn);
        mWeiboBtn = (Button) findViewById(R.id.login_weibo_btn);

        mQqBtn.setOnClickListener(this);
        mWeiboBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch (arg0.getId()) {
            case R.id.login_qq_btn:

                break;
            case R.id.login_weibo_btn:

                break;

            default:
                break;
        }

    }
}
