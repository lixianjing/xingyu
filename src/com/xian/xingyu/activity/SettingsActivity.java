
package com.xian.xingyu.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.xian.xingyu.MainApp;
import com.xian.xingyu.R;
import com.xian.xingyu.base.BaseActivity;
import com.xian.xingyu.view.CommonHeadView;

public class SettingsActivity extends BaseActivity implements OnClickListener {

    private CommonHeadView headView;
    private Button btn;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.settings_activity);
        setNeedBackGesture(true);

        headView = (CommonHeadView) findViewById(R.id.Settings_head);
        headView.setTitle("设置页面");
        headView.setLeftText("返回", new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                onBackPressed();
            }
        });

        headView.setRightText("编辑", new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Log.e("lmf", ">>>>>>>>编辑>>>>>>>>");
            }
        });

        btn = (Button) findViewById(R.id.settings_logout_btn);
        btn.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.settings_logout_btn:

                if (MainApp.sAccountManager != null) {
                    MainApp.sAccountManager.logout();
                    MainApp.sAccountManager.release();
                    MainApp.sAccountManager = null;
                }
                onBackPressed();
                break;

            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Log.e("lmf", ">>>>>>>>>onDestroy>>>>>>");
    }

}
