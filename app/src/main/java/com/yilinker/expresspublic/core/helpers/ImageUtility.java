package com.yilinker.expresspublic.core.helpers;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
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

    public static Bitmap convertFileToBitmap(String filePath) {

        Bitmap bm = BitmapFactory.decodeFile(filePath);

        if (bm != null) {

//            ByteArrayOutputStream out = new ByteArrayOutputStream();
//            bm.compress(Bitmap.CompressFormat.PNG, 100, out);
//            Bitmap decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));

            //Resize the image
            double width = bm.getWidth();
            double height = bm.getHeight();
            double ratio = 400 / width;
            int newheight = (int) (ratio * height);

            return Bitmap.createScaledBitmap(bm, 400, newheight, true);
        }


        return null;
    }

}
