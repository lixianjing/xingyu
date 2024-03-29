package com.xian.xingyu.db;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class XianProvider extends ContentProvider {

    private final static String TAG = "XianProvider";
    private final static boolean DEBUG = false;

    private static final int ALL = 0;
    private static final int PERSONAL_FILTER = 1;
    private static final int EMOTION_FILTER = 2;
    private static final int FILEDATA_FILTER = 3;
    private static final int PUBLIC_SHOW_FILTER = 4;

    private static final UriMatcher sURLMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURLMatcher.addURI(DBInfo.AUTHORITY, null, ALL);

        sURLMatcher.addURI(DBInfo.AUTHORITY, DBInfo.Personal.TABLE, PERSONAL_FILTER);
        sURLMatcher.addURI(DBInfo.AUTHORITY, DBInfo.Emotion.TABLE, EMOTION_FILTER);
        sURLMatcher.addURI(DBInfo.AUTHORITY, DBInfo.FileData.TABLE, FILEDATA_FILTER);
        sURLMatcher.addURI(DBInfo.AUTHORITY, DBInfo.PublicShow.TABLE, PUBLIC_SHOW_FILTER);

    }

    private SQLiteOpenHelper mOpenHelper;

    @Override
    public boolean onCreate() {
        mOpenHelper = XianDataBaseHelper.getInstance(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
            String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        int match = sURLMatcher.match(uri);
        switch (match) {
            case ALL:
                return null;
            case PERSONAL_FILTER:
                qb.setTables(DBInfo.Personal.TABLE);
                break;
            case EMOTION_FILTER:
                qb.setTables(DBInfo.Emotion.TABLE);
                break;
            case FILEDATA_FILTER:
                qb.setTables(DBInfo.FileData.TABLE);
                break;
            case PUBLIC_SHOW_FILTER:
                qb.setTables(DBInfo.PublicShow.TABLE);
                break;

            default:

                return null;
        }
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Cursor ret = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        return ret;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        String tableName = null;

        int match = sURLMatcher.match(uri);
        switch (match) {
            case ALL:
                return null;
            case PERSONAL_FILTER:
                return null;
            case EMOTION_FILTER:
                tableName = DBInfo.Emotion.TABLE;
                break;
            case FILEDATA_FILTER:
                tableName = DBInfo.FileData.TABLE;
                break;
            case PUBLIC_SHOW_FILTER:
                tableName = DBInfo.PublicShow.TABLE;
                break;
            default:
                return null;
        }

        if (tableName != null) {
            SQLiteDatabase db = mOpenHelper.getWritableDatabase();
            long id = db.insert(tableName, null, values);
            Uri res = Uri.withAppendedPath(uri, String.valueOf(id));
            if (res != null) notifyChange(res);
            return res;
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        String tableName = null;
        int count = 0;
        int match = sURLMatcher.match(uri);
        switch (match) {
            case ALL:
                return 0;
            case PERSONAL_FILTER:
                return 0;
            case EMOTION_FILTER:
                tableName = DBInfo.Emotion.TABLE;
                break;
            case FILEDATA_FILTER:
                tableName = DBInfo.FileData.TABLE;
                break;
            case PUBLIC_SHOW_FILTER:
                tableName = DBInfo.PublicShow.TABLE;
                break;
            default:
                return 0;
        }
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        if (tableName != null) {
            count = db.delete(tableName, selection, selectionArgs);
        }
        if (count > 0) {
            notifyChange(uri);
        }
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        String tableName = null;
        int count = 0;

        int match = sURLMatcher.match(uri);
        switch (match) {
            case ALL:
                return 0;
            case PERSONAL_FILTER:
                tableName = DBInfo.Personal.TABLE;
                break;
            case EMOTION_FILTER:
                tableName = DBInfo.Emotion.TABLE;
                break;
            case FILEDATA_FILTER:
                tableName = DBInfo.FileData.TABLE;
                break;
            case PUBLIC_SHOW_FILTER:
                tableName = DBInfo.PublicShow.TABLE;
                break;
            default:
                return 0;
        }
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        if (tableName != null) {
            count = db.update(tableName, values, selection, selectionArgs);
        }
        if (count > 0) {
            notifyChange(uri);
        }
        return count;
    }

    private void notifyChange(Uri uri) {
        ContentResolver cr = getContext().getContentResolver();
        cr.notifyChange(uri, null);
    }
}
