
package com.xian.xingyu.login;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.tencent.connect.UserInfo;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.xian.xingyu.activity.MainActivity;
import com.xian.xingyu.util.BaseUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class QQAccountManager implements IAccountManager {

    private static final String APP_ID = "222222";
    private final Context mContext;

    private final Tencent mTencent;
    private UserInfo mInfo;

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
    public void login(final Activity activity) {
        // TODO Auto-generated method stub

        mTencent.login(activity, APP_ID, new IUiListener() {

            @Override
            public void onCancel() {
                // TODO Auto-generated method stub

            }

            @Override
            public void onComplete(Object arg0) {
                // TODO Auto-generated method stub
                Log.e("lmf", ">>>>>>>>>>>>onComplete>>>>>>>" + arg0);
                if (activity instanceof MainActivity) {
                    ((MainActivity) activity).updateLoginStatus(true);
                }

                getUserInfo();

            }

            @Override
            public void onError(UiError arg0) {
                // TODO Auto-generated method stub

            }

        });
    }

    @Override
    public void logout() {
        // TODO Auto-generated method stub
        mTencent.logout(mContext);
    }

    public void getUserInfo() {
        mInfo = new UserInfo(mContext, mTencent.getQQToken());
        mInfo.getUserInfo(new IUiListener() {

            @Override
            public void onCancel() {
                // TODO Auto-generated method stub

            }

            @Override
            public void onComplete(Object arg0) {
                // TODO Auto-generated method stub
                Log.e("lmf", ">>getUserInfo>>>>>arg0>>>>" + arg0);
                final JSONObject json = (JSONObject) arg0;
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            BaseUtil.saveFileToData(mContext, "figureurl_qq_1",
                                    json.getString("figureurl_qq_1"));
                            BaseUtil.saveFileToData(mContext, "figureurl_qq_2",
                                    json.getString("figureurl_qq_2"));
                            BaseUtil.saveFileToData(mContext, "figureurl_1",
                                    json.getString("figureurl_1"));
                            BaseUtil.saveFileToData(mContext, "figureurl_2",
                                    json.getString("figureurl_2"));
                            BaseUtil.saveFileToData(mContext, "figureurl",
                                    json.getString("figureurl"));
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }).start();

            }

            @Override
            public void onError(UiError arg0) {
                // TODO Auto-generated method stub

            }

        });
    }

    public void load(String openid, String accessToken, long expiresIn) {
        // TODO Auto-generated method stub
        mTencent.setOpenId(openid);
        mTencent.setAccessToken(accessToken, String.valueOf(expiresIn));
    }

}
