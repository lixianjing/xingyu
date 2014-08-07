package com.xian.xingyu.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.xian.xingyu.R;
import com.xian.xingyu.base.BaseActivity;
import com.xian.xingyu.view.CommonHeadView;


public class AddActivity extends BaseActivity {


    private CommonHeadView headView;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.add_activity);


        headView = (CommonHeadView) findViewById(R.id.commonhead);
        headView.setTitle("编辑信息页面");
        headView.setLeftText("取消", new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                onBackPressed();
            }
        });

        headView.setRightText("保存", new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Log.e("lmf", ">>>>>>>>编辑>>>>>>>>");
            }
        });

    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        // overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Log.e("lmf", "onDestroy>>>>>>>>>>>>>>>>>>>");
    }



}
