package com.xian.xingyu.util;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class BaseUtil {

    private static final String TAG = "BaseUtil";

    /**
     * 根据一个网络连接(String)获取bitmap图像
     *
     * @param imageUri
     * @return
     * @throws MalformedURLException
     */
    public static Bitmap getbitmap(String imageUri) {
        Log.v(TAG, "getbitmap:" + imageUri);
        // 显示网络上的图片
        Bitmap bitmap = null;
        try {
            URL myFileUrl = new URL(imageUri);
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();

            Log.v(TAG, "image download finished." + imageUri);
        } catch (IOException e) {
            e.printStackTrace();
            Log.v(TAG, "getbitmap bmp fail---");
            return null;
        }
        return bitmap;
    }

    public static void saveFileToData(Context context, String name, String uri) {
        Log.v(TAG, "saveFileToData:" + uri);
        try {
            URL myFileUrl = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            FileOutputStream fos = context.openFileOutput(name, Context.MODE_PRIVATE);

            byte[] buffer = new byte[1024];
            int len = 0;
            len = is.read(buffer);
            while (len != -1) {
                fos.write(buffer, 0, len);
                len = is.read(buffer);
            }



            fos.close();
            is.close();

            Log.v(TAG, "image download finished." + uri);
        } catch (IOException e) {
            e.printStackTrace();
            Log.v(TAG, "getbitmap bmp fail---");
        }
    }


    public static byte[] getImageData(Context context, String uri) {
        Log.v(TAG, "getImageData:" + uri);
        ByteArrayOutputStream baos = null;
        InputStream is = null;
        try {
            URL myFileUrl = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();

            baos = new ByteArrayOutputStream();
            is = conn.getInputStream();

            byte[] buffer = new byte[1024];
            int len = 0;
            len = is.read(buffer);
            while (len != -1) {
                baos.write(buffer, 0, len);
                len = is.read(buffer);
            }
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }


    public static Bitmap bytes2Bimap(byte[] b) {
        if (b != null && b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }
}
