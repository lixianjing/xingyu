package com.xian.xingyu.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class XianDataBaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "XianDataBaseHelper";
    public static final String DATABASE_NAME = "xian.db";
    private static final int DATABASE_VERSION = 1;

    private final Context mContext;

    private static XianDataBaseHelper instance;

    private synchronized static void createInstance(Context context) {
        if (instance == null) {
            instance = new XianDataBaseHelper(context);
        }

    }

    public static XianDataBaseHelper getInstance(Context context) {
        if (instance == null) {
            createInstance(context);
        }
        return instance;
    }

    private XianDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        createTables(db);
        initData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        switch (oldVersion) {
            case 0:
                if (newVersion <= 0) {
                    return;
                }

                db.beginTransaction();
                try {
                    upgradeDatabaseToVersion1(db);
                    db.setTransactionSuccessful();
                } catch (Throwable ex) {
                    Log.e(TAG, ex.getMessage(), ex);
                    break;
                } finally {
                    db.endTransaction();
                }
        }

        Log.e(TAG, "Destroying all old data.");
        dropAll(db);
        onCreate(db);

    }

    private void dropAll(SQLiteDatabase db) {

        db.execSQL("DROP TABLE IF EXISTS " + DBInfo.Personal.TABLE);

    }

    private void createTables(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + DBInfo.Personal.TABLE + " (" + DBInfo.Personal._ID
                + " INTEGER PRIMARY KEY," + DBInfo.Personal.ICON + " BLOB,"
                + DBInfo.Personal.ICON_URI + " TEXT," + DBInfo.Personal.ICON_THUMB + " BLOB,"
                + DBInfo.Personal.ICON_THUMB_URI + " TEXT," + DBInfo.Personal.NAME + " TEXT,"
                + DBInfo.Personal.DESC + " TEXT," + DBInfo.Personal.GENDER + " INTEGER DEFAULT 0,"
                + DBInfo.Personal.LOCAL + " TEXT," + DBInfo.Personal.BIRTH_YEAR + " INTEGER,"
                + DBInfo.Personal.BIRTH_MONTH + " INTEGER," + DBInfo.Personal.BIRTH_DAY
                + " INTEGER," + DBInfo.Personal.BIRTH_TYPE + " INTEGER DEFAULT 0);");

        db.execSQL("CREATE TABLE " + DBInfo.Emotion.TABLE + " (" + DBInfo.Emotion._ID
                + " INTEGER PRIMARY KEY," + DBInfo.Emotion.SUBJECT + " TEXT,"
                + DBInfo.Emotion.CONTENT + " TEXT," + DBInfo.Emotion.STAMP + " INTEGER,"
                + DBInfo.Emotion.LOCAL + " TEXT," + DBInfo.Emotion.LOCAL_GPS + " TEXT,"
                + DBInfo.Emotion.TYPE + " INTEGER DEFAULT 0," + DBInfo.Emotion.STATUS
                + " INTEGER DEFAULT 0," + DBInfo.Emotion.HAS_PIC + " INTEGER DEFAULT 0,"
                + DBInfo.Emotion.COMMENT_COUNT + " INTEGER DEFAULT 0," + DBInfo.Emotion.FAV_COUNT
                + " INTEGER DEFAULT 0," + DBInfo.Emotion.USER_TOKEN + " TEXT);");

        db.execSQL("CREATE TABLE " + DBInfo.FileData.TABLE + " (" + DBInfo.FileData._ID
                + " INTEGER PRIMARY KEY," + DBInfo.FileData.FILE_TYPE + " INTEGER,"
                + DBInfo.FileData.FILE_ID + " INTEGER," + DBInfo.FileData.MIME + " TEXT,"
                + DBInfo.FileData.URI + " TEXT," + DBInfo.FileData.THUMB_URI + " TEXT,"
                + DBInfo.FileData.SIZE + " INTEGER DEFAULT 0," + DBInfo.FileData.POS
                + " INTEGER DEFAULT 0," + DBInfo.FileData.TYPE + " INTEGER DEFAULT 0,"
                + DBInfo.FileData.STATUS + " INTEGER DEFAULT 0," + DBInfo.FileData.SEQ + " TEXT );");


        db.execSQL("CREATE TABLE " + DBInfo.PublicShow.TABLE + " (" + DBInfo.PublicShow._ID
                + " INTEGER PRIMARY KEY," + DBInfo.PublicShow.CONTENT + " TEXT,"
                + DBInfo.PublicShow.STAMP + " INTEGER," + DBInfo.PublicShow.TYPE + " INTEGER,"
                + DBInfo.PublicShow.HAS_PIC + " INTEGER," + DBInfo.PublicShow.COMMENT_COUNT
                + " INTEGER DEFAULT 0," + DBInfo.PublicShow.FAV_COUNT + " INTEGER DEFAULT 0,"
                + DBInfo.PublicShow.PIC_URI + " TEXT," + DBInfo.PublicShow.TOKEN + " TEXT);");



    }

    private void initData(SQLiteDatabase db) {

        db.execSQL("INSERT INTO " + DBInfo.Personal.TABLE + "(" + DBInfo.Personal.NAME
                + ") values('')");

    }

    private void upgradeDatabaseToVersion1(SQLiteDatabase db) {

    }

}
