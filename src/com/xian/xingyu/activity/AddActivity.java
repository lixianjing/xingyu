package com.xian.xingyu.activity;

import android.os.Bundle;

import com.xian.xingyu.R;
import com.xian.xingyu.base.BaseActivity;


public class AddActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.add_activity);
        setNeedBackGesture(true);
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }


}
