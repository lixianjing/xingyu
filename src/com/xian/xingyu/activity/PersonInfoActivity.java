
package com.xian.xingyu.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.xian.xingyu.R;
import com.xian.xingyu.base.BaseActivity;
import com.xian.xingyu.view.CommonHeadView;

public class PersonInfoActivity extends BaseActivity {

    private CommonHeadView headView;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.person_activity);
        setNeedBackGesture(true);

        headView = (CommonHeadView) findViewById(R.id.person_head);
        headView.setTitle("个人信息页面");
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
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}
