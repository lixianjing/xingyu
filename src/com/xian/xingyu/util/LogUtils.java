package com.xian.xingyu.util;

import android.util.Log;

public class LogUtils {

	private static final String TAG = "lmf";
	private static final String SLIP = ">>>>>>";

	public static final int DEBUG = 1;
	public static final int INFO = 2;
	public static final int WARN = 3;
	public static final int ERROR = 4;

	public static void log(Object... objs) {
		log(ERROR, TAG, objs);
	}

	public static void log(int mode, String tag, Object... objs) {
		switch (mode) {
		case DEBUG:
			Log.d(tag, genStrings(objs));
			break;
		case INFO:
			Log.i(tag, genStrings(objs));
			break;
		case WARN:
			Log.w(tag, genStrings(objs));
			break;
		case ERROR:
		default:
			Log.e(tag, genStrings(objs));
			break;
		}
	}

	private static String genStrings(Object... objs) {
		if (objs.length == 0)
			return "";
		StringBuilder builder = new StringBuilder();
		for (Object ob : objs) {
			builder.append(ob.toString());
			builder.append(SLIP);
		}
		return builder.toString();
	}
	

}
