package com.yilinker.expresspublic.modules.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yilinker.expresspublic.R;
import com.yilinker.expresspublic.core.helpers.CommonPrefHelper;
import com.yilinker.expresspublic.modules.BaseActivity;
import com.yilinker.expresspublic.modules.bookDelivery.BookDeliveryActivity;
import com.yilinker.expresspublic.modules.findBranch.SearchBranchesActivity;
import com.yilinker.expresspublic.modules.login.LogInActivity;
import com.yilinker.expresspublic.modules.myShipment.MyShipmentActivity;
import com.yilinker.expresspublic.modules.trackDelivery.TrackDeliveryActivity;

import java.util.logging.Logger;

public class HomeActivity extends BaseActivity implements View.OnClickListener {
    private static final Logger logger = Logger.getLogger(HomeActivity.class.getSimpleName());


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Remove back button
        ((ImageView) toolbar.findViewById(R.id.toolbar_back)).setVisibility(View.GONE);
        // Check position of title
        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
    }

    @Override
    protected int getBaseLayout() {
        return R.layout.activity_base_rl;
    }

    @Override
    protected int getToolbarTitle()
    {
        return R.string.title_home;
    }

    @Override
    protected int getLayoutResource()
    {
        return R.layout.activity_home;
    }

    @Override
    protected void initListeners() {
        findViewById(R.id.fab_trackADelivery).setOnClickListener(this);
        findViewById(R.id.fab_findBranch).setOnClickListener(this);
        findViewById(R.id.fab_bookDelivery).setOnClickListener(this);
        findViewById(R.id.fab_myShipment).setOnClickListener(this);
    }

    @Override
    protected Intent resultIntent() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.fab_trackADelivery:
                handleTrackADelivery();
                break;

            case R.id.fab_findBranch:
                handleFindBranch();
                break;

            case R.id.fab_bookDelivery:
                handleBookDelivery();
                break;

            case R.id.fab_myShipment:
                handleMyShipment();
                break;

            default:
                break;
        }
    }

    private void handleMyShipment()
    {
        if(CommonPrefHelper.isUserLoggedIn(this))
        {
            Intent intent = new Intent(this, MyShipmentActivity.class);
            startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(this, LogInActivity.class);
            startActivity(intent);
        }
    }

    private void handleBookDelivery()
    {
        if(CommonPrefHelper.isUserLoggedIn(this))
        {
            Intent intent = new Intent(this, BookDeliveryActivity.class);
            startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(this, LogInActivity.class);
            startActivity(intent);
        }
    }

    private void handleFindBranch()
    {
        if(CommonPrefHelper.isUserLoggedIn(this))
        {
            Intent intent = new Intent(this, SearchBranchesActivity.class);
            startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(this, LogInActivity.class);
            startActivity(intent);
        }
    }

    private void handleTrackADelivery()
    {
        if(CommonPrefHelper.isUserLoggedIn(this))
        {
            Intent intent = new Intent(this, TrackDeliveryActivity.class);
            startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(this, LogInActivity.class);
            startActivity(intent);
        }
    }
}
