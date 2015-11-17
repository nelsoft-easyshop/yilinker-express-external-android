package com.yilinker.expresspublic.modules.bookDelivery;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yilinker.expresspublic.R;
import com.yilinker.expresspublic.core.contants.BundleKey;
import com.yilinker.expresspublic.core.contants.RequestCode;
import com.yilinker.expresspublic.core.helpers.ImageUtility;
import com.yilinker.expresspublic.core.helpers.MediaHelper;
import com.yilinker.expresspublic.modules.BaseActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Created by Jeico.
 */
public class PackageDetailsActivity2 extends BaseActivity implements View.OnClickListener {
    private static final Logger logger = Logger.getLogger(PackageDetailsActivity2.class.getSimpleName());

    public static final int REQUIRED_WIDTH = 640;
    public static final int REQUIRED_HEIGHT = 640;
    public static final int MAX_PHOTO_COUNT = 5;

    private File photo;
    private String photoFilepath;
    private ArrayList<String> photoFilePathList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        photoFilePathList = new ArrayList<>();
    }

    @Override
    protected int getBaseLayout() {
        return R.layout.activity_base_ll;
    }

    @Override
    protected int getToolbarTitle() {
        return R.string.title_package_details;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_package_details;
    }

    @Override
    protected void initListeners() {
        // Set onclick listener for adding image
        findViewById(R.id.btn_add).setOnClickListener(this);
        // Set onclick listener for submit
        findViewById(R.id.btn_submit).setOnClickListener(this);
        //Set onclick listener for view image
        findViewById(R.id.tv_imageCount).setOnClickListener(this);
    }

    @Override
    protected Intent resultIntent() {

        if(resultCode == RESULT_OK)
        {
            String packageName = ((EditText) findViewById(R.id.et_packageName)).getText().toString().trim();
            String sku = ((EditText) findViewById(R.id.et_sku)).getText().toString().trim();
            String quantityRaw = ((EditText) findViewById(R.id.et_quantity)).getText().toString().trim();
            int quantity;
            if(TextUtils.isEmpty(quantityRaw))
            {
                quantity = 0;
            }
            else
            {
                quantity = Integer.parseInt(quantityRaw);
            }

            boolean isFragile = ((SwitchCompat) findViewById(R.id.sc_fragile)).isChecked();
            String paidBy;

            boolean isPaidBySender = ((SwitchCompat) findViewById(R.id.sc_paidBySender)).isChecked();
            if(isPaidBySender)
            {
                paidBy = "sender";
            }
            else
            {
                paidBy = "recipient";
            }

            Bundle bundle = new Bundle();
            bundle.putString(BundleKey.PACKAGE_NAME, packageName);
            bundle.putString(BundleKey.SKU, sku);
            bundle.putInt(BundleKey.QUANTITY, quantity);
            bundle.putString(BundleKey.PAID_BY, paidBy);
            bundle.putBoolean(BundleKey.IS_FRAGILE, isFragile);
            bundle.putStringArrayList(BundleKey.PHOTO_FILEPATH_LIST, photoFilePathList);

            Intent intent = new Intent();
            intent.putExtras(bundle);

            return intent;
        }
        else
        {
            return null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(resultCode == RESULT_OK)
        {
            if(requestCode == RequestCode.RCA_TAKE_PHOTO)
            {
                if(photo != null)
                {
                    photoFilepath = photo.getAbsolutePath();

                }

            } else if(requestCode == RequestCode.RCA_GALLERY_SELECT) {

                // try to retrieve the image using the data from the intent
                String fileSrc = ImageUtility.getRealPathFromURI(this, data.getData());

                if (fileSrc != null)
                {
                    photo = new File(fileSrc);
                    photoFilepath = photo.getAbsolutePath();

                }

            }

            // Get the dimensions of the bitmap
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(photoFilepath, bmOptions);
            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;
            // Determine how much to scale down the image
            int scaleFactor = Math.min(photoW/REQUIRED_WIDTH, photoH/REQUIRED_HEIGHT);
            // Decode the image file into a Bitmap sized to fill the View
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;
            Bitmap bitmap = BitmapFactory.decodeFile(photoFilepath, bmOptions);
            // Crop image to square
            bitmap = MediaHelper.cropToSquare(bitmap);
            // Save image

            try
            {
                File tempFile = MediaHelper.createOutputMediaFile();

                if(tempFile != null)
                {
                    FileOutputStream out = new FileOutputStream(tempFile.getAbsolutePath());
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                    out.flush();
                    out.close();

                    photoFilePathList.add(tempFile.getAbsolutePath());

                    updatePhotoCount();
                }
                else
                {
                    logger.severe("temp is null");
                }
            } catch(Exception e)
            {
                e.printStackTrace();
            }

        }

        // Delete original image
        if(photo != null)
        {
            if(photo.exists())
            {
                photo.delete();
            }
            photo = null;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_add:
                addPhoto();
                break;

            case R.id.btn_submit:
                handleSubmit();
                break;

            case R.id.tv_imageCount:
                handleImageCount();
                break;

            default:
                break;
        }
    }

    private void handleImageCount() {

        Intent intent = new Intent(this, PackageDetailsImagesActivity.class);
        startActivityForResult(intent, RequestCode.RCA_PACKAGE_IMAGES);

    }

    private void handleSubmit() {
        resultCode = RESULT_OK;
        finish();
    }

    private void addPhoto() {

        if(photoFilePathList.size() < MAX_PHOTO_COUNT) {

            final CharSequence[] items = {"Take Photo", "Choose from Gallery"};

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select Photo");
            builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (items[which].equals("Take Photo")) {

                        takePhotoCamera();

                    } else if (items[which].equals("Choose from Gallery")) {

                        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        galleryIntent.setType("image/*");
                        startActivityForResult(Intent.createChooser(galleryIntent, "Select Photos"),
                                RequestCode.RCA_GALLERY_SELECT);

                    }

                    dialog.dismiss();
                }
            });

            builder.create().show();
        }
        else
        {
            Toast.makeText(PackageDetailsActivity2.this, getString(R.string.error_maximum_photo_upload), Toast.LENGTH_SHORT).show();
        }
    }

    private void takePhotoCamera() {

        try
        {
            photo = MediaHelper.createOutputMediaFile();
            MediaHelper.actionTakePhoto(this, photo, RequestCode.RCA_TAKE_PHOTO);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void updatePhotoCount() {
        ((TextView) findViewById(R.id.tv_imageCount)).setText(photoFilePathList.size() + " Images");
    }


}
