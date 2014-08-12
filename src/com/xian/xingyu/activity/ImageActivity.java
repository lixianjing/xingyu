package com.xian.xingyu.activity;

import android.os.Bundle;
import android.view.View;

import com.xian.xingyu.R;
import com.xian.xingyu.base.BaseActivity;
import com.xian.xingyu.view.CommonHeadView;

public class ImageActivity extends BaseActivity {

    private CommonHeadView mHeadView;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.image_activity);
        setNeedBackGesture(true);

        initTitle();
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void initTitle() {
        mHeadView = (CommonHeadView) findViewById(R.id.head_common);
        mHeadView.setTitle("图片页面");
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
