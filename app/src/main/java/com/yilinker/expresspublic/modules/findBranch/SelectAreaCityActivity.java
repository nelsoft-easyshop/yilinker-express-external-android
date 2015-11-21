package com.yilinker.expresspublic.modules.findBranch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.yilinker.expresspublic.BaseApplication;
import com.yilinker.expresspublic.R;
import com.yilinker.expresspublic.ResponseHandler;
import com.yilinker.expresspublic.core.api.LocationApi;
import com.yilinker.expresspublic.core.contants.BundleKey;
import com.yilinker.expresspublic.core.contants.RequestCode;
import com.yilinker.expresspublic.core.models.City;
import com.yilinker.expresspublic.core.models.ProvinceWithCity;
import com.yilinker.expresspublic.core.responses.EvGetAllProvinceAndCityResp;
import com.yilinker.expresspublic.modules.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Jeico.
 */
public class SelectAreaCityActivity extends BaseActivity implements ResponseHandler, ProvinceAndCityAdapter.OnCityListener {
    private static final Logger logger = Logger.getLogger(SelectAreaCityActivity.class.getSimpleName());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        vollleyGetAllProvinceAndCity();
    }

    @Override
    protected int getBaseLayout() {
        return R.layout.activity_base_ll;
    }

    @Override
    protected int getToolbarTitle() {
        return R.string.title_select_area;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_select_area_city;
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected Intent resultIntent() {
        return null;
    }

    @Override
    public void onResponse(int requestCode, Object object) {
        switch (requestCode)
        {
            case RequestCode.RCR_GET_ALL_PROVINCE_AND_CITY:
                EvGetAllProvinceAndCityResp evGetAllProvinceAndCityResp = (EvGetAllProvinceAndCityResp) object;
                setupList(evGetAllProvinceAndCityResp);
                break;

            default:
                break;
        }
    }

    @Override
    public void onErrorResponse(int requestCode, String message) {
        switch (requestCode)
        {
            case RequestCode.RCR_GET_ALL_PROVINCE_AND_CITY:
                Toast.makeText(SelectAreaCityActivity.this, message, Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }

    private void vollleyGetAllProvinceAndCity()
    {
        Request request = LocationApi.getAllProvinceAndCity(RequestCode.RCR_GET_ALL_PROVINCE_AND_CITY, this);
        BaseApplication.getInstance().getRequestQueue().add(request);
    }

    private void setupList(EvGetAllProvinceAndCityResp evGetAllProvinceAndCityResp) {

        List<ProvinceModel> provinceModelList = new ArrayList<>();

        List<ProvinceWithCity> provinceWithCityList = evGetAllProvinceAndCityResp.data;
        for(ProvinceWithCity provinceWithCity : provinceWithCityList)
        {
            List<Object> cityList = new ArrayList<>();
            for(City city : provinceWithCity.getCityList())
            {
                cityList.add(city);
                cityList.add(city);
                cityList.add(city);
            }

            ProvinceModel provinceModel = new ProvinceModel(provinceWithCity.getId(), provinceWithCity.getName(), cityList);
            provinceModelList.add(provinceModel);
        }

        RecyclerView rv_provinceCity = (RecyclerView) findViewById(R.id.rv_provinceCity);
        ProvinceAndCityAdapter provinceAndCityAdapter = new ProvinceAndCityAdapter(this, provinceModelList, this);
        rv_provinceCity.setAdapter(provinceAndCityAdapter);
        rv_provinceCity.setLayoutManager(new LinearLayoutManager(this));
        rv_provinceCity.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCitySelected(Long id, String name) {

        Bundle bundle = new Bundle();
        bundle.putLong(BundleKey.CITY_ID, id);
        bundle.putString(BundleKey.KEYWORD, name);

        Intent intent = new Intent(this, SelectAreaBranchActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
