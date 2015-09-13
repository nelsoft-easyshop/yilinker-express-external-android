package com.yilinker.expresspublic.modules.bookDelivery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.volley.Request;
import com.yilinker.expresspublic.BaseApplication;
import com.yilinker.expresspublic.R;
import com.yilinker.expresspublic.ResponseHandler;
import com.yilinker.expresspublic.core.api.LocationApi;
import com.yilinker.expresspublic.core.contants.BundleKey;
import com.yilinker.expresspublic.core.contants.RequestCode;
import com.yilinker.expresspublic.core.enums.AddressType;
import com.yilinker.expresspublic.core.helpers.OAuthPrefHelper;
import com.yilinker.expresspublic.modules.BaseActivity;
import com.yilinker.expresspublic.modules.common.addAddressLocation.AddAddressLocationActivity;

import java.util.logging.Logger;

/**
 * Created by Jeico.
 */
public class AddressLocationListActiviy extends BaseActivity implements View.OnClickListener, ResponseHandler {
    private static final Logger logger = Logger.getLogger(AddressLocationListActiviy.class.getSimpleName());

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
        return R.string.title_my_address_locations;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_address_location_list;
    }

    @Override
    protected void initListeners() {
        // Set onclick listener for new sender detail
        findViewById(R.id.btn_newSenderDetail).setOnClickListener(this);
    }

    @Override
    protected Intent resultIntent() {
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK)
        {
            if(requestCode == RequestCode.RCA_ADD_ADDRESS_LOCATION)
            {
                //refreshList();
            }
        }
        else
        {

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_newSenderDetail:
                handleNewSenderDetail();
                break;

            default:
                break;
        }
    }

    @Override
    public void onResponse(int requestCode, Object object) {

    }

    @Override
    public void onErrorResponse(int requestCode, String message) {

    }

    private void handleNewSenderDetail() {

        Bundle bundle = new Bundle();
        bundle.putSerializable(BundleKey.ADDRESS_TYPE, AddressType.SENDER);

        Intent intent = new Intent(this, AddAddressLocationActivity.class);
        intent.putExtras(bundle);

        startActivityForResult(intent, RequestCode.RCA_ADD_ADDRESS_LOCATION);
    }

    private void volleyGetMyAddressLocations()
    {
        Request request = LocationApi.getMyAddressLocations(OAuthPrefHelper.getAccessToken(this), RequestCode.RCR_GET_MY_ADDRESS_LOCATIONS, this);
        BaseApplication.getInstance().getRequestQueue().add(request);
    }


}
