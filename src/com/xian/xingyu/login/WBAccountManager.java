package com.xian.xingyu.login;

import java.text.SimpleDateFormat;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuth;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.AsyncWeiboRunner;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.net.WeiboParameters;
import com.sina.weibo.sdk.openapi.LogoutAPI;
import com.sina.weibo.sdk.openapi.UsersAPI;
import com.sina.weibo.sdk.openapi.models.User;
import com.sina.weibo.sdk.utils.LogUtil;
import com.xian.xingyu.activity.MainActivity;
import com.xian.xingyu.bean.PersonInfo;
import com.xian.xingyu.db.DBInfo;
import com.xian.xingyu.db.DBManager;
import com.xian.xingyu.util.BaseUtil;
import com.xian.xingyu.util.Configs;

public class WBAccountManager implements IAccountManager {

    private static final String TAG = "WBAccountManager";

    /** 当前 DEMO 应用的 APP_KEY，第三方应用应该使用自己的 APP_KEY 替换该 APP_KEY */
    public static final String APP_KEY = "2045436852";

    /**
     * 当前 DEMO 应用的回调页，第三方应用可以使用自己的回调页。
     * <p>
     * 注：关于授权回调页对移动客户端应用来说对用户是不可见的，所以定义为何种形式都将不影响， 但是没有定义将无法使用 SDK 认证登录。
     * 建议使用默认回调页：https://api.weibo.com/oauth2/default.html
     * </p>
     */
    public static final String REDIRECT_URL = "http://www.sina.com";

    /**
     * Scope 是 OAuth2.0 授权机制中 authorize 接口的一个参数。通过 Scope，平台将开放更多的微博
     * 核心功能给开发者，同时也加强用户隐私保护，提升了用户体验，用户在新 OAuth2.0 授权页中有权利 选择赋予应用的功能。
     * 我们通过新浪微博开放平台-->管理中心-->我的应用-->接口管理处，能看到我们目前已有哪些接口的 使用权限，高级权限需要进行申请。 目前 Scope 支持传入多个 Scope
     * 权限，用逗号分隔。 有关哪些 OpenAPI 需要权限申请，请查看：http://open.weibo.com/wiki/%E5%BE%AE%E5%8D%9AAPI 关于 Scope
     * 概念及注意事项，请查看：http://open.weibo.com/wiki/Scope
     */
    public static final String SCOPE = "email,direct_messages_read,direct_messages_write,"
            + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
            + "follow_app_official_microblog," + "invitation_write";
    /**
     * WeiboSDKDemo 程序的 APP_SECRET。 请注意：请务必妥善保管好自己的 APP_SECRET，不要直接暴露在程序中，此处仅作为一个DEMO来演示。
     */
    private static final String WEIBO_DEMO_APP_SECRET = "4e47e691a516afad0fc490e05ff70ee5";

    /** 通过 code 获取 Token 的 URL */
    private static final String OAUTH2_ACCESS_TOKEN_URL =
            "https://open.weibo.cn/oauth2/access_token";

    /** 微博 Web 授权接口类，提供登陆等功能 */
    private WeiboAuth mWeiboAuth;
    private Oauth2AccessToken mAccessToken;

    private Context mContext;

    private DBManager mDBManager;
    private Configs mConfigs;
    private Handler mHandler;

    private static WBAccountManager instance;

    private synchronized static void createInstance(Context context) {
        if (instance == null) {
            instance = new WBAccountManager(context);
        }

    }

    public static WBAccountManager getInstance(Context context) {
        if (instance == null) {
            createInstance(context);
        }
        return instance;
    }

    private WBAccountManager(Context context) {
        super();
        this.mContext = context;
        mDBManager = DBManager.getInstance(context.getApplicationContext());
        mConfigs = Configs.getInstance(context.getApplicationContext());
        mWeiboAuth = new WeiboAuth(context, APP_KEY, REDIRECT_URL, SCOPE);
    }

    @Override
    public boolean isLogin() {
        // TODO Auto-generated method stub
        if (mAccessToken != null && mAccessToken.isSessionValid()) {
            return true;
        }
        return false;
    }

    @Override
    public void login(Activity activity) {
        // TODO Auto-generated method stub
        mWeiboAuth.authorize(new WeiboAuthListener() {

            @Override
            public void onCancel() {
                // TODO Auto-generated method stub
                Log.e("lmf", ">>>>>>>login>>>>>>onCancel>>>>>>>>>>>>");
                mHandler.sendEmptyMessage(MainActivity.MSG_LOGIN_CANCEL);
            }

            @Override
            public void onComplete(Bundle values) {
                // TODO Auto-generated method stub

                if (values != null) {
                    String code = values.getString("code");
                    if (!TextUtils.isEmpty(code)) {
                        Log.e("lmf",
                                ">>>>>>>>>>>>>weibosdk_demo_toast_obtain_code_success>>>>>>>>>>>>"
                                        + code);
                        fetchTokenAsync(code, WEIBO_DEMO_APP_SECRET);
                        return;
                    }
                }
                mHandler.sendEmptyMessage(MainActivity.MSG_LOGIN_ERROR);

            }

            @Override
            public void onWeiboException(WeiboException arg0) {
                // TODO Auto-generated method stub
                Log.e("lmf", ">>>>>>>>>>>>>login error >>>>>>>>>>>>" + arg0.getMessage());
                mHandler.sendEmptyMessage(MainActivity.MSG_LOGIN_ERROR);
            }

        }, WeiboAuth.OBTAIN_AUTH_CODE);
    }

    @Override
    public void logout() {
        // TODO Auto-generated method stub
        LogoutAPI logout = new LogoutAPI(mAccessToken);
        logout.logout(new RequestListener() {

            @Override
            public void onWeiboException(WeiboException arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onComplete(String arg0) {
                // TODO Auto-generated method stub

            }
        });

        BaseUtil.cleanLoginConfig(mConfigs);
        mDBManager.updatePersonal(new PersonInfo());
    }

    @Override
    public void loadAccount(String uid, String accessToken, String expiresIn) {
        // TODO Auto-generated method stub
        mAccessToken = new Oauth2AccessToken(accessToken, expiresIn);
        mAccessToken.setUid(uid);

    }

    @Override
    public void getPersonalInfo() {
        // TODO Auto-generated method stub
        UsersAPI mUsersAPI = new UsersAPI(mAccessToken);

        mUsersAPI.show(Long.parseLong(mAccessToken.getUid()), new RequestListener() {
            @Override
            public void onComplete(String response) {
                if (!TextUtils.isEmpty(response)) {
                    // 调用 User#parse 将JSON串解析成User对象
                    User user = User.parse(response);
                    Log.e("lmf", ">>>>>>>>>response>>>>>>>>>>>>" + response);
                    if (user != null) {

                        Log.e("lmf", ">>>>>>>>>user>>>>>>>>>>>>" + user);
                        PersonInfo personal = new PersonInfo();
                        personal.setIcon(null);
                        personal.setIconUri(null);
                        personal.setIconThumb(null);
                        personal.setIconThumbUri(user.avatar_large);
                        personal.setName(user.screen_name);
                        personal.setDesc(user.description);
                        personal.setGender(DBInfo.Personal.GENDER_NONE);
                        personal.setLocal(user.location);
                        personal.setBirthYear(1990);
                        personal.setBirthMonth(1);
                        personal.setBirthDay(1);
                        personal.setBirthType(DBInfo.Personal.BIRTH_TYPE_GREGORIAN);

                        mDBManager.updatePersonal(personal);

                        Message message =
                                mHandler.obtainMessage(MainActivity.MSG_LOGIN_GET_INFO_SUCCESS);
                        message.obj = personal;
                        message.sendToTarget();
                        return;
                    }
                }
                mHandler.sendEmptyMessage(MainActivity.MSG_LOGIN_GET_INFO_ERROR);
            }

            @Override
            public void onWeiboException(WeiboException e) {
                LogUtil.e(TAG, e.getMessage());
                Log.e("lmf", ">>>>>>>>>onWeiboException>>>>>>>>>>>>" + e.getMessage());
                mHandler.sendEmptyMessage(MainActivity.MSG_LOGIN_GET_INFO_ERROR);
            }
        });
    }

    @Override
    public int getType() {
        // TODO Auto-generated method stub
        return Configs.TYPE_WEIBO;
    }

    @Override
    public void setHandler(Handler hanlder) {
        this.mHandler = hanlder;
    }

    /**
     * 异步获取 Token。
     *
     * @param authCode 授权 Code，该 Code 是一次性的，只能被获取一次 Token
     * @param appSecret 应用程序的 APP_SECRET，请务必妥善保管好自己的 APP_SECRET， 不要直接暴露在程序中，此处仅作为一个DEMO来演示。
     */
    public void fetchTokenAsync(String authCode, String appSecret) {

        WeiboParameters requestParams = new WeiboParameters();
        requestParams.put(WBConstants.AUTH_PARAMS_CLIENT_ID, APP_KEY);
        requestParams.put(WBConstants.AUTH_PARAMS_CLIENT_SECRET, appSecret);
        requestParams.put(WBConstants.AUTH_PARAMS_GRANT_TYPE, "authorization_code");
        requestParams.put(WBConstants.AUTH_PARAMS_CODE, authCode);
        requestParams.put(WBConstants.AUTH_PARAMS_REDIRECT_URL, REDIRECT_URL);

        // 异步请求，获取 Token
        AsyncWeiboRunner.requestAsync(OAUTH2_ACCESS_TOKEN_URL, requestParams, "POST",
                new RequestListener() {
                    @Override
                    public void onComplete(String response) {
                        LogUtil.d(TAG, "Response: " + response);

                        // 获取 Token 成功
                        mAccessToken = Oauth2AccessToken.parseAccessToken(response);
                        if (mAccessToken != null && mAccessToken.isSessionValid()) {
                            LogUtil.d(TAG, "Success! " + mAccessToken.toString());

                            String date =
                                    new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
                                            .format(new java.util.Date(mAccessToken
                                                    .getExpiresTime()));

                            Log.e("lmf", ">>>>>>>>>>mAccessToken.getUid()>>>>>>>>>>>>"
                                    + mAccessToken.getUid());

                            Log.e("lmf", ">>>>>>>>>>mAccessToken.getToken()>>>>>>>>>>>>"
                                    + mAccessToken.getToken());
                            Log.e("lmf", ">>>>>>>>>>mAccessToken.getExpiresTime()>>>>>>>>>>>>"
                                    + mAccessToken.getExpiresTime() + ":" + date);

                            Editor editor = mConfigs.getEditor();
                            editor.putString(Configs.KEY, mAccessToken.getUid());
                            editor.putString(Configs.TOKEN, mAccessToken.getToken());
                            editor.putLong(Configs.AUTH_TIME,
                                    Long.valueOf(mAccessToken.getExpiresTime()));
                            editor.putInt(Configs.TYPE, Configs.TYPE_WEIBO);
                            editor.putInt(Configs.INFO_STATUS, Configs.INFO_STATUS_DEFAULT);
                            editor.commit();

                            mHandler.sendEmptyMessage(MainActivity.MSG_LOGIN_SUCCESS);

                        } else {
                            mAccessToken = null;
                            mHandler.sendEmptyMessage(MainActivity.MSG_LOGIN_ERROR);
                        }

                    }

                    @Override
                    public void onWeiboException(WeiboException e) {

                        Log.e("lmf",
                                ">>>>>>>>>>weibosdk_demo_toast_obtain_token_failed>>>>>>>>>>>>"
                                        + e.getMessage());
                        mHandler.sendEmptyMessage(MainActivity.MSG_LOGIN_ERROR);
                    }
                });
    }

    @Override
    public void release() {
        // TODO Auto-generated method stub
        mWeiboAuth = null;
        mAccessToken = null;
        mDBManager = null;
        mConfigs = null;
        mHandler = null;
        mContext = null;

        instance = null;
    }

}
