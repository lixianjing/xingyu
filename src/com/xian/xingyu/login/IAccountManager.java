package com.xian.xingyu.login;

import android.app.Activity;
import android.os.Handler;



public interface IAccountManager {

    public boolean isLogin();

    public void login(Activity activity);

    public void logout();

    public void loadAccount(String uid, String accessToken, String expiresIn);

    public void getPersonalInfo();

    public int getType();

    public void setHandler(Handler handler);

    public void release();
}
