package com.yilinker.expresspublic.modules.bookDelivery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yilinker.expresspublic.R;
import com.yilinker.expresspublic.modules.BaseActivity;
import com.yilinker.expresspublic.modules.common.addAddressLocation.AddAddressLocationActivity;

import java.util.logging.Logger;

/**
 * Created by Jeico.
 */
public class AddressLocationListActiviy extends BaseActivity implements View.OnClickListener {
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

    private void handleNewSenderDetail() {
        Intent intent = new Intent(this, AddAddressLocationActivity.class);
        startActivity(intent);
    }
}
