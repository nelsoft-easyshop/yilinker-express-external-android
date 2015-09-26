package com.yilinker.expresspublic.modules.bookDelivery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.google.gson.GsonBuilder;
import com.yilinker.expresspublic.BaseApplication;
import com.yilinker.expresspublic.R;
import com.yilinker.expresspublic.ResponseHandler;
import com.yilinker.expresspublic.core.api.LocationApi;
import com.yilinker.expresspublic.core.contants.BundleKey;
import com.yilinker.expresspublic.core.contants.RequestCode;
import com.yilinker.expresspublic.core.enums.AddressType;
import com.yilinker.expresspublic.core.helpers.OAuthPrefHelper;
import com.yilinker.expresspublic.core.interfaces.AddressListener;
import com.yilinker.expresspublic.core.models.Address;
import com.yilinker.expresspublic.core.responses.EvAddressLocationListResp;
import com.yilinker.expresspublic.modules.BaseActivity;
import com.yilinker.expresspublic.modules.common.addAddressLocation.AddAddressLocationActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Jeico.
 */
public class RecipientLocationListActivity extends BaseActivity implements View.OnClickListener, ResponseHandler, AddressListener
{
    private static final Logger logger = Logger.getLogger(RecipientLocationListActivity.class.getSimpleName());

    private List<Address> masterAddressList;
    private List<Address> addressList;

    private String keyword;
    private AddressAdapter addressAdapter;

    private Address selectedAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        masterAddressList = new ArrayList<>();
        addressList = new ArrayList<>();
        addressAdapter = new AddressAdapter(this, addressList, this);

        RecyclerView rv_myRecipientLocationList = (RecyclerView) findViewById(R.id.rv_myRecipientLocationList);
        rv_myRecipientLocationList.setHasFixedSize(true);
        rv_myRecipientLocationList.setAdapter(addressAdapter);
        rv_myRecipientLocationList.setLayoutManager(new LinearLayoutManager(this));

        volleyGetMyRecipientsLocations();
    }

    @Override
    protected int getBaseLayout() {
        return R.layout.activity_base_ll;
    }

    @Override
    protected int getToolbarTitle() {
        return R.string.title_my_recipients_locations;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_recipient_location_list;
    }

    @Override
    protected void initListeners() {
        // Set onclick listener for new sender detail
        findViewById(R.id.btn_newRecipientDetail).setOnClickListener(this);

        EditText et_searchField = (EditText) findViewById(R.id.et_searchField);
        et_searchField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                String tempKeyword = v.getText().toString().trim();

                logger.severe("tempKeyword: " + tempKeyword);
                if(TextUtils.isEmpty(tempKeyword))
                {
                    filterAddress(tempKeyword);
                }
                else if(tempKeyword.length() >= 3)
                {
                    filterAddress(tempKeyword);
                }

                return false;
            }
        });

        et_searchField.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                // Do nothing
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String tempKeyword = s.toString().trim();

                logger.severe("tempKeyword: " + tempKeyword);
                if(TextUtils.isEmpty(tempKeyword))
                {
                    filterAddress(tempKeyword);
                }
                else if(tempKeyword.length() >= 3)
                {
                    filterAddress(tempKeyword);
                }
            }
        });
    }

    @Override
    protected Intent resultIntent() {

        if(resultCode == RESULT_OK && selectedAddress != null)
        {
            Bundle bundle = new Bundle();
            bundle.putString(BundleKey.ADDRESS, new GsonBuilder().create().toJson(selectedAddress));

            Intent intent = new Intent();
            intent.putExtras(bundle);

            return intent;
        }
        else
        {
            return null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK)
        {
            if(requestCode == RequestCode.RCA_ADD_ADDRESS_LOCATION)
            {
                volleyGetMyRecipientsLocations();
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
            case R.id.btn_newRecipientDetail:
                handleNewRecipientDetail();
                break;

            default:
                break;
        }
    }

    @Override
    public void onAddressSelected(Address address) {

        selectedAddress = address;
        resultCode = RESULT_OK;
        finish();
    }

    @Override
    public void onResponse(int requestCode, Object object) {
        switch (requestCode)
        {
            case RequestCode.RCR_GET_MY_RECIPIENT_LOCATIONS:
                EvAddressLocationListResp evAddressLocationListResp = (EvAddressLocationListResp) object;
                populateList(evAddressLocationListResp.data);
                filterAddress(keyword);
                break;

            default:
                break;
        }

        findViewById(R.id.rv_myRecipientLocationList).setVisibility(View.VISIBLE);
    }

    @Override
    public void onErrorResponse(int requestCode, String message) {
        switch (requestCode)
        {
            case RequestCode.RCR_GET_MY_RECIPIENT_LOCATIONS:
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }

        findViewById(R.id.rv_myRecipientLocationList).setVisibility(View.VISIBLE);
    }

    private void handleNewRecipientDetail() {

        Bundle bundle = new Bundle();
        bundle.putSerializable(BundleKey.ADDRESS_TYPE, AddressType.RECIPIENT);

        Intent intent = new Intent(this, AddAddressLocationActivity.class);
        intent.putExtras(bundle);

        startActivityForResult(intent, RequestCode.RCA_ADD_ADDRESS_LOCATION);
    }

    private void volleyGetMyRecipientsLocations()
    {
        findViewById(R.id.rv_myRecipientLocationList).setVisibility(View.GONE);

        Request request = LocationApi.getMyRecipientLocations(OAuthPrefHelper.getAccessToken(this), RequestCode.RCR_GET_MY_RECIPIENT_LOCATIONS, this);
        BaseApplication.getInstance().getRequestQueue().add(request);
    }

    private void populateList(List<Address> tempAddressList)
    {
        if(tempAddressList != null)
        {
            addressList.clear();
            masterAddressList.clear();

            for (Address address : tempAddressList)
            {
                addressList.add(address);
                masterAddressList.add(address);
            }

            addressAdapter.notifyDataSetChanged();
        }
    }

    private void filterAddress(String keyword) {
        logger.severe(keyword);
        this.keyword = keyword;
        addressList.clear();

        if(TextUtils.isEmpty(keyword))
        {
            for (Address address : masterAddressList)
            {
                addressList.add(address);
            }
        }
        else
        {
            logger.severe("else");
            for (Address address : masterAddressList)
            {
                String _keyword = keyword.toLowerCase();
                String _name = address.getContactPerson().toLowerCase();
                String _address = address.getAddress().toLowerCase();

                if(_name.contains(_keyword) || _address.contains(_keyword))
                {
                    logger.severe("add");
                    addressList.add(address);
                }
            }
        }

        addressAdapter.notifyDataSetChanged();
    }
}
