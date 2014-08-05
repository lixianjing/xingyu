
package com.xian.xingyu.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.xian.xingyu.R;
import com.xian.xingyu.activity.MainActivity;

public class LoginDialog extends Dialog implements android.view.View.OnClickListener {

    private final Context mContext;
    private final Handler mHandler;
    private Button mQqBtn, mWeiboBtn;

    public LoginDialog(Context context, Handler handler) {
        super(context, R.style.MyDialog);
        this.mContext = context;
        mHandler = handler;
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

                mHandler.sendEmptyMessage(MainActivity.MSG_LOGIN_QQ);

                this.dismiss();
                break;
            case R.id.login_weibo_btn:
                mHandler.sendEmptyMessage(MainActivity.MSG_LOGIN_WEIBO);

                this.dismiss();
                break;

            default:
                break;
        }

    }
}
