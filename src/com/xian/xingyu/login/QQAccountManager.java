
package com.xian.xingyu.login;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.tencent.connect.UserInfo;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.xian.xingyu.activity.MainActivity;
import com.xian.xingyu.bean.PersonInfo;
import com.xian.xingyu.db.DBInfo;
import com.xian.xingyu.db.DBManager;
import com.xian.xingyu.util.BaseUtil;
import com.xian.xingyu.util.Configs;

import org.json.JSONException;
import org.json.JSONObject;

public class QQAccountManager implements IAccountManager {

    private static final String APP_ID = "222222";

    private Context mContext;

    private DBManager mDBManager;
    private Configs mConfigs;
    private Tencent mTencent;
    private Handler mHandler;

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
        mTencent = Tencent.createInstance(APP_ID, context.getApplicationContext());
        mDBManager = DBManager.getInstance(context.getApplicationContext());
        mConfigs = Configs.getInstance(context.getApplicationContext());

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
        mTencent.login(activity, APP_ID, new IUiListener() {

            @Override
            public void onCancel() {
                // TODO Auto-generated method stub
                mHandler.sendEmptyMessage(MainActivity.MSG_LOGIN_CANCEL);
            }

            @Override
            public void onComplete(Object arg0) {
                // TODO Auto-generated method stub

                final JSONObject json = (JSONObject) arg0;

                try {
                    Editor editor = mConfigs.getEditor();
                    editor.putString(Configs.KEY, json.getString("openid"));
                    editor.putString(Configs.TOKEN, json.getString("access_token"));

                    long expires_in = json.getLong("expires_in");
                    expires_in = System.currentTimeMillis() + expires_in * 1000;

                    editor.putLong(Configs.AUTH_TIME, expires_in);
                    editor.putInt(Configs.TYPE, Configs.TYPE_QQ);
                    editor.putInt(Configs.INFO_STATUS, Configs.INFO_STATUS_DEFAULT);
                    editor.commit();
                    mHandler.sendEmptyMessage(MainActivity.MSG_LOGIN_SUCCESS);
                } catch (JSONException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                    mHandler.sendEmptyMessage(MainActivity.MSG_LOGIN_ERROR);
                }

                Log.e("lmf", ">>>login>>>>>>>>>onComplete>>>>>>>" + arg0);

            }

            @Override
            public void onError(UiError arg0) {
                // TODO Auto-generated method stub
                mHandler.sendEmptyMessage(MainActivity.MSG_LOGIN_ERROR);
            }

        });
    }

    @Override
    public void logout() {
        // TODO Auto-generated method stub
        mTencent.logout(mContext);

        BaseUtil.cleanLoginConfig(mConfigs);

    }

    @Override
    public void loadAccount(String uid, String accessToken, String expiresIn) {
        // TODO Auto-generated method stub
        mTencent.setOpenId(uid);

        long time = Long.valueOf(expiresIn) - System
                .currentTimeMillis() / 1000;

        mTencent.setAccessToken(accessToken, String.valueOf(time));

    }

    @Override
    public void getPersonalInfo() {
        // TODO Auto-generated method stub
        UserInfo userInfo = new UserInfo(mContext, mTencent.getQQToken());
        userInfo.getUserInfo(new IUiListener() {

            @Override
            public void onCancel() {
                // TODO Auto-generated method stub
                mHandler.sendEmptyMessage(MainActivity.MSG_LOGIN_GET_INFO_CANCEL);
            }

            @Override
            public void onComplete(Object arg0) {

                final JSONObject json = (JSONObject) arg0;
                try {

                    PersonInfo personal = new PersonInfo();
                    personal.setIcon(null);
                    personal.setIconUri(null);
                    personal.setIconThumb(null);
                    personal.setIconThumbUri(json.getString("figureurl_qq_2"));
                    personal.setName(json.getString("nickname"));
                    personal.setDesc(json.getString("msg"));
                    personal.setGender(DBInfo.Personal.GENDER_NONE);
                    personal.setLocal(json.getString("province") + "," + json.getString("city"));
                    personal.setBirthYear(1990);
                    personal.setBirthMonth(1);
                    personal.setBirthDay(1);
                    personal.setBirthType(DBInfo.Personal.BIRTH_TYPE_GREGORIAN);

                    mDBManager.updatePersonal(personal);

                    Message message = mHandler
                            .obtainMessage(MainActivity.MSG_LOGIN_GET_INFO_SUCCESS);
                    message.obj = personal;
                    message.sendToTarget();

                } catch (Exception e) {
                    e.printStackTrace();
                    mHandler.sendEmptyMessage(MainActivity.MSG_LOGIN_GET_INFO_ERROR);
                }

            }

            @Override
            public void onError(UiError arg0) {
                // TODO Auto-generated method stub
                mHandler.sendEmptyMessage(MainActivity.MSG_LOGIN_GET_INFO_ERROR);
            }

        });
    }

    @Override
    public int getType() {
        // TODO Auto-generated method stub
        return Configs.TYPE_QQ;

    }

    @Override
    public void setHanlder(Handler mHanlder) {
        this.mHandler = mHanlder;
    }

    @Override
    public void release() {
        // TODO Auto-generated method stub
        mTencent = null;
        mDBManager = null;
        mConfigs = null;
        mContext = null;
        mHandler = null;
        instance = null;
    }

}
