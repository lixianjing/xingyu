
package com.xian.xingyu.db;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.xian.xingyu.bean.Account;
import com.xian.xingyu.bean.Personal;

public class DBManager {

    private static final String TAG = "DBManager";

    private final Context mContext;
    private final ContentResolver mCr;

    private static DBManager instance;

    private synchronized static void createInstance(Context context) {
        if (instance == null) {
            instance = new DBManager(context);
        }

    }

    public static DBManager getInstance(Context context) {
        if (instance == null) {
            createInstance(context);
        }
        return instance;
    }

    private DBManager(Context context) {
        super();
        mContext = context;
        mCr = mContext.getContentResolver();
    }

    public Uri insertAccount() {
        return null;
    }

    public Account getAccount(long id) {
        return null;
    }

    public int deleteAccount(long id) {
        return 0;
    }

    public int updateAccount(long id) {
        return 0;
    }

    public Account getCurrentAccount() {
        Cursor cursor = null;
        try {
            cursor = mCr.query(DBInfo.Account.CONTENT_URI, DBInfo.Account.COLUMNS,
                    DBInfo.Account.STATUS + " = " + DBInfo.Account.STATUS_USED,
                    null,
                    null);
            if (cursor.moveToFirst()) {
                Account account = new Account();

                account.setId(cursor.getLong(DBInfo.Account.INDEX_ID));
                account.setKey(cursor.getString(DBInfo.Account.INDEX_KEY));
                account.setToken(cursor.getString(DBInfo.Account.INDEX_TOKEN));
                account.setAuthTime(cursor.getLong(DBInfo.Account.INDEX_AUTH_TIME));
                account.setType(cursor.getInt(DBInfo.Account.INDEX_TYPE));
                account.setStatus(cursor.getInt(DBInfo.Account.INDEX_STATUS));
                account.setInfoStatus(cursor.getInt(DBInfo.Account.INDEX_INFO_STATUS));

                return account;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return null;
    }

    public Personal getPersonalByAccountId(long id) {
        Cursor cursor = null;
        try {
            cursor = mCr.query(DBInfo.Personal.CONTENT_URI, DBInfo.Personal.COLUMNS,
                    DBInfo.Personal.ACCOUNT_ID + " = " + id,
                    null,
                    null);
            if (cursor.moveToFirst()) {
                Personal personal = new Personal();

                personal.setId(cursor.getLong(DBInfo.Personal.INDEX_ID));
                personal.setAccountId(cursor.getLong(DBInfo.Personal.INDEX_ACCOUNT_ID));
                personal.setIconByte(cursor.getBlob(DBInfo.Personal.INDEX_ICON_BYTE));
                personal.setIconUri(cursor.getString(DBInfo.Personal.INDEX_ICON_URI));
                personal.setIconThumb(cursor.getBlob(DBInfo.Personal.INDEX_ICON_THUMB));
                personal.setName(cursor.getString(DBInfo.Personal.INDEX_NAME));
                personal.setDesc(cursor.getString(DBInfo.Personal.INDEX_DESC));
                personal.setGender(cursor.getInt(DBInfo.Personal.INDEX_GENDER));
                personal.setLocal(cursor.getString(DBInfo.Personal.INDEX_LOCAL));
                personal.setBirthYear(cursor.getInt(DBInfo.Personal.INDEX_BIRTH_YEAR));
                personal.setBirthMonth(cursor.getInt(DBInfo.Personal.INDEX_BIRTH_MONTH));
                personal.setBirthDay(cursor.getInt(DBInfo.Personal.INDEX_BIRTH_DAY));
                personal.setBirthType(cursor.getInt(DBInfo.Personal.INDEX_BIRTH_TYPE));

                return personal;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return null;
    }

}
