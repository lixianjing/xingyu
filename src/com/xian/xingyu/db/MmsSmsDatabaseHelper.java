//
//package com.xian.xingyu.db;
//
//import android.annotation.SuppressLint;
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//import android.provider.BaseColumns;
//import android.util.Log;
//
//import java.io.File;
//import java.io.FileOutputStream;
//
//@SuppressLint("NewApi")
//public class MmsSmsDatabaseHelper extends SQLiteOpenHelper {
//    private static final String TAG = "MmsSmsDatabaseHelper";
//
//    private static final String SMS_UPDATE_THREAD_READ_BODY = "  UPDATE threads SET read = "
//            + "    CASE (SELECT COUNT(*)" + "          FROM sms" + "          WHERE " + Sms.READ
//            + " = 0" + "            AND " + Sms.THREAD_ID + " = threads._id)"
//            + "      WHEN 0 THEN 1" + "      ELSE 0" + "    END" + "  WHERE threads._id = new."
//            + Sms.THREAD_ID + "; ";
//
//
//    private static final String UPDATE_THREAD_COUNT_ON_NEW =
//            "  UPDATE threads SET message_count = "
//                    + "     (SELECT COUNT(sms._id) FROM sms LEFT JOIN threads "
//                    + "      ON threads._id = " + Sms.THREAD_ID + "      WHERE " + Sms.THREAD_ID
//                    + " = new.thread_id" + "        AND sms." + Sms.TYPE + " != 3) + "
//                    + "     (SELECT COUNT(pdu._id) FROM pdu LEFT JOIN threads "
//                    + "      ON threads._id = " + Mms.THREAD_ID + "      WHERE " + Mms.THREAD_ID
//                    + " = new.thread_id" + "        AND (m_type=132 OR m_type=130 OR m_type=128)"
//                    + "        AND " + Mms.MESSAGE_BOX + " != 3) "
//                    + "  WHERE threads._id = new.thread_id; ";
//
//
//    private static final String UPDATE_THREAD_COUNT_ON_OLD =
//            "  UPDATE threads SET message_count = "
//                    + "     (SELECT COUNT(sms._id) FROM sms LEFT JOIN threads "
//                    + "      ON threads._id = " + Sms.THREAD_ID + "      WHERE " + Sms.THREAD_ID
//                    + " = old.thread_id" + "        AND sms." + Sms.TYPE + " != 3) + "
//                    + "     (SELECT COUNT(pdu._id) FROM pdu LEFT JOIN threads "
//                    + "      ON threads._id = " + Mms.THREAD_ID + "      WHERE " + Mms.THREAD_ID
//                    + " = old.thread_id" + "        AND (m_type=132 OR m_type=130 OR m_type=128)"
//                    + "        AND " + Mms.MESSAGE_BOX + " != 3) "
//                    + "  WHERE threads._id = old.thread_id; ";
//
//    private static final String SMS_UPDATE_THREAD_DATE_SNIPPET_COUNT_ON_UPDATE = "BEGIN"
//            + "  UPDATE threads SET" + "    date = new." + Sms.DATE + ", " + "    snippet = new."
//            + Sms.SMS_SNIPPET + ", " + "    snippet_cs = 0 ," + " has_emoji= new.has_emoji "
//            + "  WHERE threads._id = new."
//            + Sms.THREAD_ID + "; " + UPDATE_THREAD_COUNT_ON_NEW + SMS_UPDATE_THREAD_READ_BODY
//            + "END;";
//
//
//    private static final String PDU_UPDATE_THREAD_CONSTRAINTS = "  WHEN new." + Mms.MESSAGE_TYPE
//            + "=" + PduHeaders.MESSAGE_TYPE_RETRIEVE_CONF + "    OR new." + Mms.MESSAGE_TYPE + "="
//            + PduHeaders.MESSAGE_TYPE_NOTIFICATION_IND + "    OR new." + Mms.MESSAGE_TYPE + "="
//            + PduHeaders.MESSAGE_TYPE_SEND_REQ + " ";
//
//    // When looking in the pdu table for unread messages, only count messages that
//    // are displayed to the user. The constants are defined in PduHeaders and could be used
//    // here, but the string "(m_type=132 OR m_type=130 OR m_type=128)" is used throughout this
//    // file and so it is used here to be consistent.
//    // m_type=128 = MESSAGE_TYPE_SEND_REQ
//    // m_type=130 = MESSAGE_TYPE_NOTIFICATION_IND
//    // m_type=132 = MESSAGE_TYPE_RETRIEVE_CONF
//    private static final String PDU_UPDATE_THREAD_READ_BODY = "  UPDATE threads SET read = "
//            + "    CASE (SELECT COUNT(*)" + "          FROM " + MmsProvider.TABLE_PDU
//            + "          WHERE " + Mms.READ + " = 0" + "            AND " + Mms.THREAD_ID
//            + " = threads._id " + "            AND (m_type=132 OR m_type=130 OR m_type=128)) "
//            + "      WHEN 0 THEN 1" + "      ELSE 0" + "    END" + "  WHERE threads._id = new."
//            + Mms.THREAD_ID + "; ";
//
//    private static final String PDU_UPDATE_THREAD_DATE_SNIPPET_COUNT_ON_UPDATE = "BEGIN"
//            + "  UPDATE threads SET" + "    date = ((new." + Mms.DATE + ")*1000), "
//            + "    snippet = new." + Mms.SUBJECT + ", " + " has_emoji = 1 ,"
//            + "    snippet_cs = new."
//            + Mms.SUBJECT_CHARSET + "  WHERE threads._id = new." + Mms.THREAD_ID + "; "
//            + UPDATE_THREAD_COUNT_ON_NEW + PDU_UPDATE_THREAD_READ_BODY + "END;";
//
//    private static final String UPDATE_THREAD_SNIPPET_SNIPPET_CS_ON_DELETE =
//            "  UPDATE threads SET snippet = " + "   (SELECT snippet FROM"
//                    + "     (SELECT date * 1000 AS date, sub AS snippet, thread_id FROM pdu"
//                    + "      UNION SELECT date, sms_snippet AS snippet, thread_id FROM sms)"
//                    + "    WHERE thread_id = OLD.thread_id ORDER BY date DESC LIMIT 1) "
//                    + "  WHERE threads._id = OLD.thread_id; "
//                    + "  UPDATE threads SET snippet_cs = " + "   (SELECT snippet_cs FROM"
//                    + "     (SELECT date * 1000 AS date, sub_cs AS snippet_cs, thread_id FROM pdu"
//                    + "      UNION SELECT date, 0 AS snippet_cs, thread_id FROM sms)"
//                    + "    WHERE thread_id = OLD.thread_id ORDER BY date DESC LIMIT 1) "
//                    + "  WHERE threads._id = OLD.thread_id; ";
//
//
//    // When a part is inserted, if it is not text/plain or application/smil
//    // (which both can exist with text-only MMSes), then there is an attachment.
//    // Set has_attachment=1 in the threads table for the thread in question.
//    private static final String PART_UPDATE_THREADS_ON_INSERT_TRIGGER =
//            "CREATE TRIGGER update_threads_on_insert_part " + " AFTER INSERT ON part "
//                    + " WHEN new.ct != 'text/plain' AND new.ct != 'application/smil' " + " BEGIN "
//                    + "  UPDATE threads SET has_attachment=1 WHERE _id IN "
//                    + "   (SELECT pdu.thread_id FROM part JOIN pdu ON pdu._id=part.mid "
//                    + "     WHERE part._id=new._id LIMIT 1); " + " END";
//
//    // When the 'mid' column in the part table is updated, we need to run the trigger to update
//    // the threads table's has_attachment column, if the part is an attachment.
//    private static final String PART_UPDATE_THREADS_ON_UPDATE_TRIGGER =
//            "CREATE TRIGGER update_threads_on_update_part " + " AFTER UPDATE of " + Part.MSG_ID
//                    + " ON part "
//                    + " WHEN new.ct != 'text/plain' AND new.ct != 'application/smil' " + " BEGIN "
//                    + "  UPDATE threads SET has_attachment=1 WHERE _id IN "
//                    + "   (SELECT pdu.thread_id FROM part JOIN pdu ON pdu._id=part.mid "
//                    + "     WHERE part._id=new._id LIMIT 1); " + " END";
//
//
//    // When a part is deleted (with the same non-text/SMIL constraint as when
//    // we set has_attachment), update the threads table for all threads.
//    // Unfortunately we cannot update only the thread that the part was
//    // attached to, as it is possible that the part has been orphaned and
//    // the message it was attached to is already gone.
//    private static final String PART_UPDATE_THREADS_ON_DELETE_TRIGGER =
//            "CREATE TRIGGER update_threads_on_delete_part " + " AFTER DELETE ON part "
//                    + " WHEN old.ct != 'text/plain' AND old.ct != 'application/smil' " + " BEGIN "
//                    + "  UPDATE threads SET has_attachment = " + "   CASE "
//                    + "    (SELECT COUNT(*) FROM part JOIN pdu "
//                    + "     WHERE pdu.thread_id = threads._id "
//                    + "     AND part.ct != 'text/plain' AND part.ct != 'application/smil' "
//                    + "     AND part.mid = pdu._id)" + "   WHEN 0 THEN 0 " + "   ELSE 1 "
//                    + "   END; " + " END";
//
//    // When the 'thread_id' column in the pdu table is updated, we need to run the trigger to update
//    // the threads table's has_attachment column, if the message has an attachment in 'part' table
//    private static final String PDU_UPDATE_THREADS_ON_UPDATE_TRIGGER =
//            "CREATE TRIGGER update_threads_on_update_pdu " + " AFTER UPDATE of thread_id ON pdu "
//                    + " BEGIN " + "  UPDATE threads SET has_attachment=1 WHERE _id IN "
//                    + "   (SELECT pdu.thread_id FROM part JOIN pdu "
//                    + "     WHERE part.ct != 'text/plain' AND part.ct != 'application/smil' "
//                    + "     AND part.mid = pdu._id);" + " END";
//
//
//
//    private static final String CREATE_TRIGGER_DELETE_EMESSAGE_DATA_ON_DELETE_SMS =
//            "CREATE TRIGGER IF NOT EXISTS delete_emessage_data_on_delete_sms "
//                    + " AFTER DELETE ON sms " + " WHEN old.emessage_type!=0 " + " BEGIN "
//                    + " DELETE from emessage_data WHERE sms_id = old._id; " + "END ;";
//
//    private static MmsSmsDatabaseHelper mInstance = null;
//
//    static final String DATABASE_NAME = "messages.db";
//    static final int DATABASE_VERSION = 68;
//    private final Context mContext;
//
//    private MmsSmsDatabaseHelper(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//        mContext = context;
//    }
//
//    /**
//     * Return a singleton helper for the combined MMS and SMS database.
//     */
//    /* package */static synchronized MmsSmsDatabaseHelper getInstance(Context context) {
//        if (mInstance == null) {
//            mInstance = new MmsSmsDatabaseHelper(context);
//        }
//        return mInstance;
//    }
//
//
//
//    public static int deleteOneSms(SQLiteDatabase db, int message_id) {
//        int thread_id = -1;
//        // Find the thread ID that the specified SMS belongs to.
//        Cursor c =
//                db.query("sms", new String[] {"thread_id"}, "_id=" + message_id, null, null, null,
//                        null);
//        if (c != null) {
//            if (c.moveToFirst()) {
//                thread_id = c.getInt(0);
//            }
//            c.close();
//        }
//
//        // Delete the specified message.
//        int rows = db.delete("sms", "_id=" + message_id, null);
//        if (thread_id > 0) {
//            // Update its thread.
//            updateThread(db, thread_id);
//        }
//        return rows;
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        createMmsTables(db);
//        createSmsTables(db);
//        createCommonTables(db);
//        createCommonTriggers(db);
//        createMmsTriggers(db);
//        createWordsTables(db);
//        createIndices(db);
//        createEspierTables(db);
//        createEmessageTables(db);
//        createEmessageIndex(db);
//        createEucTables(db);
//        initEucData(db);
//        File files = new File(Emessage.getFilePath(mContext));
//        if (!files.isDirectory()) {
//            files.mkdir();
//        }
//        try {
//            files.setReadable(true);
//            files.setWritable(true);
//        } catch (Exception e) {
//            // TODO: handle exception
//        }
//    }
//
//    // When upgrading the database we need to populate the words
//    // table with the rows out of sms and part.
//    private void populateWordsTable(SQLiteDatabase db) {
//        final String TABLE_WORDS = "words";
//        {
//            Cursor smsRows =
//                    db.query("sms", new String[] {Sms._ID, Sms.BODY}, null, null, null, null, null);
//            try {
//                if (smsRows != null) {
//                    smsRows.moveToPosition(-1);
//                    ContentValues cv = new ContentValues();
//                    while (smsRows.moveToNext()) {
//                        cv.clear();
//
//                        long id = smsRows.getLong(0); // 0 for Sms._ID
//                        String body = smsRows.getString(1); // 1 for Sms.BODY
//
//                        cv.put(Telephony.MmsSms.WordsTable.ID, id);
//                        cv.put(Telephony.MmsSms.WordsTable.INDEXED_TEXT, body);
//                        cv.put(Telephony.MmsSms.WordsTable.SOURCE_ROW_ID, id);
//                        cv.put(Telephony.MmsSms.WordsTable.TABLE_ID, 1);
//                        db.insert(TABLE_WORDS, Telephony.MmsSms.WordsTable.INDEXED_TEXT, cv);
//                    }
//                }
//            } finally {
//                if (smsRows != null) {
//                    smsRows.close();
//                }
//            }
//        }
//
//        {
//            Cursor mmsRows =
//                    db.query("part", new String[] {Part._ID, Part.TEXT}, "ct = 'text/plain'", null,
//                            null, null, null);
//            try {
//                if (mmsRows != null) {
//                    mmsRows.moveToPosition(-1);
//                    ContentValues cv = new ContentValues();
//                    while (mmsRows.moveToNext()) {
//                        cv.clear();
//
//                        long id = mmsRows.getLong(0); // 0 for Part._ID
//                        String body = mmsRows.getString(1); // 1 for Part.TEXT
//
//                        cv.put(Telephony.MmsSms.WordsTable.ID, id);
//                        cv.put(Telephony.MmsSms.WordsTable.INDEXED_TEXT, body);
//                        cv.put(Telephony.MmsSms.WordsTable.SOURCE_ROW_ID, id);
//                        cv.put(Telephony.MmsSms.WordsTable.TABLE_ID, 1);
//                        db.insert(TABLE_WORDS, Telephony.MmsSms.WordsTable.INDEXED_TEXT, cv);
//                    }
//                }
//            } finally {
//                if (mmsRows != null) {
//                    mmsRows.close();
//                }
//            }
//        }
//    }
//
//    private void createWordsTables(SQLiteDatabase db) {
//        try {
//            db.execSQL("CREATE VIRTUAL TABLE words USING FTS3 (_id INTEGER PRIMARY KEY, index_text TEXT, source_id INTEGER, table_to_use INTEGER);");
//
//            // monitor the sms table
//            // NOTE don't handle inserts using a trigger because it has an unwanted
//            // side effect: the value returned for the last row ends up being the
//            // id of one of the trigger insert not the original row insert.
//            // Handle inserts manually in the provider.
//            db.execSQL("CREATE TRIGGER sms_words_update AFTER UPDATE ON sms BEGIN UPDATE words "
//                    + " SET index_text = NEW.body WHERE (source_id=NEW._id AND table_to_use=1); "
//                    + " END;");
//            db.execSQL("CREATE TRIGGER sms_words_delete AFTER DELETE ON sms BEGIN DELETE FROM "
//                    + "  words WHERE source_id = OLD._id AND table_to_use = 1; END;");
//
//            // monitor the mms table
//            db.execSQL("CREATE TRIGGER mms_words_update AFTER UPDATE ON part BEGIN UPDATE words "
//                    + " SET index_text = NEW.text WHERE (source_id=NEW._id AND table_to_use=2); "
//                    + " END;");
//            db.execSQL("CREATE TRIGGER mms_words_delete AFTER DELETE ON part BEGIN DELETE FROM "
//                    + " words WHERE source_id = OLD._id AND table_to_use = 2; END;");
//
//            populateWordsTable(db);
//        } catch (Exception ex) {
//            Log.e(TAG, "got exception creating words table: " + ex.toString());
//        }
//    }
//
//    private void createIndices(SQLiteDatabase db) {
//        createThreadIdIndex(db);
//    }
//
//    private void createThreadIdIndex(SQLiteDatabase db) {
//        try {
//            db.execSQL("CREATE INDEX IF NOT EXISTS typeThreadIdIndex ON sms"
//                    + " (type, thread_id);");
//        } catch (Exception ex) {
//            Log.e(TAG, "got exception creating indices: " + ex.toString());
//        }
//    }
//
//    private void createMmsTables(SQLiteDatabase db) {
//        // N.B.: Whenever the columns here are changed, the columns in
//        // {@ref MmsSmsProvider} must be changed to match.
//        db.execSQL("CREATE TABLE " + MmsProvider.TABLE_PDU + " (" + Mms._ID
//                + " INTEGER PRIMARY KEY," + Mms.THREAD_ID + " INTEGER," + Mms.DATE + " INTEGER,"
//                + Mms.MESSAGE_BOX + " INTEGER," + Mms.READ + " INTEGER DEFAULT 0," + Mms.MESSAGE_ID
//                + " TEXT," + Mms.SUBJECT + " TEXT," + Mms.SUBJECT_CHARSET + " INTEGER,"
//                + Mms.CONTENT_TYPE + " TEXT," + Mms.CONTENT_LOCATION + " TEXT," + Mms.EXPIRY
//                + " INTEGER," + Mms.MESSAGE_CLASS + " TEXT," + Mms.MESSAGE_TYPE + " INTEGER,"
//                + Mms.MMS_VERSION + " INTEGER," + Mms.MESSAGE_SIZE + " INTEGER," + Mms.PRIORITY
//                + " INTEGER," + Mms.READ_REPORT + " INTEGER," + Mms.REPORT_ALLOWED + " INTEGER,"
//                + Mms.RESPONSE_STATUS + " INTEGER," + Mms.STATUS + " INTEGER," + Mms.TRANSACTION_ID
//                + " TEXT," + Mms.RETRIEVE_STATUS + " INTEGER," + Mms.RETRIEVE_TEXT + " TEXT,"
//                + Mms.RETRIEVE_TEXT_CHARSET + " INTEGER," + Mms.READ_STATUS + " INTEGER,"
//                + Mms.CONTENT_CLASS + " INTEGER," + Mms.RESPONSE_TEXT + " TEXT,"
//                + Mms.DELIVERY_TIME + " INTEGER," + Mms.DELIVERY_REPORT + " INTEGER," + Mms.LOCKED
//                + " INTEGER DEFAULT 0," + Mms.SEEN + " INTEGER DEFAULT 0" + ");");
//
//        db.execSQL("CREATE TABLE " + MmsProvider.TABLE_ADDR + " (" + Addr._ID
//                + " INTEGER PRIMARY KEY," + Addr.MSG_ID + " INTEGER," + Addr.CONTACT_ID
//                + " INTEGER," + Addr.ADDRESS + " TEXT," + Addr.TYPE + " INTEGER," + Addr.CHARSET
//                + " INTEGER);");
//
//        db.execSQL("CREATE TABLE " + MmsProvider.TABLE_PART + " (" + Part._ID
//                + " INTEGER PRIMARY KEY," + Part.MSG_ID + " INTEGER," + Part.SEQ
//                + " INTEGER DEFAULT 0," + Part.CONTENT_TYPE + " TEXT," + Part.NAME + " TEXT,"
//                + Part.CHARSET + " INTEGER," + Part.CONTENT_DISPOSITION + " TEXT," + Part.FILENAME
//                + " TEXT," + Part.CONTENT_ID + " TEXT," + Part.CONTENT_LOCATION + " TEXT,"
//                + Part.CT_START + " INTEGER," + Part.CT_TYPE + " TEXT," + Part._DATA + " TEXT,"
//                + Part.TEXT + " TEXT);");
//
//        db.execSQL("CREATE TABLE " + MmsProvider.TABLE_RATE + " (" + Rate.SENT_TIME + " INTEGER);");
//
//        db.execSQL("CREATE TABLE " + MmsProvider.TABLE_DRM + " (" + BaseColumns._ID
//                + " INTEGER PRIMARY KEY," + "_data TEXT);");
//    }
//
//    private void createMmsTriggers(SQLiteDatabase db) {
//        // Cleans up parts when a MM is deleted.
//        db.execSQL("CREATE TRIGGER part_cleanup DELETE ON " + MmsProvider.TABLE_PDU + " "
//                + "BEGIN " + "  DELETE FROM " + MmsProvider.TABLE_PART + "  WHERE " + Part.MSG_ID
//                + "=old._id;" + "END;");
//
//        // Cleans up address info when a MM is deleted.
//        db.execSQL("CREATE TRIGGER addr_cleanup DELETE ON " + MmsProvider.TABLE_PDU + " "
//                + "BEGIN " + "  DELETE FROM " + MmsProvider.TABLE_ADDR + "  WHERE " + Addr.MSG_ID
//                + "=old._id;" + "END;");
//
//        // Delete obsolete delivery-report, read-report while deleting their
//        // associated Send.req.
//        db.execSQL("CREATE TRIGGER cleanup_delivery_and_read_report " + "AFTER DELETE ON "
//                + MmsProvider.TABLE_PDU + " " + "WHEN old." + Mms.MESSAGE_TYPE + "="
//                + PduHeaders.MESSAGE_TYPE_SEND_REQ + " " + "BEGIN " + "  DELETE FROM "
//                + MmsProvider.TABLE_PDU + "  WHERE (" + Mms.MESSAGE_TYPE + "="
//                + PduHeaders.MESSAGE_TYPE_DELIVERY_IND + "    OR " + Mms.MESSAGE_TYPE + "="
//                + PduHeaders.MESSAGE_TYPE_READ_ORIG_IND + ")" + "    AND " + Mms.MESSAGE_ID
//                + "=old." + Mms.MESSAGE_ID + "; " + "END;");
//
//        // Update threads table to indicate whether attachments exist when
//        // parts are inserted or deleted.
//        db.execSQL(PART_UPDATE_THREADS_ON_INSERT_TRIGGER);
//        db.execSQL(PART_UPDATE_THREADS_ON_UPDATE_TRIGGER);
//        db.execSQL(PART_UPDATE_THREADS_ON_DELETE_TRIGGER);
//        db.execSQL(PDU_UPDATE_THREADS_ON_UPDATE_TRIGGER);
//    }
//
//    private void createSmsTables(SQLiteDatabase db) {
//        // N.B.: Whenever the columns here are changed, the columns in
//        // {@ref MmsSmsProvider} must be changed to match.
//        db.execSQL("CREATE TABLE sms ("
//                + "_id INTEGER PRIMARY KEY,"
//                + "thread_id INTEGER,"
//                + "address TEXT,"
//                + "person INTEGER,"
//                + "date INTEGER,"
//                + "protocol INTEGER,"
//                + "read INTEGER DEFAULT 0,"
//                + "status INTEGER DEFAULT -1,"
//                + // a TP-Status value
//                  // or -1 if it
//                  // status hasn't
//                  // been received
//                "type INTEGER,"
//                + "reply_path_present INTEGER,"
//                + "subject TEXT,"
//                + "body TEXT,"
//                + "service_center TEXT,"
//                + "locked INTEGER DEFAULT 0,"
//                + "error_code INTEGER DEFAULT 0,"
//                + "seen INTEGER DEFAULT 0 ,"
//                + "sms_id INTEGER,"
//                + "emessage_type INTEGER  DEFAULT 0,"
//                + "emessage_id TEXT,"
//                + "emessage_address TEXT,"
//                + "emessage_status INTEGER DEFAULT 1 ,"
//                + "emessage_date INTEGER DEFAULT 0 , "
//                + " secret_type INTEGER DEFAULT 0  ,"
//                + " sms_snippet TEXT , privacy INTEGER DEFAULT 0 , emessage_online INTEGER DEFAULT 0 , has_emoji INTEGER DEFAULT 0);");
//
//        /**
//         * This table is used by the SMS dispatcher to hold incomplete partial messages until all
//         * the parts arrive.
//         */
//        db.execSQL("CREATE TABLE raw (" + "_id INTEGER PRIMARY KEY," + "date INTEGER,"
//                + "reference_number INTEGER," + // one per full message
//                "count INTEGER," + // the number of parts
//                "sequence INTEGER," + // the part number of this message
//                "destination_port INTEGER," + "address TEXT," + "pdu TEXT);"); // the raw PDU for
//                                                                               // this part
//
//        db.execSQL("CREATE TABLE attachments (" + "sms_id INTEGER," + "content_url TEXT,"
//                + "offset INTEGER);");
//
//        /**
//         * This table is used by the SMS dispatcher to hold pending delivery status report intents.
//         */
//        db.execSQL("CREATE TABLE sr_pending (" + "reference_number INTEGER," + "action TEXT,"
//                + "data TEXT);");
//
//    }
//
//    private void createEmessageTables(SQLiteDatabase db) {
//        // N.B.: Whenever the columns here are changed, the columns in
//        // {@ref MmsSmsProvider} must be changed to match.
//
//        /**
//         * This table is used by the emessage save blob data
//         */
//        db.execSQL("CREATE TABLE emessage_data ( _id INTEGER PRIMARY KEY," + "sms_id INTEGER ,"
//                + "data blob , seq INTEGER DEFAULT -1, ct TEXT ," + "fp TEXT , content TEXT,"
//                + "size INTEGER, type INTEGER DEFAULT -1," + "version DEFAULT " + DATABASE_VERSION
//                + "," + "user_status INTEGER , file_name TEXT ," + "file_digest TEXT ,"
//                + "file_status INTEGER , " + "access_url TEXT, " + "access_passwd TEXT,"
//                + "file_download_pos INTEGER ," + "file_download_percent INTEGER ,"
//                + "file_begin_date INTEGER ,"
//                + "file_finish_date INTEGER ,voice_time INTEGER ,voice_play INTEGER ,euc_record_id INTEGER);");
//
//
//        /**
//         * This table is used by the emessage no numbers
//         */
//        db.execSQL("CREATE TABLE emessage_no_user_numbers (" + "_id INTEGER PRIMARY KEY,"
//                + "number TEXT ," + "date INTEGER);");
//
//        db.execSQL(CREATE_TRIGGER_DELETE_EMESSAGE_DATA_ON_DELETE_SMS);
//
//        db.execSQL("CREATE TABLE emessage_contacts_filter (" + "_id INTEGER PRIMARY KEY,"
//                + "contacts_id TEXT ," + "type INTEGER DEFAULT 0);");
//
//        db.execSQL("CREATE TABLE espier_timer (" + "_id INTEGER PRIMARY KEY," + "endTime LONG ,"
//                + "action TEXT ," + "append TEXT ," + "type INTEGER DEFAULT 0, key_index TEXT );");
//    }
//
//    private void createEmessageIndex(SQLiteDatabase db) {
//        db.execSQL("CREATE INDEX index_threads ON threads(date)");
//        db.execSQL("CREATE INDEX index_threads_common ON threads_common(date)");
//        db.execSQL("CREATE INDEX index_sms ON sms(thread_id,address)");
//    }
//
//    private void createCommonTables(SQLiteDatabase db) {
//        // TODO Ensure that each entry is removed when the last use of
//        // any address equivalent to its address is removed.
//
//        /**
//         * This table maps the first instance seen of any particular MMS/SMS address to an ID, which
//         * is then used as its canonical representation. If the same address or an equivalent
//         * address (as determined by our Sqlite PHONE_NUMBERS_EQUAL extension) is seen later, this
//         * same ID will be used.
//         */
//        db.execSQL("CREATE TABLE canonical_addresses (" + "_id INTEGER PRIMARY KEY,"
//                + "address TEXT);");
//
//        /**
//         * This table maps the subject and an ordered set of recipient IDs, separated by spaces, to
//         * a unique thread ID. The IDs come from the canonical_addresses table. This works because
//         * messages are considered to be part of the same thread if they have the same subject (or a
//         * null subject) and the same set of recipients.
//         */
//        db.execSQL("CREATE TABLE threads (" + Threads._ID + " INTEGER PRIMARY KEY," + Threads.DATE
//                + " INTEGER DEFAULT 0," + Threads.MESSAGE_COUNT + " INTEGER DEFAULT 0,"
//                + Threads.RECIPIENT_IDS + " TEXT," + Threads.SNIPPET + " TEXT,"
//                + Threads.SNIPPET_CHARSET + " INTEGER DEFAULT 0," + Threads.READ
//                + " INTEGER DEFAULT 1," + Threads.TYPE + " INTEGER DEFAULT 0," + Threads.ERROR
//                + " INTEGER DEFAULT 0," + Threads.HAS_ATTACHMENT + " INTEGER DEFAULT 0,"
//                + Threads.FAV + " INTEGER DEFAULT 0,"+ Threads.HAS_EMOJI + " INTEGER DEFAULT 0 );");
//
//
//        /**
//         * threads for common
//         */
//        db.execSQL("CREATE TABLE threads_common (" + Threads._ID + " INTEGER PRIMARY KEY,"
//                + Threads.DATE + " INTEGER DEFAULT 0," + Threads.MESSAGE_COUNT
//                + " INTEGER DEFAULT 0," + Threads.RECIPIENT_IDS + " TEXT," + Threads.SNIPPET
//                + " TEXT," + Threads.SNIPPET_CHARSET + " INTEGER DEFAULT 0," + Threads.READ
//                + " INTEGER DEFAULT 1," + Threads.TYPE + " INTEGER DEFAULT 0," + Threads.ERROR
//                + " INTEGER DEFAULT 0," + Threads.HAS_ATTACHMENT + " INTEGER DEFAULT 0,"
//                + Threads.FAV + " INTEGER DEFAULT 0," + Threads.HAS_EMOJI + " INTEGER DEFAULT 0 );");
//
//        /**
//         * This table stores the queue of messages to be sent/downloaded.
//         */
//        db.execSQL("CREATE TABLE " + MmsSmsProvider.TABLE_PENDING_MSG + " (" + PendingMessages._ID
//                + " INTEGER PRIMARY KEY," + PendingMessages.PROTO_TYPE + " INTEGER,"
//                + PendingMessages.MSG_ID + " INTEGER," + PendingMessages.MSG_TYPE + " INTEGER,"
//                + PendingMessages.ERROR_TYPE + " INTEGER," + PendingMessages.ERROR_CODE
//                + " INTEGER," + PendingMessages.RETRY_INDEX + " INTEGER NOT NULL DEFAULT 0,"
//                + PendingMessages.DUE_TIME + " INTEGER," + PendingMessages.LAST_TRY + " INTEGER);");
//
//    }
//
//    // TODO Check the query plans for these triggers.
//    private void createCommonTriggers(SQLiteDatabase db) {
//        // Updates threads table whenever a message is added to pdu.
//        db.execSQL("CREATE TRIGGER pdu_update_thread_on_insert AFTER INSERT ON "
//                + MmsProvider.TABLE_PDU + " " + PDU_UPDATE_THREAD_CONSTRAINTS
//                + PDU_UPDATE_THREAD_DATE_SNIPPET_COUNT_ON_UPDATE);
//
//        // Updates threads table whenever a message is added to sms.
//        db.execSQL("CREATE TRIGGER sms_update_thread_on_insert AFTER INSERT ON sms "
//                + SMS_UPDATE_THREAD_DATE_SNIPPET_COUNT_ON_UPDATE);
//
//        // Updates threads table whenever a message in pdu is updated.
//        db.execSQL("CREATE TRIGGER pdu_update_thread_date_subject_on_update AFTER" + "  UPDATE OF "
//                + Mms.DATE + ", " + Mms.SUBJECT + ", " + Mms.MESSAGE_BOX + "  ON "
//                + MmsProvider.TABLE_PDU + " " + PDU_UPDATE_THREAD_CONSTRAINTS
//                + PDU_UPDATE_THREAD_DATE_SNIPPET_COUNT_ON_UPDATE);
//
//        // Updates threads table whenever a message in sms is updated.
//        db.execSQL("CREATE TRIGGER sms_update_thread_date_subject_on_update AFTER" + "  UPDATE OF "
//                + Sms.DATE + ", " + Sms.BODY + ", " + Sms.TYPE + "  ON sms "
//                + SMS_UPDATE_THREAD_DATE_SNIPPET_COUNT_ON_UPDATE);
//
//        // Updates threads table whenever a message in pdu is updated.
//        db.execSQL("CREATE TRIGGER pdu_update_thread_read_on_update AFTER" + "  UPDATE OF "
//                + Mms.READ + "  ON " + MmsProvider.TABLE_PDU + " " + PDU_UPDATE_THREAD_CONSTRAINTS
//                + "BEGIN " + PDU_UPDATE_THREAD_READ_BODY + "END;");
//
//        // Updates threads table whenever a message in sms is updated.
//        db.execSQL("CREATE TRIGGER sms_update_thread_read_on_update AFTER" + "  UPDATE OF "
//                + Sms.READ + "  ON sms " + "BEGIN " + SMS_UPDATE_THREAD_READ_BODY + "END;");
//
//        // Update threads table whenever a message in pdu is deleted
//        db.execSQL("CREATE TRIGGER pdu_update_thread_on_delete " + "AFTER DELETE ON pdu "
//                + "BEGIN " + "  UPDATE threads SET " + "     date = (strftime('%s','now') * 1000)"
//                + "  WHERE threads._id = old." + Mms.THREAD_ID + "; " + UPDATE_THREAD_COUNT_ON_OLD
//                + UPDATE_THREAD_SNIPPET_SNIPPET_CS_ON_DELETE + "END;");
//
//        // When the last message in a thread is deleted, these
//        // triggers ensure that the entry for its thread ID is removed
//        // from the threads table.
//        db.execSQL("CREATE TRIGGER delete_obsolete_threads_pdu " + "AFTER DELETE ON pdu "
//                + "BEGIN " + "  DELETE FROM threads " + "  WHERE " + "    _id = old.thread_id "
//                + "    AND _id NOT IN " + "    (SELECT thread_id FROM sms "
//                + "     UNION SELECT thread_id from pdu); " + "END;");
//
//        db.execSQL("CREATE TRIGGER delete_obsolete_threads_when_update_pdu " + "AFTER UPDATE OF "
//                + Mms.THREAD_ID + " ON pdu " + "WHEN old." + Mms.THREAD_ID + " != new."
//                + Mms.THREAD_ID + " " + "BEGIN " + "  DELETE FROM threads " + "  WHERE "
//                + "    _id = old.thread_id " + "    AND _id NOT IN "
//                + "    (SELECT thread_id FROM sms " + "     UNION SELECT thread_id from pdu); "
//                + "END;");
//        // Insert pending status for M-Notification.ind or M-ReadRec.ind
//        // when they are inserted into Inbox/Outbox.
//        db.execSQL("CREATE TRIGGER insert_mms_pending_on_insert " + "AFTER INSERT ON pdu "
//                + "WHEN new."
//                + Mms.MESSAGE_TYPE
//                + "="
//                + PduHeaders.MESSAGE_TYPE_NOTIFICATION_IND
//                + "  OR new."
//                + Mms.MESSAGE_TYPE
//                + "="
//                + PduHeaders.MESSAGE_TYPE_READ_REC_IND
//                + " "
//                + "BEGIN "
//                + "  INSERT INTO "
//                + MmsSmsProvider.TABLE_PENDING_MSG
//                + "    ("
//                + PendingMessages.PROTO_TYPE
//                + ","
//                + "     "
//                + PendingMessages.MSG_ID
//                + ","
//                + "     "
//                + PendingMessages.MSG_TYPE
//                + ","
//                + "     "
//                + PendingMessages.ERROR_TYPE
//                + ","
//                + "     "
//                + PendingMessages.ERROR_CODE
//                + ","
//                + "     "
//                + PendingMessages.RETRY_INDEX
//                + ","
//                + "     "
//                + PendingMessages.DUE_TIME
//                + ") "
//                + "  VALUES "
//                + "    ("
//                + MmsSms.MMS_PROTO
//                + ","
//                + "      new."
//                + BaseColumns._ID
//                + "," + "      new." + Mms.MESSAGE_TYPE + ",0,0,0,0);" + "END;");
//
//        // Insert pending status for M-Send.req when it is moved into Outbox.
//        db.execSQL("CREATE TRIGGER insert_mms_pending_on_update " + "AFTER UPDATE ON pdu "
//                + "WHEN new."
//                + Mms.MESSAGE_TYPE
//                + "="
//                + PduHeaders.MESSAGE_TYPE_SEND_REQ
//                + "  AND new."
//                + Mms.MESSAGE_BOX
//                + "="
//                + Mms.MESSAGE_BOX_OUTBOX
//                + "  AND old."
//                + Mms.MESSAGE_BOX
//                + "!="
//                + Mms.MESSAGE_BOX_OUTBOX
//                + " "
//                + "BEGIN "
//                + "  INSERT INTO "
//                + MmsSmsProvider.TABLE_PENDING_MSG
//                + "    ("
//                + PendingMessages.PROTO_TYPE
//                + ","
//                + "     "
//                + PendingMessages.MSG_ID
//                + ","
//                + "     "
//                + PendingMessages.MSG_TYPE
//                + ","
//                + "     "
//                + PendingMessages.ERROR_TYPE
//                + ","
//                + "     "
//                + PendingMessages.ERROR_CODE
//                + ","
//                + "     "
//                + PendingMessages.RETRY_INDEX
//                + ","
//                + "     "
//                + PendingMessages.DUE_TIME
//                + ") "
//                + "  VALUES "
//                + "    ("
//                + MmsSms.MMS_PROTO
//                + ","
//                + "      new."
//                + BaseColumns._ID
//                + ","
//                + "      new."
//                + Mms.MESSAGE_TYPE
//                + ",0,0,0,0);" + "END;");
//
//        // When a message is moved out of Outbox, delete its pending status.
//        db.execSQL("CREATE TRIGGER delete_mms_pending_on_update " + "AFTER UPDATE ON "
//                + MmsProvider.TABLE_PDU + " " + "WHEN old." + Mms.MESSAGE_BOX + "="
//                + Mms.MESSAGE_BOX_OUTBOX + "  AND new." + Mms.MESSAGE_BOX + "!="
//                + Mms.MESSAGE_BOX_OUTBOX + " " + "BEGIN " + "  DELETE FROM "
//                + MmsSmsProvider.TABLE_PENDING_MSG + "  WHERE " + PendingMessages.MSG_ID
//                + "=new._id; " + "END;");
//
//        // Delete pending status for a message when it is deleted.
//        db.execSQL("CREATE TRIGGER delete_mms_pending_on_delete " + "AFTER DELETE ON "
//                + MmsProvider.TABLE_PDU + " " + "BEGIN " + "  DELETE FROM "
//                + MmsSmsProvider.TABLE_PENDING_MSG + "  WHERE " + PendingMessages.MSG_ID
//                + "=old._id; " + "END;");
//
//        // TODO Add triggers for SMS retry-status management.
//
//        // Update the error flag of threads when the error type of
//        // a pending MM is updated.
//        db.execSQL("CREATE TRIGGER update_threads_error_on_update_mms "
//                + "  AFTER UPDATE OF err_type ON pending_msgs "
//                + "  WHEN (OLD.err_type < 10 AND NEW.err_type >= 10)"
//                + "    OR (OLD.err_type >= 10 AND NEW.err_type < 10) " + "BEGIN"
//                + "  UPDATE threads SET error = " + "    CASE"
//                + "      WHEN NEW.err_type >= 10 THEN error + 1" + "      ELSE error - 1"
//                + "    END " + "  WHERE _id =" + "   (SELECT DISTINCT thread_id" + "    FROM pdu"
//                + "    WHERE _id = NEW.msg_id); " + "END;");
//
//        // Update the error flag of threads when delete pending message.
//        db.execSQL("CREATE TRIGGER update_threads_error_on_delete_mms " + "  BEFORE DELETE ON pdu"
//                + "  WHEN OLD._id IN (SELECT DISTINCT msg_id"
//                + "                   FROM pending_msgs"
//                + "                   WHERE err_type >= 10) " + "BEGIN "
//                + "  UPDATE threads SET error = error - 1" + "  WHERE _id = OLD.thread_id; "
//                + "END;");
//
//        // Update the error flag of threads while moving an MM out of Outbox,
//        // which was failed to be sent permanently.
//        db.execSQL("CREATE TRIGGER update_threads_error_on_move_mms "
//                + "  BEFORE UPDATE OF msg_box ON pdu "
//                + "  WHEN (OLD.msg_box = 4 AND NEW.msg_box != 4) "
//                + "  AND (OLD._id IN (SELECT DISTINCT msg_id"
//                + "                   FROM pending_msgs"
//                + "                   WHERE err_type >= 10)) " + "BEGIN "
//                + "  UPDATE threads SET error = error - 1" + "  WHERE _id = OLD.thread_id; "
//                + "END;");
//
//        // Update the error flag of threads after a text message was
//        // failed to send/receive.
//        db.execSQL("CREATE TRIGGER update_threads_error_on_update_sms "
//                + "  AFTER UPDATE OF type ON sms" + "  WHEN (OLD.type != 5 AND NEW.type = 5)"
//                + "    OR (OLD.type = 5 AND NEW.type != 5) " + "BEGIN "
//                + "  UPDATE threads SET error = " + "    CASE"
//                + "      WHEN NEW.type = 5 THEN error + 1" + "      ELSE error - 1" + "    END "
//                + "  WHERE _id = NEW.thread_id; " + "END;");
//
//
//        // lmf add
//        // for thread_common
//
//        db.execSQL(" CREATE TRIGGER thread_update_thread_common_snippet_on_update "
//                + " AFTER UPDATE OF message_count,snippet,snippet_cs ON threads "
//                + " BEGIN "
//                + " UPDATE threads_common SET snippet = "
//                + " (SELECT snippet FROM  (SELECT date * 1000 AS date, sub AS snippet, thread_id FROM pdu "
//                + " UNION SELECT date, sms_snippet AS snippet, thread_id FROM sms  WHERE privacy=0 ) "
//                + " WHERE thread_id = NEW._id ORDER BY date DESC LIMIT 1)    "
//                + " WHERE threads_common._id = NEW._id;   "
//                + " UPDATE threads_common SET snippet_cs =  (SELECT snippet_cs FROM   "
//                + " (SELECT date * 1000 AS date, sub_cs AS snippet_cs, thread_id FROM pdu "
//                + " UNION  SELECT date, 0 AS snippet_cs, thread_id FROM sms WHERE privacy=0 ) "
//                + " WHERE thread_id = NEW._id ORDER BY date DESC LIMIT 1) "
//                + " WHERE threads_common._id = NEW._id;"
//                + " UPDATE threads_common SET has_emoji = (SELECT has_emoji FROM "
//                + " (SELECT date * 1000 AS date, 1 AS has_emoji, thread_id FROM pdu "
//                + " UNION  SELECT date, has_emoji, thread_id FROM sms WHERE privacy=0 ) "
//                + " WHERE thread_id = NEW._id ORDER BY date DESC LIMIT 1) "
//                + " WHERE threads_common._id = NEW._id; " + "END; ");
//
//
//        db.execSQL(" CREATE TRIGGER thread_update_thread_common_count_on_update "
//                + " AFTER UPDATE OF message_count ON threads "
//                + " BEGIN "
//                + " REPLACE INTO  threads_common(_id,date,recipient_ids,type,snippet,snippet_cs,is_favorite)  "
//                + "VALUES(NEW._id,NEW.date,NEW.recipient_ids,NEW.type,NEW.snippet,NEW.snippet_cs,NEW.is_favorite); "
//                + " UPDATE threads_common SET message_count = "
//                + " (SELECT COUNT(sms._id) FROM sms LEFT JOIN threads_common ON threads_common._id = thread_id "
//                + " WHERE thread_id = NEW._id   AND sms.privacy=0  AND sms.type != 3) +  "
//                + " (SELECT COUNT(pdu._id) FROM pdu LEFT JOIN threads_common  ON threads_common._id = thread_id "
//                + " WHERE thread_id = NEW._id   AND (m_type=132 OR m_type=130 OR m_type=128)   "
//                + " AND msg_box != 3)  WHERE threads_common._id = NEW._id;  END; ");
//
//
//        db.execSQL(" CREATE TRIGGER thread_update_thread_common_status_on_update "
//                + " AFTER UPDATE OF date,error,has_attachment,is_favorite ON threads "
//                + " BEGIN  "
//                + " UPDATE threads_common SET date=NEW.date , error =  NEW.error , has_attachment= NEW.has_attachment , is_favorite=NEW.is_favorite "
//                + " WHERE threads_common._id = NEW._id; END; ");
//
//        db.execSQL(" CREATE TRIGGER thread_update_thread_common_read_on_update "
//                + " AFTER UPDATE OF read ON threads "
//                + " BEGIN  "
//                + " UPDATE threads_common SET read =   "
//                + " CASE ( "
//                + " (SELECT COUNT(*)  FROM sms  "
//                + " WHERE read = 0  AND  privacy=0  AND thread_id = threads_common._id)  + "
//                + " (SELECT COUNT(*)  FROM pdu   WHERE read = 0    AND thread_id = threads_common._id "
//                + " AND (m_type=132 OR m_type=130 OR m_type=128)) " + " ) "
//                + " WHEN 0 THEN 1      ELSE 0    END   " + " WHERE threads_common._id = new._id; "
//                + " END; ");
//
//
//        db.execSQL(" CREATE TRIGGER thread_update_thread_common_on_insert "
//                + " AFTER INSERT ON threads  BEGIN  "
//                + " INSERT INTO  threads_common(_id,date,recipient_ids,message_count,type)  "
//                + " VALUES(NEW._id,NEW.date,NEW.recipient_ids,NEW.message_count,NEW.type);  END; ");
//
//
//        db.execSQL(" CREATE TRIGGER thread_update_thread_common_on_delete "
//                + " AFTER DELETE ON threads "
//                + " BEGIN  DELETE from threads_common WHERE _id = old._id; END; ");
//
//
//        db.execSQL(" CREATE TRIGGER thread_common_delete_count_on_update "
//                + " AFTER UPDATE OF  message_count ON threads_common "
//                + " BEGIN  DELETE from threads_common WHERE _id = old._id AND message_count=0 AND "
//                + "(( select count(*)  from sms where privacy=1 AND  thread_id = old._id )>0); END; ");
//
//
//    }
//
//    private void createEspierTables(SQLiteDatabase db) {
//        db.execSQL("CREATE TABLE " + EspierProvider.TABLE_USERS + " (" + EspierProvider.User._ID
//                + " INTEGER PRIMARY KEY," + EspierProvider.User.J_ID + " TEXT,"
//                + EspierProvider.User.E_ID + " TEXT," + EspierProvider.User.CLIENT_ID + " TEXT,"
//                + EspierProvider.User.PHONE + " TEXT," + EspierProvider.User.NAME + " TEXT,"
//                + EspierProvider.User.UPDATE_TIME + " INTEGER,"
//                + EspierProvider.User.RECENT_CONTACT_TIME + " INTEGER DEFAULT 0,"
//                + EspierProvider.User.EXTRA1 + " TEXT," + EspierProvider.User.EXTRA2 + " TEXT);");
//
//        db.execSQL("CREATE INDEX IF NOT EXISTS recentContactTimeIndex ON espier_users"
//                + " (recent_contact_time);");
//
//        // try {
//        // db.execSQL("CREATE INDEX IF NOT EXISTS recentContactTimeIndex ON espier_users"
//        // + " (recent_contact_time);");
//        // } catch (Exception ex) {
//        // Log.e(TAG, "got exception creating indices: " + ex.toString());
//        // }
//
//        db.execSQL("INSERT INTO espier_users (j_id, espier_id, client_id, phone, "
//                + " name, update_time)"
//                + " VALUES ('xmpprobot#espier.mobi@xmpp.espier.mobi','xmpprobot@espier.mobi','robot',"
//                + " 'xmpprobot@espier.mobi','Robot Andrew'," + System.currentTimeMillis() + " )");
//
//
//        db.execSQL("INSERT INTO espier_users (j_id, espier_id, client_id, phone, "
//                + " name, update_time)"
//                + " VALUES ('xmppadmin#espier.mobi@xmpp.espier.mobi','xmppadmin@espier.mobi','admin',"
//                + " 'xmppadmin@espier.mobi','Espier Studio'," + System.currentTimeMillis() + " )");
//
//        db.execSQL("INSERT INTO espier_users (j_id, espier_id, client_id, phone, "
//                + " name, update_time)"
//                + " VALUES ('xmpp.espier.mobi','xmpp.espier.mobi','System',"
//                + " 'xmpp.espier.mobi','System'," + System.currentTimeMillis() + " )");
//    }
//
//    private void createEucTables(SQLiteDatabase db) {
//
//        db.execSQL("CREATE TABLE " + DBInfo.TABLE_CHAT_ROOM + " (" + EucInfo.DBInfo._ID
//                + " INTEGER PRIMARY KEY," + EucInfo.DBInfo.EUC_ID + " TEXT,"
//                + EucInfo.DBInfo.NAME + " TEXT," + EucInfo.DBInfo.ICON + " TEXT,"
//                + EucInfo.DBInfo.SUBJECT + " TEXT," + EucInfo.DBInfo.DESC + " TEXT,"
//                + EucInfo.DBInfo.NOTICE + " TEXT ," + EucInfo.DBInfo.OWNER
//                + " INTEGER DEFAULT 0);");
//
//        db.execSQL("CREATE TABLE " + DBInfo.TABLE_CHAT_RECORD + " (" + EucInfo.DBInfo._ID
//                + " INTEGER PRIMARY KEY," + EucInfo.DBInfo.ROOM_ID + " INTEGER,"
//                + EucInfo.DBInfo.CONTACT_ID + " INTEGER," + EucInfo.DBInfo.CONTENT
//                + " TEXT," + EucInfo.DBInfo.CONTENT_TYPE + " INTEGER,"
//                + EucInfo.DBInfo.MESSAGE_TYPE + " INTEGER, " + EucInfo.DBInfo.SEND_DATE
//                + " INTEGER ," + EucInfo.DBInfo.EID + " TEXT ," + EucInfo.DBInfo.HAS_EMOJI
//                + " INTEGER DEFAULT 0);");
//
//        db.execSQL("CREATE TABLE " + DBInfo.TABLE_CHAT_OCCUPANT + " (" + EucInfo.DBInfo._ID
//                + " INTEGER PRIMARY KEY," + EucInfo.DBInfo.ROOM_ID + " INTEGER,"
//                + EucInfo.DBInfo.OCCUPANT_ID + " TEXT," + EucInfo.DBInfo.OCCUPANT_NAME
//                + " TEXT," + EucInfo.DBInfo.ICON + " TEXT," + EucInfo.DBInfo.ME
//                + " INTEGER DEFAULT 0," + EucInfo.DBInfo.AGE + " INTEGER,"
//                + EucInfo.DBInfo.MOOD + " TEXT," + EucInfo.DBInfo.CHARACTERISTICS
//                + " TEXT );");
//
//
//
//        db.execSQL("CREATE TABLE " + DBInfo.TABLE_CHAT_PRIVATE + " (" + EucInfo.DBInfo._ID
//                + " INTEGER PRIMARY KEY," + EucInfo.DBInfo.THREAD_ID + " INTEGER,"
//                + EucInfo.DBInfo.SUBJECT + " TEXT," + EucInfo.DBInfo.BODY + " TEXT,"
//                + EucInfo.DBInfo.CONTENT_TYPE + " INTEGER," + EucInfo.DBInfo.MESSAGE_TYPE
//                + " INTEGER, " + EucInfo.DBInfo.SEEN + " INTEGER DEFAULT 0,"
//                + EucInfo.DBInfo.DATE + " INTEGER ," + EucInfo.DBInfo.EID + " TEXT ,"
//                + EucInfo.DBInfo.HAS_EMOJI + " INTEGER DEFAULT 0);");
//
//
//        db.execSQL("CREATE TABLE " + DBInfo.TABLE_CHAT_THREAD + " (" + EucInfo.DBInfo._ID
//                + " INTEGER PRIMARY KEY," + EucInfo.DBInfo.ROOM_ID + " INTEGER,"
//                + EucInfo.DBInfo.CONTACT_ID + " INTEGER," + EucInfo.DBInfo.DATE
//                + " INTEGER," + EucInfo.DBInfo.MESSAGE_COUNT + " INTEGER);");
//
//
//        // 创建索引
//        db.execSQL("CREATE INDEX IF NOT EXISTS  index_record ON chat_record(room_id,contact_id)");
//        db.execSQL("CREATE INDEX IF NOT EXISTS  index_record_contact ON chat_record(contact_id)");
//        db.execSQL("CREATE INDEX IF NOT EXISTS  index_record_eid ON chat_record(eid)");
//    }
//
//
//    private void initEucData(SQLiteDatabase db) {
//
//        db.execSQL("INSERT INTO chat_room(euc_id) values('na-sys-z-999-00-00000000@chatroom.xmpp.espier.mobi')");
//
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int currentVersion) {
//        Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + currentVersion + ".");
//
//        switch (oldVersion) {
//            case 53:
//                if (currentVersion <= 53) {
//                    return;
//                }
//
//                db.beginTransaction();
//                try {
//                    upgradeDatabaseToVersion54(db);
//                    db.setTransactionSuccessful();
//                } catch (Throwable ex) {
//                    Log.e(TAG, ex.getMessage(), ex);
//                    break;
//                } finally {
//                    db.endTransaction();
//                }
//            case 54:
//                if (currentVersion <= 54) {
//                    return;
//                }
//
//                db.beginTransaction();
//                try {
//                    upgradeDatabaseToVersion55(db);
//                    db.setTransactionSuccessful();
//                } catch (Throwable ex) {
//                    Log.e(TAG, ex.getMessage(), ex);
//                    break;
//                } finally {
//                    db.endTransaction();
//                }
//            case 55:
//                if (currentVersion <= 55) {
//                    return;
//                }
//
//                db.beginTransaction();
//                try {
//                    upgradeDatabaseToVersion56(db);
//                    db.setTransactionSuccessful();
//                } catch (Throwable ex) {
//                    Log.e(TAG, ex.getMessage(), ex);
//                    break;
//                } finally {
//                    db.endTransaction();
//                }
//            case 56:
//                if (currentVersion <= 56) {
//                    return;
//                }
//
//                db.beginTransaction();
//                try {
//                    upgradeDatabaseToVersion57(db);
//                    db.setTransactionSuccessful();
//                } catch (Throwable ex) {
//                    Log.e(TAG, ex.getMessage(), ex);
//                    break;
//                } finally {
//                    db.endTransaction();
//                }
//            case 57:
//                if (currentVersion <= 57) {
//                    return;
//                }
//
//                db.beginTransaction();
//                try {
//                    upgradeDatabaseToVersion58(db);
//                    db.setTransactionSuccessful();
//                } catch (Throwable ex) {
//                    Log.e(TAG, ex.getMessage(), ex);
//                    break;
//                } finally {
//                    db.endTransaction();
//                }
//
//            case 58:
//                if (currentVersion <= 58) {
//                    return;
//                }
//
//                db.beginTransaction();
//                try {
//                    upgradeDatabaseToVersion59(db);
//                    db.setTransactionSuccessful();
//                } catch (Throwable ex) {
//                    Log.e(TAG, ex.getMessage(), ex);
//                    break;
//                } finally {
//                    db.endTransaction();
//                }
//
//            case 59:
//                if (currentVersion <= 59) {
//                    return;
//                }
//
//                db.beginTransaction();
//                try {
//                    upgradeDatabaseToVersion60(db);
//                    db.setTransactionSuccessful();
//                } catch (Throwable ex) {
//                    Log.e(TAG, ex.getMessage(), ex);
//                    break;
//                } finally {
//                    db.endTransaction();
//                }
//
//            case 60:
//                if (currentVersion <= 60) {
//                    return;
//                }
//
//                db.beginTransaction();
//                try {
//                    upgradeDatabaseToVersion61(db);
//                    db.setTransactionSuccessful();
//                } catch (Throwable ex) {
//                    Log.e(TAG, ex.getMessage(), ex);
//                    break;
//                } finally {
//                    db.endTransaction();
//                }
//            case 61:
//                if (currentVersion <= 61) {
//                    return;
//                }
//
//                db.beginTransaction();
//                try {
//                    upgradeDatabaseToVersion62(db);
//                    db.setTransactionSuccessful();
//                } catch (Throwable ex) {
//                    Log.e(TAG, ex.getMessage(), ex);
//                    break;
//                } finally {
//                    db.endTransaction();
//                }
//            case 62:
//                if (currentVersion <= 62) {
//                    return;
//                }
//
//                db.beginTransaction();
//                try {
//                    upgradeDatabaseToVersion63(db);
//                    db.setTransactionSuccessful();
//                } catch (Throwable ex) {
//                    Log.e(TAG, ex.getMessage(), ex);
//                    break;
//                } finally {
//                    db.endTransaction();
//                }
//            case 63:
//                if (currentVersion <= 63) {
//                    return;
//                }
//
//                db.beginTransaction();
//                try {
//                    upgradeDatabaseToVersion64(db);
//                    db.setTransactionSuccessful();
//                } catch (Throwable ex) {
//                    Log.e(TAG, ex.getMessage(), ex);
//                    break;
//                } finally {
//                    db.endTransaction();
//                }
//            case 64:
//                if (currentVersion <= 64) {
//                    return;
//                }
//
//                db.beginTransaction();
//                try {
//                    upgradeDatabaseToVersion65(db);
//                    db.setTransactionSuccessful();
//                } catch (Throwable ex) {
//                    Log.e(TAG, ex.getMessage(), ex);
//                    break;
//                } finally {
//                    db.endTransaction();
//                }
//            case 65:
//                if (currentVersion <= 65) {
//                    return;
//                }
//
//                db.beginTransaction();
//                try {
//                    upgradeDatabaseToVersion66(db);
//                    db.setTransactionSuccessful();
//                } catch (Throwable ex) {
//                    Log.e(TAG, ex.getMessage(), ex);
//                    break;
//                } finally {
//                    db.endTransaction();
//                }
//
//            case 66:
//                if (currentVersion <= 66) {
//                    return;
//                }
//
//                db.beginTransaction();
//                try {
//                    upgradeDatabaseToVersion67(db);
//                    db.setTransactionSuccessful();
//                } catch (Throwable ex) {
//                    Log.e(TAG, ex.getMessage(), ex);
//                    break;
//                } finally {
//                    db.endTransaction();
//                }
//            case 67:
//                if (currentVersion <= 67) {
//                    return;
//                }
//
//                db.beginTransaction();
//                try {
//                    upgradeDatabaseToVersion68(db);
//                    db.setTransactionSuccessful();
//                } catch (Throwable ex) {
//                    Log.e(TAG, ex.getMessage(), ex);
//                    break;
//                } finally {
//                    db.endTransaction();
//                }
//                return;
//        }
//
//
//
//        Log.e(TAG, "Destroying all old data.");
//        dropAll(db);
//        onCreate(db);
//    }
//
//    private void dropAll(SQLiteDatabase db) {
//        // Clean the database out in order to start over from scratch.
//        // We don't need to drop our triggers here because SQLite automatically
//        // drops a trigger when its attached database is dropped.
//        db.execSQL("DROP TABLE IF EXISTS canonical_addresses");
//        db.execSQL("DROP TABLE IF EXISTS threads");
//        db.execSQL("DROP TABLE IF EXISTS " + MmsSmsProvider.TABLE_PENDING_MSG);
//        db.execSQL("DROP TABLE IF EXISTS sms");
//        db.execSQL("DROP TABLE IF EXISTS raw");
//        db.execSQL("DROP TABLE IF EXISTS attachments");
//        db.execSQL("DROP TABLE IF EXISTS thread_ids");
//        db.execSQL("DROP TABLE IF EXISTS sr_pending");
//        db.execSQL("DROP TABLE IF EXISTS words");
//        db.execSQL("DROP TABLE IF EXISTS " + MmsProvider.TABLE_PDU + ";");
//        db.execSQL("DROP TABLE IF EXISTS " + MmsProvider.TABLE_ADDR + ";");
//        db.execSQL("DROP TABLE IF EXISTS " + MmsProvider.TABLE_PART + ";");
//        db.execSQL("DROP TABLE IF EXISTS " + MmsProvider.TABLE_RATE + ";");
//        db.execSQL("DROP TABLE IF EXISTS " + MmsProvider.TABLE_DRM + ";");
//        db.execSQL("DROP TABLE IF EXISTS " + Emessage.TABLE_EMESSAGE_DATA + ";");
//        db.execSQL("DROP TABLE IF EXISTS " + EmessageNoUserNumbers.TABLE_NAME + ";");
//        db.execSQL("DROP TABLE IF EXISTS " + EspierProvider.TABLE_USERS + ";");
//        db.execSQL("DROP TABLE IF EXISTS " + DBInfo.TABLE_CHAT_ROOM + ";");
//        db.execSQL("DROP TABLE IF EXISTS " + DBInfo.TABLE_CHAT_RECORD + ";");
//        db.execSQL("DROP TABLE IF EXISTS " + DBInfo.TABLE_CHAT_OCCUPANT + ";");
//        db.execSQL("DROP TABLE IF EXISTS " + DBInfo.TABLE_CHAT_PRIVATE + ";");
//        db.execSQL("DROP TABLE IF EXISTS " + DBInfo.TABLE_CHAT_THREAD + ";");
//
//        db.execSQL("DROP TABLE IF EXISTS  threads_common ;");
//        db.execSQL("DROP TABLE IF EXISTS  emessage_contacts_filter ;");
//        db.execSQL("DROP TABLE IF EXISTS  espier_timer ;");
//
//
//    }
//
//
//    private void upgradeDatabaseToVersion54(SQLiteDatabase db) {
//
//        db.execSQL("ALTER TABLE emessage_data add COLUMN seq INTEGER DEFAULT -1");
//        db.execSQL("ALTER TABLE emessage_data add COLUMN ct TEXT ");
//        db.execSQL("ALTER TABLE emessage_data add COLUMN fp TEXT ");
//        db.execSQL("ALTER TABLE emessage_data add COLUMN content TEXT ");
//        db.execSQL("ALTER TABLE emessage_data add COLUMN size INTEGER ");
//        db.execSQL("ALTER TABLE emessage_data add COLUMN type INTEGER DEFAULT -1");
//        db.execSQL("ALTER TABLE emessage_data add COLUMN version INTEGER ");
//
//        db.execSQL("ALTER TABLE sms add COLUMN emessage_status INTEGER DEFAULT 1  ");
//
//
//        File files = new File(Emessage.getFilePath(mContext));
//        if (!files.isDirectory()) {
//            files.mkdir();
//        }
//        files.setReadable(true);
//        files.setWritable(true);
//
//        Cursor cursor = null;
//        try {
//            cursor = db.rawQuery("select * from emessage_data ", null);
//            while (cursor.moveToNext()) {
//                long id = cursor.getLong(cursor.getColumnIndex(EmessageData._ID));
//                long sms_id = cursor.getLong(cursor.getColumnIndex(EmessageData.SMS_ID));
//                byte data[] = cursor.getBlob(cursor.getColumnIndex(EmessageData.DATA));
//
//
//                File file =
//                        new File(Emessage.getFilePath(mContext) + "/" + id + "_" + sms_id + "_"
//                                + System.currentTimeMillis() + ".png");
//
//                if (file.exists()) {
//                    file.delete();
//                }
//
//                FileOutputStream fos = null;
//                try {
//                    file.createNewFile();
//                    fos = new FileOutputStream(file);
//                    fos.write(data);
//                    fos.flush();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    if (fos != null) {
//                        fos.close();
//                    }
//                }
//
//                ContentValues values = new ContentValues();
//                values.put(EmessageData.CONTENT_TYPE, "image/png");
//                values.put(EmessageData.FILE_PATH, file.getAbsolutePath());
//                // values.put(EmessageData.CONTENT, value);
//                values.put(EmessageData.SIZE, file.length());
//                db.update(Emessage.TABLE_EMESSAGE_DATA, values, EmessageData._ID + " = " + id, null);
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//        }
//
//    }
//
//
//    private void upgradeDatabaseToVersion55(SQLiteDatabase db) {
//
//        db.execSQL("ALTER TABLE sms add COLUMN emessage_date INTEGER DEFAULT 0");
//
//    }
//
//    private void upgradeDatabaseToVersion56(SQLiteDatabase db) {
//
//        db.execSQL("ALTER TABLE emessage_data add COLUMN user_status INTEGER ");
//        db.execSQL("ALTER TABLE emessage_data add COLUMN file_name TEXT ");
//        db.execSQL("ALTER TABLE emessage_data add COLUMN file_digest TEXT ");
//        db.execSQL("ALTER TABLE emessage_data add COLUMN file_status INTEGER ");
//        db.execSQL("ALTER TABLE emessage_data add COLUMN access_url TEXT ");
//        db.execSQL("ALTER TABLE emessage_data add COLUMN access_passwd TEXT ");
//        db.execSQL("ALTER TABLE emessage_data add COLUMN file_download_pos INTEGER ");
//        db.execSQL("ALTER TABLE emessage_data add COLUMN file_download_percent INTEGER ");
//        db.execSQL("ALTER TABLE emessage_data add COLUMN file_begin_date INTEGER ");
//        db.execSQL("ALTER TABLE emessage_data add COLUMN file_finish_date INTEGER ");
//
//
//    }
//
//
//    private void upgradeDatabaseToVersion57(SQLiteDatabase db) {
//
//        db.execSQL(CREATE_TRIGGER_DELETE_EMESSAGE_DATA_ON_DELETE_SMS);
//
//    }
//
//    private void upgradeDatabaseToVersion58(SQLiteDatabase db) {
//
//        db.execSQL("ALTER TABLE emessage_data add COLUMN voice_time INTEGER ");
//        db.execSQL("ALTER TABLE emessage_data add COLUMN voice_play INTEGER ");
//
//    }
//
//    private void upgradeDatabaseToVersion59(SQLiteDatabase db) {
//
//        db.execSQL("ALTER TABLE sms add COLUMN secret_type INTEGER DEFAULT 0 ");
//        db.execSQL("ALTER TABLE sms add COLUMN sms_snippet TEXT ");
//        db.execSQL("ALTER TABLE sms add COLUMN privacy INTEGER DEFAULT 0 ");
//
//
//
//
//
//        db.execSQL("update sms set sms_snippet = " + Sms.BODY);
//
//
//
//    }
//
//    private void upgradeDatabaseToVersion60(SQLiteDatabase db) {
//
//        /**
//         * threads for secret
//         */
//        db.execSQL("CREATE TABLE threads_common (" + Threads._ID + " INTEGER PRIMARY KEY,"
//                + Threads.DATE + " INTEGER DEFAULT 0," + Threads.MESSAGE_COUNT
//                + " INTEGER DEFAULT 0," + Threads.RECIPIENT_IDS + " TEXT," + Threads.SNIPPET
//                + " TEXT," + Threads.SNIPPET_CHARSET + " INTEGER DEFAULT 0," + Threads.READ
//                + " INTEGER DEFAULT 1," + Threads.TYPE + " INTEGER DEFAULT 0," + Threads.ERROR
//                + " INTEGER DEFAULT 0," + Threads.HAS_ATTACHMENT + " INTEGER DEFAULT 0,"
//                + Threads.FAV + " INTEGER DEFAULT 0);");
//
//
//        db.execSQL("DROP TRIGGER  IF EXISTS pdu_update_thread_on_delete");
//        // Update threads table whenever a message in pdu is deleted
//        db.execSQL("CREATE TRIGGER pdu_update_thread_on_delete " + "AFTER DELETE ON pdu "
//                + "BEGIN " + "  UPDATE threads SET " + "     date = (strftime('%s','now') * 1000)"
//                + "  WHERE threads._id = old." + Mms.THREAD_ID + "; " + UPDATE_THREAD_COUNT_ON_OLD
//                + UPDATE_THREAD_SNIPPET_SNIPPET_CS_ON_DELETE + "END;");
//
//
//        db.execSQL("INSERT INTO threads_common select * from threads;");
//
//
//        db.execSQL("DROP TRIGGER  IF EXISTS thread_update_thread_common_snippet_on_update");
//        db.execSQL("DROP TRIGGER  IF EXISTS thread_update_thread_common_count_on_update");
//        db.execSQL("DROP TRIGGER  IF EXISTS thread_update_thread_common_status_on_update");
//        db.execSQL("DROP TRIGGER  IF EXISTS thread_update_thread_common_read_on_update");
//        db.execSQL("DROP TRIGGER  IF EXISTS thread_update_thread_common_on_insert");
//        db.execSQL("DROP TRIGGER  IF EXISTS thread_update_thread_common_on_delete");
//
//
//
//
//
//        db.execSQL(" CREATE TRIGGER thread_update_thread_common_status_on_update "
//                + " AFTER UPDATE OF date,error,has_attachment,is_favorite ON threads "
//                + " BEGIN  "
//                + " UPDATE threads_common SET date=NEW.date , error =  NEW.error , has_attachment= NEW.has_attachment , is_favorite=NEW.is_favorite "
//                + " WHERE threads_common._id = NEW._id; END; ");
//
//        db.execSQL(" CREATE TRIGGER thread_update_thread_common_read_on_update "
//                + " AFTER UPDATE OF read ON threads "
//                + " BEGIN  "
//                + " UPDATE threads_common SET read =   "
//                + " CASE ( "
//                + " (SELECT COUNT(*)  FROM sms  "
//                + " WHERE read = 0  AND  privacy=0  AND thread_id = threads_common._id)  + "
//                + " (SELECT COUNT(*)  FROM pdu   WHERE read = 0    AND thread_id = threads_common._id "
//                + " AND (m_type=132 OR m_type=130 OR m_type=128)) " + " ) "
//                + " WHEN 0 THEN 1      ELSE 0    END   " + " WHERE threads_common._id = new._id; "
//                + " END; ");
//
//
//        db.execSQL(" CREATE TRIGGER thread_update_thread_common_on_insert "
//                + " AFTER INSERT ON threads  BEGIN  "
//                + " INSERT INTO  threads_common(_id,date,recipient_ids,message_count,type)  "
//                + " VALUES(NEW._id,NEW.date,NEW.recipient_ids,NEW.message_count,NEW.type);  END; ");
//
//
//        db.execSQL(" CREATE TRIGGER thread_update_thread_common_on_delete "
//                + " AFTER DELETE ON threads "
//                + " BEGIN  DELETE from threads_common WHERE _id = old._id; END; ");
//
//
//
//    }
//
//    private void upgradeDatabaseToVersion61(SQLiteDatabase db) {
//
//        db.execSQL("ALTER TABLE sms add COLUMN emessage_online INTEGER DEFAULT 0 ");
//
//    }
//
//    private void upgradeDatabaseToVersion62(SQLiteDatabase db) {
//
//
//        db.execSQL("CREATE TABLE emessage_contacts_filter (" + "_id INTEGER PRIMARY KEY,"
//                + "contacts_id TEXT ," + "type INTEGER DEFAULT 0);");
//
//        db.execSQL("CREATE TABLE espier_timer (" + "_id INTEGER PRIMARY KEY," + "endTime LONG ,"
//                + "action TEXT ," + "append TEXT ," + "type INTEGER DEFAULT 0, key_index TEXT );");
//    }
//
//    private void upgradeDatabaseToVersion63(SQLiteDatabase db) {
//
//        // db.execSQL(" CREATE TRIGGER thread_common_delete_count_on_update "
//        // + " AFTER UPDATE OF  message_count ON threads_common "
//        // + " BEGIN  DELETE from threads_common WHERE _id = old._id AND message_count=0 AND "
//        // + "(( select count(*)  from sms where privacy=1 AND  thread_id=_id )>0); END; ");
//    }
//
//    private void upgradeDatabaseToVersion64(SQLiteDatabase db) {
//
//        db.execSQL("CREATE INDEX index_threads ON threads(date)");
//        db.execSQL("CREATE INDEX index_threads_common ON threads_common(date)");
//        db.execSQL("CREATE INDEX index_sms ON sms(thread_id,address)");
//
//    }
//
//    private void upgradeDatabaseToVersion65(SQLiteDatabase db) {
//
//        // createEucTables(db);
//        db.execSQL("DROP TRIGGER  IF EXISTS thread_common_delete_count_on_update");
//        db.execSQL(" CREATE TRIGGER thread_common_delete_count_on_update "
//                + " AFTER UPDATE OF  message_count ON threads_common "
//                + " BEGIN  DELETE from threads_common WHERE _id = old._id AND message_count=0 AND "
//                + "(( select count(*)  from sms where privacy=1 AND  thread_id = old._id )>0); END; ");
//
//        db.execSQL("DROP TRIGGER  IF EXISTS thread_update_thread_common_count_on_update");
//
//        db.execSQL(" CREATE TRIGGER thread_update_thread_common_count_on_update "
//                + " AFTER UPDATE OF message_count ON threads "
//                + " BEGIN "
//                + " REPLACE INTO  threads_common(_id,date,recipient_ids,type,snippet,snippet_cs,is_favorite)  "
//                + "VALUES(NEW._id,NEW.date,NEW.recipient_ids,NEW.type,NEW.snippet,NEW.snippet_cs,NEW.is_favorite); "
//                + " UPDATE threads_common SET message_count = "
//                + " (SELECT COUNT(sms._id) FROM sms LEFT JOIN threads_common ON threads_common._id = thread_id "
//                + " WHERE thread_id = NEW._id   AND sms.privacy=0  AND sms.type != 3) +  "
//                + " (SELECT COUNT(pdu._id) FROM pdu LEFT JOIN threads_common  ON threads_common._id = thread_id "
//                + " WHERE thread_id = NEW._id   AND (m_type=132 OR m_type=130 OR m_type=128)   "
//                + " AND msg_box != 3)  WHERE threads_common._id = NEW._id;  END; ");
//    }
//
//    private void upgradeDatabaseToVersion66(SQLiteDatabase db) {
//
//        // createEucTables(db);
//        db.execSQL("ALTER TABLE emessage_data add COLUMN euc_record_id INTEGER");
//
//        db.execSQL("ALTER TABLE sms add COLUMN has_emoji INTEGER DEFAULT 0");
//        db.execSQL("ALTER TABLE threads add COLUMN has_emoji INTEGER DEFAULT 0");
//        db.execSQL("ALTER TABLE threads_common add COLUMN has_emoji INTEGER DEFAULT 0 ");
//
//        db.execSQL("DROP TRIGGER  IF EXISTS thread_update_thread_common_snippet_on_update");
//
//        db.execSQL(" CREATE TRIGGER thread_update_thread_common_snippet_on_update "
//                + " AFTER UPDATE OF message_count,snippet,snippet_cs ON threads "
//                + " BEGIN "
//                + " UPDATE threads_common SET snippet = "
//                + " (SELECT snippet FROM  (SELECT date * 1000 AS date, sub AS snippet, thread_id FROM pdu "
//                + " UNION SELECT date, sms_snippet AS snippet, thread_id FROM sms  WHERE privacy=0 ) "
//                + " WHERE thread_id = NEW._id ORDER BY date DESC LIMIT 1)    "
//                + " WHERE threads_common._id = NEW._id;   "
//                + " UPDATE threads_common SET snippet_cs =  (SELECT snippet_cs FROM   "
//                + " (SELECT date * 1000 AS date, sub_cs AS snippet_cs, thread_id FROM pdu "
//                + " UNION  SELECT date, 0 AS snippet_cs, thread_id FROM sms WHERE privacy=0 ) "
//                + " WHERE thread_id = NEW._id ORDER BY date DESC LIMIT 1) "
//                + " WHERE threads_common._id = NEW._id;"
//                + " UPDATE threads_common SET has_emoji = (SELECT has_emoji FROM "
//                + " (SELECT date * 1000 AS date, 1 AS has_emoji, thread_id FROM pdu "
//                + " UNION  SELECT date, has_emoji, thread_id FROM sms WHERE privacy=0 ) "
//                + " WHERE thread_id = NEW._id ORDER BY date DESC LIMIT 1) "
//                + " WHERE threads_common._id = NEW._id; " + "END; ");
//
//        db.execSQL("DROP TRIGGER  IF EXISTS sms_update_thread_on_insert");
//        db.execSQL("DROP TRIGGER  IF EXISTS sms_update_thread_date_subject_on_update");
//
//        // Updates threads table whenever a message is added to sms.
//        db.execSQL("CREATE TRIGGER sms_update_thread_on_insert AFTER INSERT ON sms "
//                + SMS_UPDATE_THREAD_DATE_SNIPPET_COUNT_ON_UPDATE);
//
//
//        // Updates threads table whenever a message in sms is updated.
//        db.execSQL("CREATE TRIGGER sms_update_thread_date_subject_on_update AFTER" + "  UPDATE OF "
//                + Sms.DATE + ", " + Sms.BODY + ", " + Sms.TYPE + "  ON sms "
//                + SMS_UPDATE_THREAD_DATE_SNIPPET_COUNT_ON_UPDATE);
//
//        db.execSQL("DROP TRIGGER  IF EXISTS pdu_update_thread_on_insert");
//        db.execSQL("DROP TRIGGER  IF EXISTS pdu_update_thread_date_subject_on_update");
//
//        db.execSQL("CREATE TRIGGER pdu_update_thread_on_insert AFTER INSERT ON "
//                + MmsProvider.TABLE_PDU + " " + PDU_UPDATE_THREAD_CONSTRAINTS
//                + PDU_UPDATE_THREAD_DATE_SNIPPET_COUNT_ON_UPDATE);
//
//        db.execSQL("CREATE TRIGGER pdu_update_thread_date_subject_on_update AFTER" + "  UPDATE OF "
//                + Mms.DATE + ", " + Mms.SUBJECT + ", " + Mms.MESSAGE_BOX + "  ON "
//                + MmsProvider.TABLE_PDU + " " + PDU_UPDATE_THREAD_CONSTRAINTS
//                + PDU_UPDATE_THREAD_DATE_SNIPPET_COUNT_ON_UPDATE);
//
//    }
//
//
//    private void upgradeDatabaseToVersion67(SQLiteDatabase db) {
//
//        // db.execSQL("CREATE TABLE " + EucInfo.TABLE_CHAT_PRIVATE + " (" + EucInfo.ChatPrivate._ID
//        // + " INTEGER PRIMARY KEY," + EucInfo.ChatPrivate.THREAD_ID + " INTEGER,"
//        // + EucInfo.ChatPrivate.EUC_ID + " TEXT," + EucInfo.ChatPrivate.OCCUPANT_ID
//        // + " TEXT," + EucInfo.ChatPrivate.SUBJECT + " TEXT," + EucInfo.ChatPrivate.BODY
//        // + " TEXT," + EucInfo.ChatPrivate.CONTENT_TYPE + " INTEGER,"
//        // + EucInfo.ChatPrivate.MESSAGE_TYPE + " INTEGER, " + EucInfo.ChatPrivate.SNIPPET
//        // + " TEXT ," + EucInfo.ChatPrivate.SEEN + " INTEGER DEFAULT 0,"
//        // + EucInfo.ChatPrivate.DATE + " INTEGER ," + EucInfo.ChatPrivate.EID + " TEXT ,"
//        // + EucInfo.ChatPrivate.HAS_EMOJI + " INTEGER DEFAULT 2);");
//        //
//        //
//        // db.execSQL("CREATE TABLE " + EucInfo.TABLE_CHAT_THREAD + " (" + EucInfo.ChatThread._ID
//        // + " INTEGER PRIMARY KEY," + EucInfo.ChatThread.EUC_ID + " TEXT,"
//        // + EucInfo.ChatThread.OCCUPANT_ID + " TEXT," + EucInfo.ChatThread.DATE + " INTEGER,"
//        // + EucInfo.ChatThread.MESSAGE_COUNT + " INTEGER," + EucInfo.ChatThread.SNIPPET
//        // + " TEXT, " + EucInfo.ChatThread.SEEN + " INTEGER DEFAULT 0,"
//        // + EucInfo.ChatThread.HAS_EMOJI + " INTEGER DEFAULT 2);");
//
//    }
//
//
//    private void upgradeDatabaseToVersion68(SQLiteDatabase db) {
//
//        db.execSQL("DROP INDEX index_record_date ");
//        db.execSQL("DROP INDEX index_record_eid ");
//
//        db.execSQL("DROP TABLE IF EXISTS " + DBInfo.TABLE_CHAT_ROOM + ";");
//        db.execSQL("DROP TABLE IF EXISTS " + DBInfo.TABLE_CHAT_RECORD + ";");
//        db.execSQL("DROP TABLE IF EXISTS " + DBInfo.TABLE_CHAT_OCCUPANT + ";");
//        db.execSQL("DROP TABLE IF EXISTS " + DBInfo.TABLE_CHAT_PRIVATE + ";");
//        db.execSQL("DROP TABLE IF EXISTS " + DBInfo.TABLE_CHAT_THREAD + ";");
//
//        createEucTables(db);
//        initEucData(db);
//
//    }
//
// }
