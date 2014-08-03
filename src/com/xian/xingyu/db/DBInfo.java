
package com.xian.xingyu.db;

import android.net.Uri;
import android.provider.BaseColumns;

public class DBInfo {
    public static final String AUTHORITY = "com.xian.xingyu.provider";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final class Account implements BaseColumns {

        public static final String TABLE = "account";

        public static final String KEY = "key";
        public static final String TOKEN = "token";
        public static final String AUTH_TIME = "auth_time";
        public static final String TYPE = "type";
        public static final String STATUS = "status";
        public static final String INFO_STATUS = "info_status";

        public static final int TYPE_DEFAULT = 0;
        public static final int TYPE_QQ = 1;
        public static final int TYPE_weibo = 2;

        public static final int STATUS_FREE = 0;
        public static final int STATUS_USED = 1;

        public static final int INFO_STATUS_DEFAULT = 0;
        public static final int INFO_STATUS_CUSTOM = 1;

        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/"
                + TABLE);

        public static final String[] COLUMNS = new String[] {
                _ID, KEY, TOKEN, AUTH_TIME, TYPE, STATUS, INFO_STATUS
        };

        public static final int INDEX_ID = 0;
        public static final int INDEX_KEY = 1;
        public static final int INDEX_TOKEN = 2;
        public static final int INDEX_AUTH_TIME = 3;
        public static final int INDEX_TYPE = 4;
        public static final int INDEX_STATUS = 5;
        public static final int INDEX_INFO_STATUS = 6;

    }

    public static final class Personal implements BaseColumns {

        public static final String TABLE = "personal";

        public static final String ACCOUNT_ID = "account_id";
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

        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/"
                + TABLE);

        public static final String[] COLUMNS = new String[] {
                _ID, ACCOUNT_ID, ICON_BYTE, ICON_URI, ICON_THUMB, NAME, DESC, GENDER, LOCAL,
                BIRTH_YEAR, BIRTH_MONTH, BIRTH_DAY,
                BIRTH_TYPE
        };

        public static final int INDEX_ID = 0;
        public static final int INDEX_ACCOUNT_ID = 1;
        public static final int INDEX_ICON_BYTE = 2;
        public static final int INDEX_ICON_URI = 3;
        public static final int INDEX_ICON_THUMB = 4;
        public static final int INDEX_NAME = 5;
        public static final int INDEX_DESC = 6;
        public static final int INDEX_GENDER = 7;
        public static final int INDEX_LOCAL = 8;
        public static final int INDEX_BIRTH_YEAR = 9;
        public static final int INDEX_BIRTH_MONTH = 10;
        public static final int INDEX_BIRTH_DAY = 11;
        public static final int INDEX_BIRTH_TYPE = 12;

    }

}
