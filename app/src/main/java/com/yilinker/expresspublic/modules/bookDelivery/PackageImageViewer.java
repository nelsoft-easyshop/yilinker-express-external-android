package com.yilinker.expresspublic.modules.bookDelivery;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.yilinker.expresspublic.R;
import com.yilinker.expresspublic.core.contants.BundleKey;
import com.yilinker.expresspublic.core.helpers.ImageUtility;
import com.yilinker.expresspublic.modules.BaseActivity;
import com.yilinker.expresspublic.modules.common.customviews.TouchImageView;

/**
 * Created by rlcoronado on 18/11/2015.
 */
public class PackageImageViewer extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handleImageIntent();
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
        return R.layout.activity_package_image_viewer;
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected Intent resultIntent() {
        return null;
    }

    private void handleImageIntent() {

        String imagePath = getIntent().getExtras().getString(BundleKey.PHOTO_FILEPATH_LIST);
        Bitmap bm = ImageUtility.convertFileToBitmap(imagePath);

        ((TouchImageView) findViewById(R.id.tivImage)).setImageBitmap(bm);

    }

}
