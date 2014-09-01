
package com.xian.xingyu.cache;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

public class ImageCacheControler {

    private static ImageCacheControler mInstance = null;

    private final DrawableCache mCache;

    private ImageCacheControler() {
        mCache = DrawableCache.getInstance();
    }

    public void release() {
        mInstance = null;
        System.gc();
    }

    /**
     * 通过此方法来获取NativeImageLoader的实例
     *
     * @return
     */
    public static ImageCacheControler getInstance() {
        if (mInstance == null) {
            createInstance();
        }
        return mInstance;
    }

    private static synchronized void createInstance() {
        if (mInstance == null) {
            mInstance = new ImageCacheControler();
        }
    }

    public Drawable getDrawableCache(Context context, String key) {
        Log.e("lmf", "?>>>>>>>>>getDrawableCache>>>>>>>>" + key);
        Drawable drawable = mCache.getDrawableCache(key);
        if (drawable == null) {
            drawable = getDrawableFromFile(context, key);
            if (drawable == null) {
                // get drawable from net
                return null;
            }
            mCache.addDrawableCache(key, drawable);
        }
        return drawable;
    }

    public Drawable getDrawableFromFile(Context context, String name) {
        String path = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath()
                + "/" + name;
        return Drawable.createFromPath(path);
    }

}
