package com.xian.xingyu.view;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.xian.xingyu.R;
import com.xian.xingyu.login.QQAccountManager;

public class LoginDialog extends Dialog implements android.view.View.OnClickListener {

    private Activity mActivity;
    private Button mQqBtn, mWeiboBtn;


    public LoginDialog(Activity activity, int theme) {
        super(activity, theme);
        this.mActivity = activity;
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
                Log.e("lmf", ">>>>>>>>>>>>>>>login_qq_btn>>>>>>>>");
                QQAccountManager.getInstance(mActivity.getApplicationContext()).login(mActivity);
                break;
            case R.id.login_weibo_btn:

                break;

            default:
                break;
        }

    }
}
