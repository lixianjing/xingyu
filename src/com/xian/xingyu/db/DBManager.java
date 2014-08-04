package com.xian.xingyu.db;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

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



    public boolean updatePersonal(Personal personal) {
        ContentValues values = new ContentValues();

        values.put(DBInfo.Personal.ICON_BYTE, personal.getIconByte());
        values.put(DBInfo.Personal.ICON_URI, personal.getIconUri());
        values.put(DBInfo.Personal.ICON_THUMB, personal.getIconThumb());
        values.put(DBInfo.Personal.NAME, personal.getName());
        values.put(DBInfo.Personal.DESC, personal.getDesc());
        values.put(DBInfo.Personal.GENDER, personal.getGender());
        values.put(DBInfo.Personal.LOCAL, personal.getLocal());
        values.put(DBInfo.Personal.BIRTH_YEAR, personal.getBirthYear());
        values.put(DBInfo.Personal.BIRTH_MONTH, personal.getBirthMonth());
        values.put(DBInfo.Personal.BIRTH_DAY, personal.getBirthDay());
        values.put(DBInfo.Personal.BIRTH_TYPE, personal.getBirthType());

        return mCr.update(DBInfo.Personal.CONTENT_URI, values, DBInfo.Personal._ID + " = 1", null) > 0;
    }

    public boolean updatePersonal(ContentValues values) {

        return mCr.update(DBInfo.Personal.CONTENT_URI, values, DBInfo.Personal._ID + " = 1", null) > 0;
    }



    public Personal getPersonal() {
        Cursor cursor = null;
        try {
            cursor =
                    mCr.query(DBInfo.Personal.CONTENT_URI, DBInfo.Personal.COLUMNS, null, null,
                            null);
            if (cursor.moveToFirst()) {
                Personal personal = new Personal();

                personal.setId(cursor.getLong(DBInfo.Personal.INDEX_ID));
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
            if (cursor != null) cursor.close();
        }

        return null;
    }

}
