package com.yilinker.expresspublic.modules.bookDelivery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yilinker.expresspublic.R;
import com.yilinker.expresspublic.core.contants.BundleKey;
import com.yilinker.expresspublic.core.contants.RequestCode;
import com.yilinker.expresspublic.core.deserializer.DateDeserializer;
import com.yilinker.expresspublic.core.requests.EvBookDeliveryReq;
import com.yilinker.expresspublic.core.serializer.DateSerializer;
import com.yilinker.expresspublic.modules.BaseActivity;

import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

/**
 * Created by Jeico.
 */
public class BookDeliveryActivity extends BaseActivity implements Observer, View.OnClickListener {
    private static final Logger logger = Logger.getLogger(BookDeliveryActivity.class.getSimpleName());

    private BookingSyncModel bookingSyncModel;

    private EvBookDeliveryReq evBookDeliveryReq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Register this activity to know if all fields are ready
        bookingSyncModel = new BookingSyncModel();
        bookingSyncModel.addObserver(this);

        // Intialize request parameters
        evBookDeliveryReq = new EvBookDeliveryReq();
    }

    @Override
    protected int getBaseLayout() {
        return R.layout.activity_base_ll;
    }

    @Override
    protected int getToolbarTitle() {
        return R.string.title_book_delivery;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_book_delivery;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK)
        {
            if(requestCode == RequestCode.RCA_SENDER_ADDRESS_LOCATION && data != null)
            {
                handleSenderAddressLocation(data);
            }
            else if(requestCode == RequestCode.RCA_RECIPIENT_ADDRESS_LOCATION && data != null)
            {
                handleRecipientAddressLocation(data);
            }
            else if(requestCode == RequestCode.RCA_PACKAGE_DETAILS && data != null)
            {
                handlePackageDetails(data);
            }
            else if(requestCode == RequestCode.RCA_PACKAGE_SIZE && data != null)
            {
                handlePackageSize(data);
            }
            else if(requestCode == RequestCode.RCA_PICKUP_SCHEDULE && data != null)
            {
                handlePickupSchedule(data);
            }
        }
        else
        {
            // Do nothing
        }
    }

    @Override
    protected void initListeners() {
        // Set onclick listener for FROM group
        findViewById(R.id.ll_from).setOnClickListener(this);
        // Set onclick lister for PACKAGE SIZE group
        findViewById(R.id.ll_packageSize).setOnClickListener(this);

        /**
         * TODO REMOVE THIS
         */
        findViewById(R.id.btn_submitBooking).setOnClickListener(this);
    }

    @Override
    protected Intent resultIntent() {
        return null;
    }

    @Override
    public void update(Observable observable, Object data) {
        Button btn_submitBooking = (Button) findViewById(R.id.btn_submitBooking);
        btn_submitBooking.setBackgroundResource(R.drawable.bg_rect_marigold);
        btn_submitBooking.setTextColor(getColor(android.R.color.white));
        // Set onclick listener for Submit Booking
        findViewById(R.id.btn_submitBooking).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ll_from:
                selectSenderAddressLocation();
                break;

            case R.id.ll_packageSize:
                startDimensionAndWeightActivity();
                break;

            case R.id.btn_submitBooking:
                handleSubmitBooking();
                break;

            default:
                break;
        }
    }

    private void selectSenderAddressLocation() {
        Intent intent = new Intent(this, AddressLocationListActiviy.class);
        startActivityForResult(intent, RequestCode.RCA_SENDER_ADDRESS_LOCATION);
    }

    private void startDimensionAndWeightActivity()
    {
        Intent intent = new Intent(this, DimensionAndWeightActivity.class);
        startActivityForResult(intent, RequestCode.RCA_PACKAGE_SIZE);
    }

    private void handleSubmitBooking() {
        /**
         * TODO
         */
        Toast.makeText(this, "Submit Booking clicked!", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, PickUpScheduleActivity.class);
        startActivity(intent);
    }

    private void handlePickupSchedule(Intent data) {
        bookingSyncModel.setIsPickUpScheduleReady(true);
    }

    private void handlePackageSize(Intent data) {
        bookingSyncModel.setIsPackageSizeReady(true);

        Bundle bundle = data.getExtras();

        String length = bundle.getString(BundleKey.LENGTH);
        String width = bundle.getString(BundleKey.WIDTH);
        String height = bundle.getString(BundleKey.HEIGHT);
        String weight = bundle.getString(BundleKey.WEIGHT);
        String packageContainerUI = bundle.getString(BundleKey.PACKAGE_CONTAINER_UI);
        String weightUI = bundle.getString(BundleKey.WEIGHT_UI);

        evBookDeliveryReq.setLength(length);
        evBookDeliveryReq.setWidth(width);
        evBookDeliveryReq.setHeight(height);
        evBookDeliveryReq.setWeight(weight);

        // Update UI
        ((TextView) findViewById(R.id.tv_packageContainer)).setText(packageContainerUI);
        ((TextView) findViewById(R.id.tv_weight)).setText(weightUI);
        findViewById(R.id.tv_packageSizeLabelMessage).setVisibility(View.GONE);
        findViewById(R.id.ll_packageSizeContainer).setVisibility(View.VISIBLE);
        ((ImageView) findViewById(R.id.iv_packageSizeCheckStatus)).setImageResource(R.drawable.ic_check);
    }

    private void handlePackageDetails(Intent data) {
        bookingSyncModel.setIsPackageDetailsReady(true);
    }

    private void handleRecipientAddressLocation(Intent data) {
        bookingSyncModel.setIsRecipientReady(true);
    }

    private void handleSenderAddressLocation(Intent data) {
        bookingSyncModel.setIsSenderReady(true);
    }
}
