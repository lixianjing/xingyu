package com.xian.xingyu.db;

import android.net.Uri;
import android.provider.BaseColumns;

public class DBInfo {
    public static final String AUTHORITY = "com.xian.xingyu.provider";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final class Personal implements BaseColumns {

        public static final String TABLE = "personal";

        public static final String ICON = "icon";
        public static final String ICON_URI = "icon_uri";
        public static final String ICON_THUMB = "icon_thumb";
        public static final String ICON_THUMB_URI = "icon_thumb_uri";
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

        public static final String[] COLUMNS = new String[] {_ID, ICON, ICON_URI, ICON_THUMB,
                ICON_THUMB_URI, NAME, DESC, GENDER, LOCAL, BIRTH_YEAR, BIRTH_MONTH, BIRTH_DAY,
                BIRTH_TYPE};

        public static final int INDEX_ID = 0;
        public static final int INDEX_ICON = 1;
        public static final int INDEX_ICON_URI = 2;
        public static final int INDEX_ICON_THUMB = 3;
        public static final int INDEX_ICON_THUMB_URI = 4;
        public static final int INDEX_NAME = 5;
        public static final int INDEX_DESC = 6;
        public static final int INDEX_GENDER = 7;
        public static final int INDEX_LOCAL = 8;
        public static final int INDEX_BIRTH_YEAR = 9;
        public static final int INDEX_BIRTH_MONTH = 10;
        public static final int INDEX_BIRTH_DAY = 11;
        public static final int INDEX_BIRTH_TYPE = 12;

    }

    public static final class Emotion implements BaseColumns {

        public static final String TABLE = "emotion";

        public static final String SUBJECT = "subject";
        public static final String CONTENT = "content";
        public static final String STAMP = "stamp";
        public static final String LOCAL = "local";
        public static final String LOCAL_GPS = "local_gps";
        // 秘密消息类型
        public static final String TYPE = "type";
        // 是否上传
        public static final String STATUS = "status";
        // 这条感情是否包含 照片
        public static final String HAS_PIC = "has_pic";
        public static final String COMMENT_COUNT = "comment_count";
        public static final String FAV_COUNT = "fav_count";

        public static final String USER_TOKEN = "user_token";

        public static final int TYPE_PRIVATE = 0;
        public static final int TYPE_PUBLIC = 1;

        public static final int STATUS_LOCAL = 0;
        public static final int STATUS_UPLOADING = 1;
        public static final int STATUS_UPLOADED = 2;
        public static final int STATUS_ERROR = 3;

        public static final int HAS_PIC_IS = 1;

        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE);

        public static final String[] COLUMNS = new String[] {_ID, SUBJECT, CONTENT, STAMP, LOCAL,
                LOCAL_GPS, TYPE, STATUS, HAS_PIC, COMMENT_COUNT, FAV_COUNT, USER_TOKEN};

        public static final int INDEX_ID = 0;
        public static final int INDEX_SUBJECT = 1;
        public static final int INDEX_CONTENT = 2;
        public static final int INDEX_STAMP = 3;
        public static final int INDEX_LOCAL = 4;
        public static final int INDEX_LOCAL_GPS = 5;
        public static final int INDEX_TYPE = 6;
        public static final int INDEX_STATUS = 7;
        public static final int INDEX_HAS_PIC = 8;
        public static final int INDEX_COMMENT_COUNT = 9;
        public static final int INDEX_FAV_COUNT = 10;
        public static final int INDEX_USER_TOKEN = 11;

    }

    public static final class FileData implements BaseColumns {

        public static final String TABLE = "fileData";
        // 来自哪的 文件
        public static final String FILE_TYPE = "file_type";
        // 对应的文件的 id
        public static final String FILE_ID = "file_id";
        public static final String MIME = "mime";
        public static final String URI = "uri";
        public static final String THUMB_URI = "thumb_uri";
        public static final String SIZE = "size";
        public static final String POS = "pos";
        // 文件类型
        public static final String TYPE = "type";
        // 下载状态
        public static final String STATUS = "status";
        public static final String SEQ = "seq";

        public static final int TYPE_IMAGE = 0;
        public static final int TYPE_VOICE = 1;

        public static final int STATUS_LOCAL = 0;
        public static final int STATUS_UPLOADING = 1;
        public static final int STATUS_UPLOADED = 2;
        public static final int STATUS_ERROR = 3;

        public static final int FILE_TYPE_EMOTION = 0;
        public static final int FILE_TYPE_SHOW = 1;

        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE);

        public static final String[] COLUMNS = new String[] {_ID, FILE_TYPE, FILE_ID, MIME, URI,
                THUMB_URI, SIZE, POS, TYPE, STATUS, SEQ};

        public static final int INDEX_ID = 0;
        public static final int INDEX_FILE_TYPE = 1;
        public static final int INDEX_FILE_ID = 2;
        public static final int INDEX_MIME = 3;
        public static final int INDEX_URI = 4;
        public static final int INDEX_THUMB_URI = 5;
        public static final int INDEX_SIZE = 6;
        public static final int INDEX_POS = 7;
        public static final int INDEX_TYPE = 8;
        public static final int INDEX_STATUS = 9;
        public static final int INDEX_SEQ = 10;

    }



    public static final class PublicShow implements BaseColumns {

        public static final String TABLE = "PublicShow";

        public static final String CONTENT = "content";
        public static final String STAMP = "stamp";
        // 秘密消息类型
        public static final String TYPE = "type";
        // 这条感情是否包含 照片
        public static final String HAS_PIC = "has_pic";
        public static final String COMMENT_COUNT = "comment_count";
        public static final String FAV_COUNT = "fav_count";
        public static final String PIC_URI = "PIC_URI";

        public static final String TOKEN = "token";
        public static final String USER_NAME = "user_name";
        public static final String USER_ICON = "user_icon";
        public static final String USER_TOKEN = "user_token";

        public static final int TYPE_PUBLIC = 0;
        public static final int TYPE_STORY = 1;


        public static final int HAS_PIC_IS = 1;

        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE);

        public static final String[] COLUMNS = new String[] {_ID, CONTENT, STAMP, TYPE, HAS_PIC,
                COMMENT_COUNT, FAV_COUNT, PIC_URI, TOKEN, USER_NAME, USER_ICON, USER_TOKEN
        };

        public static final int INDEX_ID = 0;
        public static final int INDEX_CONTENT = 1;
        public static final int INDEX_STAMP = 2;
        public static final int INDEX_TYPE = 3;
        public static final int INDEX_HAS_PIC = 4;
        public static final int INDEX_COMMENT_COUNT = 5;
        public static final int INDEX_FAV_COUNT = 6;
        public static final int INDEX_PIC_URI = 7;
        public static final int INDEX_TOKEN = 8;
        public static final int INDEX_USER_NAME = 9;
        public static final int INDEX_USER_ICON = 10;
        public static final int INDEX_USER_TOKEN = 11;
    }

}
