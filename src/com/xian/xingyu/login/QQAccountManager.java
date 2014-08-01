package com.xian.xingyu.login;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;



public class QQAccountManager implements IAccountManager {

    private static final String APP_ID = "222222";

    private Tencent mTencent;
    private Context mContext;

    private static QQAccountManager instance;

    private synchronized static void createInstance(Context context) {
        if (instance == null) {
            instance = new QQAccountManager(context);
        }

    }

    public static QQAccountManager getInstance(Context context) {
        if (instance == null) {
            createInstance(context);
        }
        return instance;
    }

    private QQAccountManager(Context context) {
        super();
        this.mContext = context;
        mTencent = Tencent.createInstance(APP_ID, mContext);


    }


    @Override
    public boolean isLogin() {
        // TODO Auto-generated method stub

        if (mTencent != null && mTencent.isSessionValid()) {
            return true;
        }
        return false;
    }

    @Override
    public void login(Activity activity) {
        // TODO Auto-generated method stub

        mTencent.login(activity, APP_ID, new BaseUiListener() {

        });
    }

    @Override
    public void logout() {
        // TODO Auto-generated method stub
        mTencent.logout(mContext);
    }


    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            Log.e("lmf", ">>>onComplete>>>登录成功>>>>>>>>");
            doComplete((JSONObject) response);
        }

        protected void doComplete(JSONObject values) {

        }

        @Override
        public void onError(UiError e) {
            Log.e("lmf", ">>>onError>>>>>>>>");
        }

        @Override
        public void onCancel() {
            Log.e("lmf", ">>>onCancel>>>>>>>>");
        }
    }



}
