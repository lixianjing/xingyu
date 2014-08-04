package com.xian.xingyu.db;

import android.net.Uri;
import android.provider.BaseColumns;

public class DBInfo {
    public static final String AUTHORITY = "com.xian.xingyu.provider";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);


    public static final class Personal implements BaseColumns {

        public static final String TABLE = "personal";

        public static final String ICON_BYTE = "icon_byte";
        public static final String ICON_URI = "icon_uri";
        public static final String ICON_THUMB = "icon_thumb";
        public static final String NAME = "name";
        public static final String DESC = "desc";
        public static final String GENDER = "gender";
        public static final String LOCAL = "local";
        public static final String BIRTH_YEAR = "birth_year";
        public static final String BIRTH_MONTH = "birth_month";
        public static final String BIRTH_DAY = "birth_day";
        public static final String BIRTH_TYPE = "birth_type";

        public static final int GENDER_NONE = 0;
        public static final int GENDER_MALE = 1;
        public static final int GENDER_FEMALE = 2;

        public static final int BIRTH_TYPE_GREGORIAN = 0;
        public static final int BIRTH_TYPE_LUNAR = 1;

        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE);

        public static final String[] COLUMNS = new String[] {_ID, ICON_BYTE, ICON_URI, ICON_THUMB,
                NAME, DESC, GENDER, LOCAL, BIRTH_YEAR, BIRTH_MONTH, BIRTH_DAY, BIRTH_TYPE};

        public static final int INDEX_ID = 0;
        public static final int INDEX_ICON_BYTE = 1;
        public static final int INDEX_ICON_URI = 2;
        public static final int INDEX_ICON_THUMB = 3;
        public static final int INDEX_NAME = 4;
        public static final int INDEX_DESC = 5;
        public static final int INDEX_GENDER = 6;
        public static final int INDEX_LOCAL = 7;
        public static final int INDEX_BIRTH_YEAR = 8;
        public static final int INDEX_BIRTH_MONTH = 9;
        public static final int INDEX_BIRTH_DAY = 10;
        public static final int INDEX_BIRTH_TYPE = 11;

    }

}
