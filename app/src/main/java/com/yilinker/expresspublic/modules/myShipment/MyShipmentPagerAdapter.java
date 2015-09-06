package com.yilinker.expresspublic.modules.myShipment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yilinker.expresspublic.core.enums.ShipmentType;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Jeico.
 */
public class MyShipmentPagerAdapter extends FragmentPagerAdapter
{
    private static final Logger logger = Logger.getLogger(MyShipmentPagerAdapter.class.getSimpleName());

    private List<String> titleList;
    private List<Fragment> fragmentList;

    public MyShipmentPagerAdapter(FragmentManager fm) {
        super(fm);

        titleList = new ArrayList<>();
        titleList.add(ShipmentType.ONGOING.getValue());
        titleList.add(ShipmentType.DELIVERED.getValue());

        fragmentList = new ArrayList<>();
        fragmentList.add(MyShipmentFragment.newInstance(ShipmentType.ONGOING));
        fragmentList.add(MyShipmentFragment.newInstance(ShipmentType.DELIVERED));
    }


    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
