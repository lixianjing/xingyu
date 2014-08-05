
package com.xian.xingyu.view;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.xian.xingyu.R;
import com.xian.xingyu.activity.MainActivity;
import com.xian.xingyu.login.QQAccountManager;

public class LoginDialog extends Dialog implements android.view.View.OnClickListener {

    private final MainActivity mActivity;
    private Button mQqBtn, mWeiboBtn;

    public LoginDialog(MainActivity activity) {
        super(activity, R.style.MyDialog);
        this.mActivity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_login);
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
                this.dismiss();
                break;
            case R.id.login_weibo_btn:

                break;

            default:
                break;
        }

    }
}
