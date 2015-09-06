package com.yilinker.expresspublic.core.utilities;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by Arci.Malabanan on 12/29/2014.
 */
public class LruBitmapCache extends LruCache<String, Bitmap> implements
        ImageLoader.ImageCache
{
    /**
     * Get default lru cache size
     * @return cache size
     */
    public static int getDefaultLruCacheSize()
    {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;

        return cacheSize;
    }

    /**
     * Default constructor
     */
    public LruBitmapCache()
    {
        this(getDefaultLruCacheSize());
    }

    /**
     * @param maxSize
     */
    public LruBitmapCache(int maxSize)
    {
        super(maxSize);
        // TODO Auto-generated constructor stub
    }


    @Override
    protected int sizeOf(String key, Bitmap value)
    {
        return value.getRowBytes() * value.getHeight() / 1024;
    }

    /* (non-Javadoc)
     * @see com.android.volley.toolbox.ImageLoader.ImageCache#getBitmap(java.lang.String)
     */
    @Override
    public Bitmap getBitmap(String url)
    {
        return get(url);
    }

    /* (non-Javadoc)
     * @see com.android.volley.toolbox.ImageLoader.ImageCache#putBitmap(java.lang.String, android.graphics.Bitmap)
     */
    @Override
    public void putBitmap(String url, Bitmap bitmap)
    {
        put(url, bitmap);
    }
}
