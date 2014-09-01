
package com.xian.xingyu.cache;

import android.graphics.drawable.Drawable;
import android.support.v4.util.LruCache;

public class DrawableCache {

    public LruCache<String, Drawable> mMemoryCache;
    private static DrawableCache mInstance = null;
    private static int CACHE_SIZE = 32;

    private DrawableCache() {
        mMemoryCache = new LruCache<String, Drawable>(CACHE_SIZE);
    }

    public void release() {
        mMemoryCache.evictAll();
        mInstance = null;
        System.gc();
    }

    /**
     * 通过此方法来获取NativeImageLoader的实例
     *
     * @return
     */
    public static DrawableCache getInstance() {
        if (mInstance == null) {
            createInstance();
        }
        return mInstance;
    }

    private static synchronized void createInstance() {
        if (mInstance == null) {
            mInstance = new DrawableCache();
        }
    }

    /**
     * 往内存缓存中添加Bitmap
     *
     * @param key
     * @param bitmap
     */
    public synchronized void addDrawableCache(String key, Drawable drawable) {
        if (getDrawableCache(key) == null && drawable != null) {
            mMemoryCache.put(key, drawable);
        }
    }

    /**
     * 根据key来获取内存中的图片
     *
     * @param key
     * @return
     */
    public Drawable getDrawableCache(String key) {
        return mMemoryCache.get(key);
    }

}
