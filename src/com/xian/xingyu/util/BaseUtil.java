
package com.xian.xingyu.util;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.xian.xingyu.db.XianDataBaseHelper;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class BaseUtil {

    private static final String TAG = "BaseUtil";

    public static final int FILE_TYPE_NATIVE = 0;
    public static final int FILE_TYPE_THUMB = 1;

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

    public static void cleanLoginConfig(Configs config) {
        Editor editor = config.getEditor();
        editor.putString(Configs.KEY, "");
        editor.putString(Configs.TOKEN, "");
        editor.putLong(Configs.AUTH_TIME, 0);
        editor.putInt(Configs.TYPE, Configs.TYPE_DEFAULT);
        editor.putInt(Configs.INFO_STATUS, Configs.INFO_STATUS_DEFAULT);
        editor.commit();
    }

    public static String selectImageByUri(Context context, Uri selectedImage) {

        if (selectedImage == null) {
            return null;
        }
        String[] filePathColumn = {
                MediaStore.Images.Media.DATA
        };
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(selectedImage, filePathColumn, null,
                    null, null);
            if (cursor == null)
                return null;
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                return picturePath;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return null;
    }

    public static void copyToSDCard(Context context) {
        File file = context.getDatabasePath(XianDataBaseHelper.DATABASE_NAME);
        if (file != null) {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File sdCardDir = Environment.getExternalStorageDirectory();// 获取SDCard目录
                File saveFile = new File(sdCardDir, file.getName());
                if (saveFile.exists()) {
                    saveFile.delete();
                }

                FileInputStream fisFrom = null;
                FileOutputStream fosTo = null;
                try {
                    fisFrom = new FileInputStream(file);
                    fosTo = new FileOutputStream(saveFile);
                    byte bt[] = new byte[1024];
                    int c;
                    while ((c = fisFrom.read(bt)) > 0) {
                        fosTo.write(bt, 0, c); // 将内容写到新文件当中
                    }
                    Toast.makeText(context, "拷贝数据完毕", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(context, "拷贝数据出错", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                } finally {
                    if (fisFrom != null) {
                        try {
                            fisFrom.close();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    if (fosTo != null) {
                        try {
                            fosTo.close();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            }

        } else {
            Toast.makeText(context, "没有数据库文件", Toast.LENGTH_SHORT).show();
        }
    }

    public static File getFilePath(Context context, int type) {
        File dir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (dir == null) {
            dir = context.getFilesDir();
        }
        File file = new File(dir, type + "-" + System.currentTimeMillis() + "-"
                + Math.round(Math.random() * 10));
        return file;
    }

    public static Uri fromFile(File file) {
        if (file == null) {
            throw new NullPointerException("file");
        }

        return Uri.fromFile(file);
    }

    // Bitmap对象保存味图片文件
    public static File saveBitmapFile(Context context, Bitmap bitmap) {
        BufferedOutputStream bos = null;
        File file = getFilePath(context, FILE_TYPE_THUMB);
        try {
            bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

    public static void copyFile(File oldFile, File newFile) {

        if (oldFile == null || !oldFile.exists())
            return;
        if (newFile == null)
            return;

        int length = 0;
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(oldFile);
            fos = new FileOutputStream(newFile);
            byte[] buffer = new byte[1024];
            while ((length = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }

    public static Bitmap getBitmapFromName(Context context, String name) {
        String path = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath()
                + "/" + name;

        BitmapFactory.Options factoryOptions = new BitmapFactory.Options();

        factoryOptions.inJustDecodeBounds = false;
        factoryOptions.inPurgeable = true;


        return BitmapFactory.decodeFile(path, factoryOptions);
    }

}
