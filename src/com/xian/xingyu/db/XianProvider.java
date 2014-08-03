
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

    private final static String TAG = "EucProvider";
    private final static boolean DEBUG = false;

    private static final int ALL = 0;
    private static final int ACCOUNT_FILTER = 1;
    private static final int PERSONAL_FILTER = 2;

    private static final UriMatcher sURLMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURLMatcher.addURI(DBInfo.AUTHORITY, null, ALL);

        sURLMatcher.addURI(DBInfo.AUTHORITY, DBInfo.Account.TABLE, ACCOUNT_FILTER);
        sURLMatcher.addURI(DBInfo.AUTHORITY, DBInfo.Personal.TABLE, PERSONAL_FILTER);

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
            case ACCOUNT_FILTER:
                qb.setTables(DBInfo.Account.TABLE);
                break;
            case PERSONAL_FILTER:
                qb.setTables(DBInfo.Personal.TABLE);
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
            case ACCOUNT_FILTER:
                tableName = DBInfo.Account.TABLE;
                break;
            case PERSONAL_FILTER:
                tableName = DBInfo.Personal.TABLE;
                break;

            default:
                return null;
        }

        if (tableName != null) {
            SQLiteDatabase db = mOpenHelper.getWritableDatabase();
            long id = db.insert(tableName, null, values);
            Uri res = Uri.withAppendedPath(uri, String.valueOf(id));
            if (res != null)
                notifyChange(res);
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
            case ACCOUNT_FILTER:
                tableName = DBInfo.Account.TABLE;
                break;
            case PERSONAL_FILTER:
                tableName = DBInfo.Personal.TABLE;
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
            case ACCOUNT_FILTER:
                tableName = DBInfo.Account.TABLE;
                break;
            case PERSONAL_FILTER:
                tableName = DBInfo.Personal.TABLE;
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
