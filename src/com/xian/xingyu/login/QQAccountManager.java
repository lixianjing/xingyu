
package com.xian.xingyu.login;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.tencent.connect.UserInfo;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.xian.xingyu.activity.MainActivity;
import com.xian.xingyu.bean.Personal;
import com.xian.xingyu.db.DBInfo;
import com.xian.xingyu.db.DBManager;
import com.xian.xingyu.util.BaseUtil;
import com.xian.xingyu.util.Configs;

import org.json.JSONException;
import org.json.JSONObject;

public class QQAccountManager {

    private static final String APP_ID = "222222";
    private final Context mContext;

    private final DBManager mDBManager;
    private final Configs mConfigs;
    private final Tencent mTencent;

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
        mDBManager = DBManager.getInstance(context.getApplicationContext());
        mConfigs = Configs.getInstance(context.getApplicationContext());

    }

    public boolean isLogin() {
        // TODO Auto-generated method stub

        if (mTencent != null && mTencent.isSessionValid()) {
            return true;
        }
        return false;
    }

    public void login(final MainActivity activity) {
        // TODO Auto-generated method stub

        mTencent.login(activity, APP_ID, new IUiListener() {

            @Override
            public void onCancel() {
                // TODO Auto-generated method stub

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
                } catch (JSONException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

                Log.e("lmf", ">>>login>>>>>>>>>onComplete>>>>>>>" + arg0);

            }

            @Override
            public void onError(UiError arg0) {
                // TODO Auto-generated method stub

            }

        });
    }

    public void logout() {
        // TODO Auto-generated method stub
        mTencent.logout(mContext);
    }

    public void getUserInfo(final MainActivity activity) {

        UserInfo userInfo = new UserInfo(mContext, mTencent.getQQToken());
        userInfo.getUserInfo(new IUiListener() {

            @Override
            public void onCancel() {
                // TODO Auto-generated method stub
                mTencent.logout(mContext);
            }

            @Override
            public void onComplete(Object arg0) {

                final JSONObject json = (JSONObject) arg0;
                try {
                    Personal personal = new Personal();
                    personal.setIconByte(null);
                    personal.setIconUri(json.getString("figureurl_qq_2"));
                    personal.setIconThumb(null);
                    personal.setName(json.getString("nickname"));
                    personal.setDesc(json.getString("msg"));
                    personal.setGender(DBInfo.Personal.GENDER_NONE);
                    personal.setLocal(json.getString("province") + "," + json.getString("city"));
                    personal.setBirthYear(1990);
                    personal.setBirthMonth(1);
                    personal.setBirthDay(1);
                    personal.setBirthType(DBInfo.Personal.BIRTH_TYPE_GREGORIAN);

                    mDBManager.updatePersonal(personal);

                    // activity.getDrawerView().loadPersonData(personal);
                    // activity.getDrawerView().updateLoginStatus(true);

                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            try {

                                final byte[] data =
                                        BaseUtil.getImageData(mContext,
                                                json.getString("figureurl_qq_2"));
                                if (data != null && data.length > 0) {
                                    ContentValues values = new ContentValues();
                                    values.put(DBInfo.Personal.ICON_BYTE, data);
                                    mDBManager.updatePersonal(values);

                                }

                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                        }
                    }).start();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(UiError arg0) {
                // TODO Auto-generated method stub
                mTencent.logout(mContext);
            }

        });
    }

    public void load(String openid, String accessToken, long expiresIn) {
        // TODO Auto-generated method stub
        mTencent.setOpenId(openid);
        mTencent.setAccessToken(accessToken, String.valueOf(expiresIn));
    }

}
