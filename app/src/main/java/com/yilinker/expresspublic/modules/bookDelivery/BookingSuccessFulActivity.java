package com.yilinker.expresspublic.modules.bookDelivery;

import android.content.Intent;
import android.os.Bundle;

import com.yilinker.expresspublic.modules.BaseActivity;

import java.util.logging.Logger;

/**
 * Created by Jeico.
 */
public class BookingSuccessFulActivity extends BaseActivity {
    private static final Logger logger = Logger.getLogger(BookingSuccessFulActivity.class.getSimpleName());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getBaseLayout() {
        return 0;
    }

    @Override
    protected int getToolbarTitle() {
        return 0;
    }

    @Override
    protected int getLayoutResource() {
        return 0;
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected Intent resultIntent() {
        return null;
    }
}
