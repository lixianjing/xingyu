/*
 * Copyright (C) 2010~2014 limingfeng
 *
 * This file is a part of limingfeng apps.
 *
 * All rights reserved.
 */

package com.xian.xingyu.bean;


import android.content.Context;
import android.database.Cursor;

import com.xian.xingyu.db.DBInfo;

public class PublicItem {

    final Context mContext;

    public long id;
    public String subject;
    public String content;
    public long stamp;
    public int type;
    public boolean hasPic;
    public int commentCount;
    public int favCount;
    public String picUri;
    public String token;
    public String userName;
    public String userIcon;
    public String userToken;


    Cursor mCursor;



    public PublicItem(Context context, Cursor cursor) {

        mContext = context;
        mCursor = cursor;


        id = cursor.getLong(DBInfo.PublicShow.INDEX_ID);
        subject = cursor.getString(DBInfo.PublicShow.INDEX_SUBJECT);
        content = cursor.getString(DBInfo.PublicShow.INDEX_CONTENT);
        stamp = cursor.getLong(DBInfo.PublicShow.INDEX_STAMP);
        type = cursor.getInt(DBInfo.PublicShow.INDEX_TYPE);
        hasPic =
                cursor.getInt(DBInfo.PublicShow.INDEX_HAS_PIC) == DBInfo.PublicShow.HAS_PIC_IS
                        ? true
                        : false;
        commentCount = cursor.getInt(DBInfo.PublicShow.INDEX_COMMENT_COUNT);
        favCount = cursor.getInt(DBInfo.PublicShow.INDEX_FAV_COUNT);
        picUri = cursor.getString(DBInfo.PublicShow.INDEX_PIC_URI);
        token = cursor.getString(DBInfo.PublicShow.INDEX_TOKEN);
        userName = cursor.getString(DBInfo.PublicShow.INDEX_USER_NAME);
        userIcon = cursor.getString(DBInfo.PublicShow.INDEX_USER_ICON);
        userToken = cursor.getString(DBInfo.PublicShow.INDEX_USER_TOKEN);



    }
}
