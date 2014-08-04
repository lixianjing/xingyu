package com.xian.xingyu;

import android.app.Application;
import android.util.Log;

import com.xian.xingyu.login.QQAccountManager;



public class MainApp extends Application {

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        Log.e("lmf", ">>>>MainApp>>>>>>>onCreate>>>>>>");
        QQAccountManager.getInstance(this.getApplicationContext());


    }



}
