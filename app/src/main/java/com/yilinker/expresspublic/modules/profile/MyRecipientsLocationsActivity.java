package com.yilinker.expresspublic.modules.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.yilinker.expresspublic.BaseApplication;
import com.yilinker.expresspublic.R;
import com.yilinker.expresspublic.ResponseHandler;
import com.yilinker.expresspublic.core.api.LocationApi;
import com.yilinker.expresspublic.core.contants.BundleKey;
import com.yilinker.expresspublic.core.contants.RequestCode;
import com.yilinker.expresspublic.core.enums.AddressType;
import com.yilinker.expresspublic.core.helpers.OAuthPrefHelper;
import com.yilinker.expresspublic.core.models.AddressGroup;
import com.yilinker.expresspublic.core.models.AddressLocation;
import com.yilinker.expresspublic.core.responses.EvMyAddressLocationModelListResp;
import com.yilinker.expresspublic.modules.BaseActivity;
import com.yilinker.expresspublic.modules.common.addAddressLocation.AddAddressLocationActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Jeico.
 */
public class MyRecipientsLocationsActivity extends BaseActivity implements View.OnClickListener, ResponseHandler {
    private static final Logger logger = Logger.getLogger(MyRecipientsLocationsActivity.class.getSimpleName());

    private List<MyAddressLocationModel> myAddressLocationModelList;

    private MyRecipientsLocationsAdapter myRecipientsLocationsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myAddressLocationModelList = new ArrayList<>();
        myRecipientsLocationsAdapter = new MyRecipientsLocationsAdapter(this, myAddressLocationModelList);

        // Expandable listview setup
        ExpandableListView elv_recipientLocation = (ExpandableListView) findViewById(R.id.elv_recipientLocation);
        elv_recipientLocation.setGroupIndicator(null);
        elv_recipientLocation.setAdapter(myRecipientsLocationsAdapter);

        refreshList();
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
        return R.layout.activity_my_recipients_locations;
    }

    @Override
    protected void initListeners() {
        // Set onclick listener for add address location
        findViewById(R.id.btn_addAddressLocation).setOnClickListener(this);
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
                refreshList();
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
            case R.id.btn_addAddressLocation:
                startAddAddressLocationActivity();
                break;

            default:
                break;
        }
    }

    @Override
    public void onResponse(int requestCode, Object object) {
        switch (requestCode)
        {
            case RequestCode.RCR_GET_MY_RECIPIENT_LOCATIONS:
                EvMyAddressLocationModelListResp evMyAddressLocationModelListResp = (EvMyAddressLocationModelListResp) object;
                populateList(evMyAddressLocationModelListResp);
                break;

            default:
                break;
        }

        findViewById(R.id.elv_recipientLocation).setVisibility(View.VISIBLE);
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

        findViewById(R.id.elv_recipientLocation).setVisibility(View.VISIBLE);
    }

    private void startAddAddressLocationActivity() {

        Bundle bundle = new Bundle();
        bundle.putSerializable(BundleKey.ADDRESS_TYPE, AddressType.RECIPIENT);

        Intent intent = new Intent(this, AddAddressLocationActivity.class);
        intent.putExtras(bundle);

        startActivityForResult(intent, RequestCode.RCA_ADD_ADDRESS_LOCATION);
    }

    private void refreshList() {

        findViewById(R.id.elv_recipientLocation).setVisibility(View.GONE);

        Request request = LocationApi.getRecipientLocationsAsGroup(OAuthPrefHelper.getAccessToken(this),
                RequestCode.RCR_GET_MY_RECIPIENT_LOCATIONS, this);
        BaseApplication.getInstance().getRequestQueue().add(request);
    }

    private void populateList(EvMyAddressLocationModelListResp evMyAddressLocationModelListResp) {

        myAddressLocationModelList.clear();

        List<EvMyAddressLocationModelListResp.Data> dataList = evMyAddressLocationModelListResp.data;

        for (EvMyAddressLocationModelListResp.Data data : dataList)
        {
            AddressGroup addressGroup = data.group;
            List<AddressLocation> addressLocationList = data.address;

            MyAddressLocationModel myAddressLocationModel = new MyAddressLocationModel();
            myAddressLocationModel.setAddressGroup(addressGroup);
            myAddressLocationModel.setAddressLocationList(addressLocationList);

            myAddressLocationModelList.add(myAddressLocationModel);
        }

        myRecipientsLocationsAdapter.notifyDataSetChanged();

        ExpandableListView elv_recipientLocation = (ExpandableListView) findViewById(R.id.elv_recipientLocation);

        // Expand list
        for(int position = 0; position < myRecipientsLocationsAdapter.getGroupCount(); position++)
        {
            elv_recipientLocation.expandGroup(position);
        }
    }
}
