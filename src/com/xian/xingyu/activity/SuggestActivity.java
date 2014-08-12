package com.xian.xingyu.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xian.xingyu.R;
import com.xian.xingyu.base.BaseActivity;
import com.xian.xingyu.view.CommonHeadView;

public class SuggestActivity extends BaseActivity {

    private Context mContext;

    private CommonHeadView mHeadView;
    private EditText messageEt, contactEt;
    private Button sendBtn;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        mContext = this;
        setContentView(R.layout.suggest_activity);
        setNeedBackGesture(true);

        initTitle();
        initView();

    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void initView() {
        messageEt = (EditText) findViewById(R.id.suggest_message_et);
        contactEt = (EditText) findViewById(R.id.suggest_contact_et);
        sendBtn = (Button) findViewById(R.id.suggest_send_btn);

        sendBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Toast.makeText(mContext, "发送以后什么逻辑", 2000).show();
            }
        });

    }

    private void initTitle() {
        mHeadView = (CommonHeadView) findViewById(R.id.head_common);
        mHeadView.setTitle("建议页面");
        mHeadView.setLeftText("返回", new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                onBackPressed();
            }
        });

        mHeadView.setRightText("保存", new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
    }

}
