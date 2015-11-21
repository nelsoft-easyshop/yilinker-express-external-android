package com.yilinker.expresspublic.modules.myShipment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.yilinker.expresspublic.R;
import com.yilinker.expresspublic.modules.BaseActivity;

import java.util.logging.Logger;

/**
 * Created by Jeico.
 */
public class MyShipmentActivity extends BaseActivity
{
    private static final Logger logger = Logger.getLogger(MyShipmentActivity.class.getSimpleName());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        // Get the ViewPager and set it's PagerAdapter so that it can display items
//        ViewPager vp_viewPager = (ViewPager) findViewById(R.id.vp_viewPager);
//        vp_viewPager.setAdapter(new MyShipmentPagerAdapter(getSupportFragmentManager()));
//
//        // Give the TabLayout the ViewPager
//        TabLayout tl_tabs = (TabLayout) findViewById(R.id.tl_tabs);
//        tl_tabs.setupWithViewPager(vp_viewPager);

        initViews();

    }

    private void initViews() {

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager vp_viewPager = (ViewPager) findViewById(R.id.vp_viewPager);
        vp_viewPager.setAdapter(new MyShipmentPagerAdapter(getSupportFragmentManager()));

        // Give the TabLayout the ViewPager
        TabLayout tl_tabs = (TabLayout) findViewById(R.id.tl_tabs);
        tl_tabs.setupWithViewPager(vp_viewPager);

    }


    @Override
    protected int getBaseLayout() {
        return R.layout.activity_base_ll;
    }

    @Override
    protected int getToolbarTitle() {
        return R.string.title_my_shipments;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_my_shipment;
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected Intent resultIntent() {
        return null;
    }
}
