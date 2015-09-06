package com.yilinker.expresspublic.modules.account;

import android.content.Intent;
import android.os.Bundle;

import com.yilinker.expresspublic.R;
import com.yilinker.expresspublic.modules.BaseActivity;

import java.util.logging.Logger;

/**
 * Created by Jeico.
 */
public class UpdateProfileActivity extends BaseActivity
{
    private static final Logger logger = Logger.getLogger(UpdateProfileActivity.class.getSimpleName());

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
        return R.string.title_update_profile;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_update_profile;
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected Intent resultIntent() {
        return null;
    }


}
