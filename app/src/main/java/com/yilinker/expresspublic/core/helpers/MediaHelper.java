package com.yilinker.expresspublic.core.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.yilinker.expresspublic.core.contants.AppConstant;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * Created by Jeico on 5/18/2015.
 */
public class MediaHelper
{
    private static final Logger logger = Logger.getLogger(MediaHelper.class.getSimpleName());

    /**
     * TODO
     * @param context
     * @param requestCode
     */
    public static void actionTakePhoto(Context context, int requestCode)
    {
        /**
         * Check camera availability
         */
        if (isDeviceSupportCamera(context))
        {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            /**
             * This condition returns the first activity component that can handle this intent.
             * Without this condition, the app will crash if there's no component that can handle this intent.
             * This will ensure that the intent can be handle.
             */
            if (intent.resolveActivity(context.getPackageManager()) != null)
            {
                try
                {
                    File photoFile = MediaHelper.createOutputMediaFile();

                    /**
                     * Store the photo taken from camera to 'photoFile'
                     */
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                    ((Activity) context).startActivityForResult(intent, requestCode);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * TODO
     * @param context
     * @param photoFile
     * @param requestCode
     */
    public static void actionTakePhoto(Context context, File photoFile, int requestCode)
    {
        /**
         * Check camera availability
         */
        if (isDeviceSupportCamera(context))
        {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            /**
             * This condition returns the first activity component that can handle this intent.
             * Without this condition, the app will crash if there's no component that can handle this intent.
             * This will ensure that the intent can be handle.
             */
            if (intent.resolveActivity(context.getPackageManager()) != null)
            {
                /**
                 * Store the photo taken from camera to 'photoFile'
                 */
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                ((Activity) context).startActivityForResult(intent, requestCode);
            }
        }
    }

    /**
     * Checking device has camera hardware or not
     *
     * @param context
     * @return
     */
    public static boolean isDeviceSupportCamera(Context context)
    {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
        {
            // this device has a camera
            return true;
        }
        else
        {
            // no camera on this device
            return false;
        }
    }

    /**
     * TODO
     *
     * @return
     */
    public static File createOutputMediaFile() throws
            IOException
    {

        // External sdcard location
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), AppConstant.DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists())
        {
            if (!mediaStorageDir.mkdirs())
            {
                logger.info("Failed to create" + " " + AppConstant.DIRECTORY_NAME + " " + "directory.");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());

        File mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");

        return mediaFile;
    }

    /**
     * TODO
     *
     * @param context
     * @param filepath
     */
    public static void addMediaToGallery(Context context, String filepath)
    {
        File f = new File(filepath);
        Uri contentUri = Uri.fromFile(f);

        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(contentUri);

        context.sendBroadcast(mediaScanIntent);
    }

    /**
     * TODO
     *
     * @param context
     * @param file
     */
    public static void addMediaToGallery(Context context, File file)
    {
        Uri contentUri = Uri.fromFile(file);

        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(contentUri);

        context.sendBroadcast(mediaScanIntent);
    }

    /**
     * TODO
     *
     * @param context
     * @param filename
     * @param bitmap
     * @return
     */
    public static boolean saveBitmapToInternalStorage(Context context, String filename, Bitmap bitmap)
    {
        try
        {
            // Use the compress method on the Bitmap object to write image to
            // the OutputStream
            FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE);

            // Writing the bitmap to the output stream
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();

            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * TODO
     *
     * @param context
     * @param filename
     * @return
     */
    public static Bitmap loadBitmapFromInternalStorage(Context context, String filename)
    {
        try
        {
            File filePath = context.getFileStreamPath(filename);
            FileInputStream fi = new FileInputStream(filePath);

            return BitmapFactory.decodeStream(fi);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * TODO
     *
     * @param context
     * @param filename
     * @return
     */
    public static boolean deleteBitmapFromInternalStorage(Context context, String filename)
    {
        if (context.deleteFile(filename))
        {
            logger.severe("File deleted successfully.");
            return true;
        }
        else
        {
            logger.severe("File deleted unsuccessfully.");
            return false;
        }
    }

    /**
     * Calculates the sample size of the image
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return inSampleSize
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight)
    {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;

        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth)
        {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth)
            {

                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    /**
     * TODO
     * @param filepath
     * @param bitmap
     * @return
     */
    public static Bitmap repairOrientation(String filepath, Bitmap bitmap)
    {
        ExifInterface ei = null;
        try
        {
            ei = new ExifInterface(filepath);
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (ei != null)
        {
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation)
            {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    bitmap = rotateBitmap(bitmap, 90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    bitmap = rotateBitmap(bitmap, 180);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    bitmap = rotateBitmap(bitmap, 270);
                    break;
            }
        }

        return bitmap;
    }

    /**
     * TODO
     * @param bitmap
     * @param rotation
     * @return
     */
    public static Bitmap rotateBitmap(Bitmap bitmap, int rotation)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(rotation);

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    /**
     * TODO
     * @param bitmap
     * @return
     */
    public static Bitmap cropToSquare(Bitmap bitmap)
    {
        int width  = bitmap.getWidth();
        int height = bitmap.getHeight();
        int newWidth = (height > width) ? width : height;
        int newHeight = (height > width)? height - ( height - width) : height;
        int cropW = (width - height) / 2;
        cropW = (cropW < 0)? 0: cropW;
        int cropH = (height - width) / 2;
        cropH = (cropH < 0)? 0: cropH;
        Bitmap cropImg = Bitmap.createBitmap(bitmap, cropW, cropH, newWidth, newHeight);

        return cropImg;
    }

    public static Bitmap loadFromFile(String filename) {
        try {
            File f = new File(filename);
            if (!f.exists()) { return null; }
            Bitmap tmp = BitmapFactory.decodeFile(filename);
            return tmp;
        } catch (Exception e) {
            return null;
        }
    }
    public static Bitmap loadFromCacheFile() {
        return loadFromFile(getCacheFilename());
    }
    public static void saveToCacheFile(Bitmap bmp) {
        saveToFile(getCacheFilename(),bmp);
    }
    public static void saveToFile(String filename,Bitmap bmp) {
        try {
            FileOutputStream out = new FileOutputStream(filename);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch(Exception e) {}
    }

    public static boolean hasSDCard() { // SD????????
        String status = Environment.getExternalStorageState();
        return status.equals(Environment.MEDIA_MOUNTED);
    }
    public static String getSDCardPath() {
        File path = Environment.getExternalStorageDirectory();
        return path.getAbsolutePath();
    }

    public static String getCacheFilename() {
        File f = getSavePath();
        return f.getAbsolutePath() + "/cache.png";
    }

    public static File getSavePath() {
        File path;
        if (hasSDCard()) { // SD card
            path = new File(getSDCardPath() + "/Tegaky/");
            path.mkdir();
        } else {
            path = Environment.getDataDirectory();
        }
        return path;
    }
}
