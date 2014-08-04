package com.xian.xingyu.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Configs {

    private static final String TAG = "Configs";
    private static final String FILE = "xian";

    private final Context mContext;
    private SharedPreferences mSharedPreferences;
    private Editor mEditor;

    private static Configs instance;

    private synchronized static void createInstance(Context context) {
        if (instance == null) {
            instance = new Configs(context);
        }

    }

    public static Configs getInstance(Context context) {
        if (instance == null) {
            createInstance(context);
        }
        return instance;
    }

    private Configs(Context context) {
        super();
        mContext = context;
        mSharedPreferences = context.getSharedPreferences(FILE, Context.MODE_PRIVATE); // 私有数据
        mEditor = mSharedPreferences.edit();// 获取编辑器
    }

    public SharedPreferences getSharedPreferences() {
        return mSharedPreferences;
    }

    public void setSharedPreferences(SharedPreferences mSharedPreferences) {
        this.mSharedPreferences = mSharedPreferences;
    }

    public Editor getEditor() {
        return mEditor;
    }

    public void setEditor(Editor mEditor) {
        this.mEditor = mEditor;
    }



    public static final String KEY = "key";
    public static final String TOKEN = "token";
    public static final String AUTH_TIME = "auth_time";
    public static final String TYPE = "type";
    public static final String INFO_STATUS = "info_status";

    public static final int TYPE_DEFAULT = 0;
    public static final int TYPE_QQ = 1;
    public static final int TYPE_weibo = 2;

    public static final int INFO_STATUS_DEFAULT = 0;
    public static final int INFO_STATUS_CUSTOM = 1;


}
