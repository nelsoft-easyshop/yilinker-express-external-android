package com.yilinker.expresspublic.modules.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.viewpagerindicator.CirclePageIndicator;
import com.yilinker.expresspublic.BaseApplication;
import com.yilinker.expresspublic.R;
import com.yilinker.expresspublic.ResponseHandler;
import com.yilinker.expresspublic.core.api.HomeApi;
import com.yilinker.expresspublic.core.contants.RequestCode;
import com.yilinker.expresspublic.core.helpers.CommonPrefHelper;
import com.yilinker.expresspublic.core.models.Slider;
import com.yilinker.expresspublic.core.responses.EvSliderListResp;
import com.yilinker.expresspublic.modules.BaseActivity;
import com.yilinker.expresspublic.modules.bookDelivery.BookDeliveryActivity;
import com.yilinker.expresspublic.modules.findBranch.SearchBranchesActivity;
import com.yilinker.expresspublic.modules.login.LoginActivity;
import com.yilinker.expresspublic.modules.myShipment.MyShipmentActivity;
import com.yilinker.expresspublic.modules.trackDelivery.TrackDeliveryActivity;

import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.UpdateManager;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class HomeActivity
        extends BaseActivity
        implements View.OnClickListener, ResponseHandler
{
    private static final Logger logger = Logger.getLogger(HomeActivity.class.getSimpleName());

    private ViewPager vp_slider;

    private List<SliderFragment> sliderFragmentList;

    private SliderPagerAdapter sliderPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Remove back button
        ((ImageView) toolbar.findViewById(R.id.toolbar_back)).setVisibility(View.GONE);
        // Check position of title
        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setGravity(Gravity.START | Gravity.CENTER_VERTICAL);


        sliderFragmentList = new ArrayList<>();
        sliderPagerAdapter = new SliderPagerAdapter(getSupportFragmentManager(), sliderFragmentList);
        // Instantiate a ViewPager and a PagerAdapter plus the animation.
        vp_slider = (ViewPager) findViewById(R.id.vp_slider);
        vp_slider.setAdapter(sliderPagerAdapter);

        // Instantiate circle page indicator
        CirclePageIndicator cpi_indicator = (CirclePageIndicator) findViewById(R.id.cpi_indicator);
        cpi_indicator.setViewPager(vp_slider);

        //TODO uncomment this when api for getSlider is working
//        volleyGetSliderList();
        //temp
        sliderContent();

//        checkForUpdates();
    }

    @Override
    protected int getBaseLayout()
    {
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
    protected void initListeners()
    {
        findViewById(R.id.fab_trackADelivery).setOnClickListener(this);
        findViewById(R.id.fab_findBranch).setOnClickListener(this);
        findViewById(R.id.fab_bookDelivery).setOnClickListener(this);
        findViewById(R.id.fab_myShipment).setOnClickListener(this);
    }

    @Override
    protected Intent resultIntent()
    {
        return null;
    }

    @Override
    protected void onPause()
    {
        super.onPause();
//        UpdateManager.unregister();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
//        checkForCrashes();
    }

    @Override
    public void onClick(View v)
    {
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

    private void checkForCrashes()
    {
        CrashManager.register(this, getString(R.string.hockeyapp_app_id));
    }

    private void checkForUpdates()
    {
        /**
         * TODO Remove this for store / production builds!
         */
        UpdateManager.register(this, getString(R.string.hockeyapp_app_id));
    }

    private void handleMyShipment()
    {
        if (CommonPrefHelper.isUserLoggedIn(this))
        {
            Intent intent = new Intent(this, MyShipmentActivity.class);
            startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    private void handleBookDelivery()
    {
        if (CommonPrefHelper.isUserLoggedIn(this))
        {
            Intent intent = new Intent(this, BookDeliveryActivity.class);
            startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    private void handleFindBranch()
    {
        if (CommonPrefHelper.isUserLoggedIn(this))
        {
            Intent intent = new Intent(this, SearchBranchesActivity.class);
            startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    private void handleTrackADelivery()
    {
        if (CommonPrefHelper.isUserLoggedIn(this))
        {
            Intent intent = new Intent(this, TrackDeliveryActivity.class);
            startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onResponse(int requestCode, Object object)
    {
        switch (requestCode)
        {
            case RequestCode.RCR_GET_SLIDER_LIST:
                EvSliderListResp evSliderListResp = (EvSliderListResp) object;
                handleSliderListResp(evSliderListResp.data);
                break;

            default:
                break;
        }
    }

    @Override
    public void onErrorResponse(int requestCode, String message)
    {
        switch (requestCode)
        {
            case RequestCode.RCR_GET_SLIDER_LIST:
                break;

            default:
                break;
        }
    }

    private void volleyGetSliderList()
    {
        Request request = HomeApi.getSliderList(RequestCode.RCR_GET_SLIDER_LIST, this);
        BaseApplication.getInstance().getRequestQueue().add(request);
    }

    private void handleSliderListResp(List<Slider> sliderList)
    {
        if (sliderList != null)
        {
            sliderFragmentList.clear();

            for (Slider slider : sliderList)
            {
                sliderFragmentList.add(SliderFragment.newInstance(slider.getTitle(), slider.getContent()));
            }

            sliderPagerAdapter.notifyDataSetChanged();
        }
    }

    private void sliderContent() {
        List<Slider> sliderList = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            int a = i + 1;
            Slider slider = new Slider();
            slider.setTitle("Sample Title " + a);
            slider.setContent("Crazy Fredrick bought many very exquisite opal jewels.\n" +
                    "We promptly judged antique ivory buckles for the next prize.\n" +
                    "A mad boxer shot a quick, gloved jab to the jaw of his dizzy opponent.");
            sliderList.add(slider);
        }

        handleSliderListResp(sliderList);
    }
}
