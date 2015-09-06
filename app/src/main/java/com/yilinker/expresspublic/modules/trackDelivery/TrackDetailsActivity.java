package com.yilinker.expresspublic.modules.trackDelivery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yilinker.expresspublic.R;
import com.yilinker.expresspublic.core.contants.BundleKey;
import com.yilinker.expresspublic.core.deserializer.DateDeserializer;
import com.yilinker.expresspublic.core.models.DeliveryPackage;
import com.yilinker.expresspublic.core.models.DeliveryStatus;
import com.yilinker.expresspublic.core.serializer.DateSerializer;
import com.yilinker.expresspublic.modules.BaseActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Jeico.
 */
public class TrackDetailsActivity extends BaseActivity {
    private static final Logger logger = Logger.getLogger(TrackDetailsActivity.class.getSimpleName());

    private DeliveryStatusAdapter deliveryStatusAdapter;

    private List<DeliveryStatus> deliveryStatusList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        String deliveryPackageRaw = bundle.getString(BundleKey.DELIVERY_PACKAGE);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateDeserializer())
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create();

        DeliveryPackage deliveryPackage = gson.fromJson(deliveryPackageRaw, DeliveryPackage.class);
        // Tracking Number
        ((TextView) findViewById(R.id.tv_trackingNumber)).setText(deliveryPackage.getTrackingNumber());
        // Origin to destination
        ((TextView) findViewById(R.id.tv_originToDestination)).setText(deliveryPackage.getOriginToDestination());
        // Shipping type
        ((TextView) findViewById(R.id.tv_shippingType)).setText(deliveryPackage.getShippingType());
        // Status
        ((TextView) findViewById(R.id.tv_status)).setText(deliveryPackage.getStatus());
        // Status Remarks
        ((TextView) findViewById(R.id.tv_statusRemarks)).setText(deliveryPackage.getStatusRemarks());
        // Status "Delivered" icon
        if(deliveryPackage.getStatus().contains("Delivered"))
        {
            ((ImageView) findViewById(R.id.iv_checkDelivered)).setVisibility(View.VISIBLE);
        }

        if(deliveryPackage.getDeliveryStatusList() != null)
        {
            deliveryStatusList = deliveryPackage.getDeliveryStatusList();
        }
        else
        {
            deliveryStatusList = new ArrayList<>();
        }

        logger.severe("size: " + deliveryStatusList.size());

        Collections.sort(deliveryStatusList, DeliveryStatus.sortDateDesc);

        // Initialize adapter
        deliveryStatusAdapter = new DeliveryStatusAdapter(this, deliveryStatusList);

        // Delivery Message List
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_deliveryStatusList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(deliveryStatusAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected int getBaseLayout() {
        return R.layout.activity_base_ll;
    }

    @Override
    protected int getToolbarTitle() {
        return R.string.title_track_details;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_track_details;
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected Intent resultIntent() {
        return null;
    }
}
