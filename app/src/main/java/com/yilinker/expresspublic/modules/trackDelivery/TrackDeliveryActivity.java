package com.yilinker.expresspublic.modules.trackDelivery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yilinker.expresspublic.BaseApplication;
import com.yilinker.expresspublic.R;
import com.yilinker.expresspublic.ResponseHandler;
import com.yilinker.expresspublic.core.api.TrackApi;
import com.yilinker.expresspublic.core.contants.BundleKey;
import com.yilinker.expresspublic.core.contants.RequestCode;
import com.yilinker.expresspublic.core.deserializer.DateDeserializer;
import com.yilinker.expresspublic.core.helpers.OAuthPrefHelper;
import com.yilinker.expresspublic.core.interfaces.DeliveryPackageListener;
import com.yilinker.expresspublic.core.models.DeliveryPackage;
import com.yilinker.expresspublic.core.responses.EvDeliveryPackageListResp;
import com.yilinker.expresspublic.core.responses.EvDeliveryPackageResp;
import com.yilinker.expresspublic.core.serializer.DateSerializer;
import com.yilinker.expresspublic.modules.BaseActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Jeico.
 */
public class TrackDeliveryActivity extends BaseActivity implements DeliveryPackageListener, ResponseHandler, View.OnClickListener {
    private static final Logger logger = Logger.getLogger(TrackDeliveryActivity.class.getSimpleName());

    private List<DeliveryPackage> deliveryPackageList;

    private DeliveryPackageAdapter deliveryPackageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        deliveryPackageList = new ArrayList<>();

        deliveryPackageAdapter = new DeliveryPackageAdapter(this, deliveryPackageList, this);

        RecyclerView rv_deliveryPackageList = (RecyclerView) findViewById(R.id.rv_deliveryPackageList);
        rv_deliveryPackageList.setHasFixedSize(true);
        rv_deliveryPackageList.setAdapter(deliveryPackageAdapter);
        rv_deliveryPackageList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected int getBaseLayout() {
        return R.layout.activity_base_ll;
    }

    @Override
    protected int getToolbarTitle() {
        return R.string.title_track_delivery;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_track_delivery;
    }

    @Override
    protected void initListeners() {
        // Set qr code onclick listener
        findViewById(R.id.iv_scanTrackingCode).setOnClickListener(this);
        // Set search field listener
        EditText et_searchField = (EditText) findViewById(R.id.et_searchField);
        et_searchField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE)
                {
                    volleySearchTrackingNumber(v.getText().toString().trim());
                }

                return false;
            }
        });

        et_searchField.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s)
            {
                // Do nothing
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                // Do nothing
            }

            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                String keyword = s.toString().trim();
                volleySearchTrackingNumber(keyword);
            }
        });
    }

    @Override
    protected Intent resultIntent() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.iv_scanTrackingCode:
                handleScanningQRCode();
                break;

            default:
                break;
        }
    }

    private void handleScanningQRCode() {
        Intent intent = new Intent(this, ScanTrackingCodeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDeliveryPackageSelected(DeliveryPackage deliveryPackage) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateDeserializer())
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create();
        String deliveryPackageRaw = gson.toJson(deliveryPackage);

        Bundle bundle = new Bundle();
        bundle.putString(BundleKey.DELIVERY_PACKAGE, deliveryPackageRaw);

        Intent intent = new Intent(this, TrackDetailsActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onResponse(int requestCode, Object object) {
        switch (requestCode)
        {
            case RequestCode.RCR_SEARCH_TRACKING_NUMBER:
//                EvDeliveryPackageResp evDeliveryPackageResp = (EvDeliveryPackageResp) object;
//                populateList(evDeliveryPackageResp.data);
                EvDeliveryPackageListResp evDeliveryPackageListResp = (EvDeliveryPackageListResp) object;
                populateList(evDeliveryPackageListResp.data);
                break;

            default:
                break;
        }

        ((RecyclerView) findViewById(R.id.rv_deliveryPackageList)).setVisibility(View.VISIBLE);
    }

    @Override
    public void onErrorResponse(int requestCode, String message) {
        switch (requestCode)
        {
            case RequestCode.RCR_SEARCH_TRACKING_NUMBER:
                deliveryPackageList.clear();
                deliveryPackageAdapter.notifyDataSetChanged();
                break;

            default:
                break;
        }

        ((RecyclerView) findViewById(R.id.rv_deliveryPackageList)).setVisibility(View.VISIBLE);
    }

    private void volleySearchTrackingNumber(String trackingNumber)
    {
        ((RecyclerView) findViewById(R.id.rv_deliveryPackageList)).setVisibility(View.GONE);

        String accessToken = OAuthPrefHelper.getAccessToken(this);

        Request request = TrackApi.searchTrackingNumber(accessToken, trackingNumber, RequestCode.RCR_SEARCH_TRACKING_NUMBER, this);

        BaseApplication.getInstance().getRequestQueue().add(request);
    }

//    private void populateList(DeliveryPackage deliveryPackage)
    private void populateList(List<DeliveryPackage> tempDeliveryPackageList)
    {
//        if(deliveryPackages != null)
//        {
//            deliveryPackageList.clear();
//            deliveryPackageList.add(deliveryPackage);
//            deliveryPackageAdapter.notifyDataSetChanged();
//        }

        if(deliveryPackageList != null)
        {
            deliveryPackageList.clear();

            if (tempDeliveryPackageList != null) {

                for (DeliveryPackage deliveryPackage : tempDeliveryPackageList) {
                    deliveryPackageList.add(deliveryPackage);
                }

            }

            deliveryPackageAdapter.notifyDataSetChanged();
        }
    }
}
