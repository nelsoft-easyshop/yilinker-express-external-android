package com.yilinker.expresspublic.modules.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Jeico.
 */
public class SliderPagerAdapter extends FragmentStatePagerAdapter
{
    private static final Logger logger = Logger.getLogger(SliderPagerAdapter.class.getSimpleName());

    private List<SliderFragment> sliderFragmentList;

    public SliderPagerAdapter(FragmentManager fm, List<SliderFragment> sliderFragmentList)
    {
        super(fm);

        this.sliderFragmentList = sliderFragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return sliderFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return sliderFragmentList.size();
    }
}
