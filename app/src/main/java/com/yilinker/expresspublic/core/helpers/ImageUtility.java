package com.yilinker.expresspublic.core.helpers;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;

import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.LruCache;
/**
 * Created by jaybr_000 on 8/7/2015.
 */
public class ImageUtility {

    public static String getRealPathFromURI(Context context, Uri contentURI) {
        String result = null;
        String[] projection = {MediaStore.Images.Media.DATA};

        try {
            Cursor cursor = context.getContentResolver().query(contentURI, projection, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(projection[0]);
            result = cursor.getString(columnIndex);
            cursor.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }


}
