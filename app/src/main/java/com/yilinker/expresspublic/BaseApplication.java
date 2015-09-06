package com.yilinker.expresspublic;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.yilinker.expresspublic.core.utilities.LruBitmapCache;

import java.util.logging.Logger;

public class BaseApplication extends Application
{
    private static final Logger logger = Logger.getLogger(BaseApplication.class.getSimpleName());

    /**
     * Singleton instance of BaseApplication
     */
    private static BaseApplication sBaseApplication;

    /**
     * Global request queue for Volley
     */
    private RequestQueue requestQueue;

    /**
     * Global image loader for Volley
     */
    private ImageLoader imageLoader;

    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize singleton instance of BaseApplication
        sBaseApplication = this;
    }

    /**
     * This method gets the singleton instance of GPApplication
     * @return GPApplication singleton instance
     */
    public static synchronized BaseApplication getInstance()
    {
        return sBaseApplication;
    }

    /**
     * Get singleton instance of request queue
     * @return
     */
    public RequestQueue getRequestQueue()
    {
        // lazy initialize the request queue, the queue instance will be created when it is
        // accessed for the first time
        if(requestQueue == null)
        {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return requestQueue;
    }

    /**
     * Get the image loader
     * @return image loader
     */
    public ImageLoader getImageLoader()
    {
        if(imageLoader == null)
        {
            imageLoader = new ImageLoader(getRequestQueue(), new LruBitmapCache());
        }

        return imageLoader;
    }
}
