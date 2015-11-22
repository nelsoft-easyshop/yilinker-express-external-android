package com.yilinker.expresspublic.modules.bookDelivery;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yilinker.expresspublic.R;
import com.yilinker.expresspublic.core.contants.BundleKey;
import com.yilinker.expresspublic.core.contants.RequestCode;
import com.yilinker.expresspublic.core.helpers.DialogHelper;
import com.yilinker.expresspublic.core.helpers.ImageUtility;
import com.yilinker.expresspublic.core.helpers.MediaHelper;
import com.yilinker.expresspublic.core.utilities.InputValidator;
import com.yilinker.expresspublic.modules.BaseActivity;

import java.io.File;
import java.io.FileNotFoundException;
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
    private boolean isPaidBy = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        photoFilePathList = new ArrayList<>();
        handleIntentData();
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
        //Set onclick listener for custom switch
        findViewById(R.id.rlSwitchPaidBy).setOnClickListener(this);

    }

    private void handleIntentData() {

        if(getIntent().getExtras() != null)
        {
            Bundle bundle = getIntent().getExtras();

            ((EditText) findViewById(R.id.et_packageName)).setText(bundle.getString(BundleKey.PACKAGE_NAME));
            ((EditText) findViewById(R.id.et_declared_value)).setText(bundle.getString(BundleKey.DECLARED_VALUE));
            ((EditText) findViewById(R.id.et_quantity)).setText(bundle.getString(BundleKey.QUANTITY));

            if(bundle.getString(BundleKey.PAID_BY).equals("false"))
            {
                isPaidBy = true;
                handlePaidBy();
//                ((SwitchCompat) findViewById(R.id.sc_paidBySender)).setChecked(false);
            }

            photoFilePathList = bundle.getStringArrayList(BundleKey.PHOTO_FILEPATH_LIST);

            updatePhotoCount();
        }

    }


    @Override
    protected Intent resultIntent() {

        if(resultCode == RESULT_OK)
        {
            String packageName = ((EditText) findViewById(R.id.et_packageName)).getText().toString().trim();
//            String sku = ((EditText) findViewById(R.id.et_sku)).getText().toString().trim();
            String declaredValue = ((EditText) findViewById(R.id.et_declared_value)).getText().toString().trim();
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

//            boolean isPaidBySender = ((SwitchCompat) findViewById(R.id.sc_paidBySender)).isChecked();
            if(isPaidBy)
            {
                paidBy = "sender";
            }
            else
            {
                paidBy = "recipient";
            }

            Bundle bundle = new Bundle();
            bundle.putString(BundleKey.PACKAGE_NAME, packageName);
//            bundle.putString(BundleKey.SKU, sku);
            bundle.putString(BundleKey.DECLARED_VALUE, declaredValue);
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

        boolean fromCamera = true;

        if (resultCode == RESULT_OK) {
            if (requestCode == RequestCode.RCA_TAKE_PHOTO)
            {
                if (photo != null)
                {
                    photoFilepath = photo.getAbsolutePath();
                    fromCamera = true;

                }

            }
            else if (requestCode == RequestCode.RCA_GALLERY_SELECT)
            {

                getPhotoPathFromGallery(data);
                fromCamera = false;

            }

            // Get the dimensions of the bitmap
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(photoFilepath, bmOptions);
            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;
            // Determine how much to scale down the image
            int scaleFactor = Math.min(photoW / REQUIRED_WIDTH, photoH / REQUIRED_HEIGHT);
            // Decode the image file into a Bitmap sized to fill the View
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;
            Bitmap bitmap = BitmapFactory.decodeFile(photoFilepath, bmOptions);
            // Crop image to square
            bitmap = MediaHelper.cropToSquare(bitmap);


            /**
             * Check if from gallery or photo taken from camera
             * If taken from camera save temporary file
             * Else get path of the image from gallery
             * Proceed to adding it to list
             */

            saveTempImage(bitmap, fromCamera);

            /**
             * Delete image if duplicate
             */
            checkIfPhotoExist();
        }
    }


    private void saveTempImage(Bitmap bitmap, boolean fromCamera) {

        FileOutputStream out = null;
        File tempFile = null;

        try
        {
            if(fromCamera)
            {
                tempFile = MediaHelper.createOutputMediaFile();

                if(tempFile != null)
                {
                    out = new FileOutputStream(tempFile.getAbsolutePath());

                }
                else
                {
                    logger.severe("temp is null");
                }
            }
            else
            {
                out = new FileOutputStream(photoFilepath);
            }

            //compress bitmap
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();

            if(fromCamera)
            {
                photoFilePathList.add(tempFile.getAbsolutePath());
            }
            else
            {
                photoFilePathList.add(photoFilepath);
            }

            updatePhotoCount();

        } catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    private void checkIfPhotoExist() {

        // Delete original image
        if (photo != null) {
            if (photo.exists()) {
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

            case R.id.rlSwitchPaidBy:
                handlePaidBy();
                break;

            default:
                break;
        }
    }

    private void handlePaidBy() {

        Animation slide;

        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
        int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
        int marginStart = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25, getResources().getDisplayMetrics());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.width = size;
        params.height = size;

        if(isPaidBy)
        {
            slide = AnimationUtils.loadAnimation(this, R.anim.anim_slide_in_right);
            params.setMargins(margin, margin, margin, margin);

            findViewById(R.id.rlSwitchPaidBy).setBackground(ContextCompat.getDrawable(this, R.drawable.bg_semi_rounded_switch_recipient));
            findViewById(R.id.tv_paidBySender).setVisibility(View.GONE);
            findViewById(R.id.switchPaidBySender).setLayoutParams(params);
            findViewById(R.id.switchPaidBySender).startAnimation(slide);
            findViewById(R.id.tv_paidByRecipient).setVisibility(View.VISIBLE);

            isPaidBy = false;
        }
        else
        {
            slide = AnimationUtils.loadAnimation(this, R.anim.anim_slide_in_left);
            params.setMargins(marginStart, margin, margin, margin);

            findViewById(R.id.rlSwitchPaidBy).setBackground(ContextCompat.getDrawable(this, R.drawable.bg_semi_rounded_switch_sender));
            findViewById(R.id.tv_paidByRecipient).setVisibility(View.GONE);
            findViewById(R.id.switchPaidBySender).setLayoutParams(params);
            findViewById(R.id.switchPaidBySender).startAnimation(slide);
            findViewById(R.id.tv_paidBySender).setVisibility(View.VISIBLE);

            isPaidBy = true;
        }
    }


    private void getPhotoPathFromGallery(Intent data) {

        // try to retrieve the image using the data from the intent
        String fileSrc =  ImageUtility.getRealPathFromURI(this, data.getData());

        if (fileSrc != null)
        {
            photoFilepath = new File(fileSrc).getAbsolutePath();
//            photoFilepath = photo.getAbsolutePath();
        }

    }

    private void handleImageCount() {

        if(photoFilePathList.size() > 0) {

            Intent intent = new Intent(this, PackageDetailsImagesActivity.class);
            intent.putExtra(BundleKey.PHOTO_FILEPATH_LIST, photoFilePathList);
            startActivityForResult(intent, RequestCode.RCA_PACKAGE_IMAGES);
        }

    }

    private void handleSubmit() {

        String packageDescription = ((EditText) findViewById(R.id.et_packageName)).getText().toString();
        String declaredValue = ((EditText) findViewById(R.id.et_declared_value)).getText().toString();
        String quantity = ((EditText) findViewById(R.id.et_quantity)).getText().toString();

        String errorMessage = validateUserInput(packageDescription, declaredValue, quantity);


        if(errorMessage != null)
        {
            AlertDialog alertDialog = DialogHelper.createOkDialog(this, true, getString(R.string.error), errorMessage);
            alertDialog.show();
        }
        else
        {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

            //finish activity
            resultCode = RESULT_OK;
            finish();
        }


    }

    private String validateUserInput(String packageDescription, String declaredValue, String quantity)
    {
        String errorMessage = InputValidator.isPackageNameValid(packageDescription);

        if(errorMessage != null)
        {
            return errorMessage;
        }

        errorMessage = InputValidator.isDeclaredValueValid(declaredValue);

        if(errorMessage != null)
        {
            return errorMessage;
        }

        errorMessage = InputValidator.isQuantityValid(quantity);

        if(errorMessage != null)
        {
            return errorMessage;
        }

        return errorMessage;
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

                        takePhotoFromGallery();

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

    private void takePhotoFromGallery() {

        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("image/*");
        startActivityForResult(Intent.createChooser(galleryIntent, "Select Photos"),
                RequestCode.RCA_GALLERY_SELECT);

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
