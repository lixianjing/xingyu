package com.xian.xingyu;

import android.app.Application;
import android.util.Log;

import com.xian.xingyu.login.IAccountManager;


public class MainApp extends Application {

    public static IAccountManager sAccountManager;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        Log.e("lmf", ">>>>MainApp>>>>>>>onCreate>>>>>>");

    }


    public static boolean isLogin() {
        return (MainApp.sAccountManager != null && MainApp.sAccountManager.isLogin());
    }

}
