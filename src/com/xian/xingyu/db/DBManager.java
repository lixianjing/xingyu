
package com.xian.xingyu.db;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.xian.xingyu.bean.EmotionInfo;
import com.xian.xingyu.bean.FileDataInfo;
import com.xian.xingyu.bean.PersonInfo;
import com.xian.xingyu.db.DBInfo.Emotion;
import com.xian.xingyu.db.DBInfo.FileData;

import java.util.ArrayList;
import java.util.List;

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

    public boolean updatePersonal(PersonInfo personal) {
        ContentValues values = new ContentValues();

        values.put(DBInfo.Personal.ICON, personal.getIcon());
        values.put(DBInfo.Personal.ICON_URI, personal.getIconUri());
        values.put(DBInfo.Personal.ICON_THUMB, personal.getIconThumb());
        values.put(DBInfo.Personal.ICON_THUMB_URI, personal.getIconThumbUri());
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

    public PersonInfo getPersonal() {
        Cursor cursor = null;
        try {
            cursor =
                    mCr.query(DBInfo.Personal.CONTENT_URI, DBInfo.Personal.COLUMNS, null, null,
                            null);
            if (cursor.moveToFirst()) {
                PersonInfo personal = new PersonInfo();

                personal.setId(cursor.getLong(DBInfo.Personal.INDEX_ID));
                personal.setIcon(cursor.getBlob(DBInfo.Personal.INDEX_ICON));
                personal.setIconUri(cursor.getString(DBInfo.Personal.INDEX_ICON_URI));
                personal.setIconThumb(cursor.getBlob(DBInfo.Personal.INDEX_ICON_THUMB));
                personal.setIconThumbUri(cursor.getString(DBInfo.Personal.INDEX_ICON_THUMB_URI));
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

    public Uri insertEmotion(EmotionInfo info) {
        ContentValues values = new ContentValues();

        values.put(Emotion.SUBJECT, info.getSubject());
        values.put(Emotion.CONTENT, info.getContent());
        values.put(Emotion.STAMP, info.getStamp());
        values.put(Emotion.LOCAL, info.getLocal());
        values.put(Emotion.LOCAL_GPS, info.getLocalGps());
        values.put(Emotion.TYPE, info.getType());
        values.put(Emotion.STATUS, info.getStatus());
        values.put(Emotion.HAS_PIC, info.isHasPic() ? Emotion.HAS_PIC_IS : 0);
        values.put(Emotion.COMMENT_COUNT, info.getCommentCount());
        values.put(Emotion.FAV_COUNT, info.getFavCount());
        values.put(Emotion.USER_TOKEN, info.getUserToken());

        return mCr.insert(Emotion.CONTENT_URI, values);

    }

    // public Uri deleteEmotion(long id) {
    // ContentValues values = new ContentValues();
    //
    // values.put(Emotion.SUBJECT, info.getSubject());
    // values.put(Emotion.CONTENT, info.getContent());
    // values.put(Emotion.STAMP, info.getStamp());
    // values.put(Emotion.LOCAL, info.getLocal());
    // values.put(Emotion.LOCAL_GPS, info.getLocalGps());
    // values.put(Emotion.TYPE, info.getType());
    // values.put(Emotion.STATUS, info.getStatus());
    // values.put(Emotion.HAS_PIC, info.isHasPic() ? Emotion.HAS_PIC_IS : 0);
    // values.put(Emotion.COMMENT_COUNT, info.getCommentCount());
    // values.put(Emotion.FAV_COUNT, info.getFavCount());
    // values.put(Emotion.USER_TOKEN, info.getUserToken());
    //
    // return mCr.insert(Emotion.CONTENT_URI, values);
    //
    // }

    // public Uri updateEmotion(long id) {
    // ContentValues values = new ContentValues();
    //
    // values.put(Emotion.SUBJECT, info.getSubject());
    // values.put(Emotion.CONTENT, info.getContent());
    // values.put(Emotion.STAMP, info.getStamp());
    // values.put(Emotion.LOCAL, info.getLocal());
    // values.put(Emotion.LOCAL_GPS, info.getLocalGps());
    // values.put(Emotion.TYPE, info.getType());
    // values.put(Emotion.STATUS, info.getStatus());
    // values.put(Emotion.HAS_PIC, info.isHasPic() ? Emotion.HAS_PIC_IS : 0);
    // values.put(Emotion.COMMENT_COUNT, info.getCommentCount());
    // values.put(Emotion.FAV_COUNT, info.getFavCount());
    // values.put(Emotion.USER_TOKEN, info.getUserToken());
    //
    // return mCr.insert(Emotion.CONTENT_URI, values);
    //
    // }
    //
    public List<EmotionInfo> queryEmotion(int count) {

        Cursor cursor = null;
        try {
            List<EmotionInfo> list = new ArrayList<EmotionInfo>();
            cursor =
                    mCr.query(Emotion.CONTENT_URI, Emotion.COLUMNS, null,
                            null, Emotion.STAMP + " DESC ");
            while (cursor.moveToNext()) {
                EmotionInfo info = new EmotionInfo();

                info.setId(cursor.getLong(Emotion.INDEX_ID));
                info.setSubject(cursor.getLong(Emotion.INDEX_SUBJECT));
                info.setContent(cursor.getLong(Emotion.INDEX_CONTENT));
                info.setStamp(cursor.getLong(Emotion.INDEX_STAMP));
                info.setId(cursor.getLong(Emotion.INDEX_LOCAL));
                info.setId(cursor.getLong(Emotion.INDEX_LOCAL_GPS));
                info.setId(cursor.getLong(Emotion.INDEX_TYPE));
                info.setId(cursor.getLong(Emotion.INDEX_STATUS));
                info.setId(cursor.getLong(Emotion.INDEX_HAS_PIC));
                info.setId(cursor.getLong(Emotion.INDEX_COMMENT_COUNT));
                info.setId(cursor.getLong(Emotion.INDEX_FAV_COUNT));
                info.setId(cursor.getLong(Emotion.INDEX_USER_TOKEN));

                private long id;
                private String subject;
                private String content;
                private long stamp;
                private String local;
                private String localGps;
                private int type;
                private int status;
                private boolean hasPic;
                private int commentCount;
                private int favCount;
                private String userToken;
                private List<FileDataInfo> fileDateList;

                list.add(info);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (cursor != null)
                cursor.close();
        }


    }
    //
    // public EmotionInfo getEmotion(long id) {
    // ContentValues values = new ContentValues();
    //
    // values.put(Emotion.SUBJECT, info.getSubject());
    // values.put(Emotion.CONTENT, info.getContent());
    // values.put(Emotion.STAMP, info.getStamp());
    // values.put(Emotion.LOCAL, info.getLocal());
    // values.put(Emotion.LOCAL_GPS, info.getLocalGps());
    // values.put(Emotion.TYPE, info.getType());
    // values.put(Emotion.STATUS, info.getStatus());
    // values.put(Emotion.HAS_PIC, info.isHasPic() ? Emotion.HAS_PIC_IS : 0);
    // values.put(Emotion.COMMENT_COUNT, info.getCommentCount());
    // values.put(Emotion.FAV_COUNT, info.getFavCount());
    // values.put(Emotion.USER_TOKEN, info.getUserToken());
    //
    // return mCr.insert(Emotion.CONTENT_URI, values);
    //
    // }

    public Uri insertFileData(FileDataInfo info) {
        ContentValues values = new ContentValues();

        values.put(FileData.FILE_TYPE, info.getFileType());
        values.put(FileData.FILE_ID, info.getFileId());
        values.put(FileData.MIME, info.getMime());
        values.put(FileData.URI, info.getUri());
        values.put(FileData.THUMB_URI, info.getThumbUri());
        values.put(FileData.SIZE, info.getSize());
        values.put(FileData.POS, info.getPos());
        values.put(FileData.TYPE, info.getType());
        values.put(FileData.STATUS, info.getStatus());
        values.put(FileData.SEQ, info.getSeq());

        return mCr.insert(FileData.CONTENT_URI, values);
    }

}
