package com.yilinker.expresspublic.modules.bookDelivery;

import android.content.Intent;
import android.os.Bundle;

import com.yilinker.expresspublic.R;
import com.yilinker.expresspublic.modules.BaseActivity;

import java.util.logging.Logger;

/**
 * Created by Jeico.
 */
public class PackageDetailsActivity extends BaseActivity {
    private static final Logger logger = Logger.getLogger(PackageDetailsActivity.class.getSimpleName());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    }

    @Override
    protected Intent resultIntent() {
        return null;
    }
}
