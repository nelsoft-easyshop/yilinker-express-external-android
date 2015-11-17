package com.yilinker.expresspublic.modules.bookDelivery;

import android.content.Intent;

import com.yilinker.expresspublic.R;
import com.yilinker.expresspublic.modules.BaseActivity;

/**
 * Created by rlcoronado on 17/11/2015.
 */
public class PackageDetailsImagesActivity extends BaseActivity {
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
        return R.layout.activity_package_details_images;
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected Intent resultIntent() {
        return null;
    }
}


