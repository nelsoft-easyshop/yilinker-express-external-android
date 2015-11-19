package com.yilinker.expresspublic.modules.bookDelivery;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yilinker.expresspublic.BaseApplication;
import com.yilinker.expresspublic.BuildConfig;
import com.yilinker.expresspublic.R;
import com.yilinker.expresspublic.ResponseHandler;
import com.yilinker.expresspublic.core.BookDeliveryRequest;
import com.yilinker.expresspublic.core.api.DeliveryApi;
import com.yilinker.expresspublic.core.contants.ApiEndpoint;
import com.yilinker.expresspublic.core.contants.BundleKey;
import com.yilinker.expresspublic.core.contants.RequestCode;
import com.yilinker.expresspublic.core.helpers.OAuthPrefHelper;
import com.yilinker.expresspublic.core.models.Address;
import com.yilinker.expresspublic.core.models.DeliveryPackage;
import com.yilinker.expresspublic.core.models.PickUpSchedule;
import com.yilinker.expresspublic.core.requests.EvBookDeliveryReq;
import com.yilinker.expresspublic.core.responses.EvBookDeliveryResponse;
import com.yilinker.expresspublic.core.utilities.CommonUtils;
import com.yilinker.expresspublic.core.utilities.DateUtils;
import com.yilinker.expresspublic.modules.BaseActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

/**
 * Created by Jeico.
 */
public class BookDeliveryActivity extends BaseActivity implements Observer, View.OnClickListener, ResponseHandler {
    private static final Logger logger = Logger.getLogger(BookDeliveryActivity.class.getSimpleName());

    private ProgressDialog progressDialog;

    private BookingSyncModel bookingSyncModel;

    private EvBookDeliveryReq evBookDeliveryReq;

    private BookDeliveryRequest bookDeliveryRequest;

    private String waybillNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Register this activity to know if all fields are ready
        bookingSyncModel = new BookingSyncModel();
        bookingSyncModel.addObserver(this);

//        bookingSyncModel.setIsRecipientReady(true);
//        bookingSyncModel.setIsSenderReady(true);
//        bookingSyncModel.setIsPackageDetailsReady(true);
//        bookingSyncModel.setIsPackageSizeReady(true);

        // Intialize request parameters
        evBookDeliveryReq = new EvBookDeliveryReq();

//        evBookDeliveryReq.setSenderAddressId(Long.valueOf(125));
//        evBookDeliveryReq.setRecipientAddressId(Long.valueOf(298));
//        evBookDeliveryReq.setPaidBy("true");
//        evBookDeliveryReq.setDeclaredValue("1000");
//        evBookDeliveryReq.setQuantity(10);
//        evBookDeliveryReq.setHeight("100");
//        evBookDeliveryReq.setWidth("100");
//        evBookDeliveryReq.setLength("100");
//        evBookDeliveryReq.setWeight("100");
//        evBookDeliveryReq.setPackageName("Test");

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
        // Set onclick listener for Submit Booking
        findViewById(R.id.btn_submitBooking).setOnClickListener(this);
        // Set onclick listener for FROM group
        findViewById(R.id.ll_from).setOnClickListener(this);
        // Set onclick listener for TO group
        findViewById(R.id.ll_to).setOnClickListener(this);
        // Set onclick listener for PACKAGE DETAILS
        findViewById(R.id.ll_packageDetails).setOnClickListener(this);
        // Set onclick listener for PACKAGE SIZE group
        findViewById(R.id.ll_packageSize).setOnClickListener(this);
        // Set onclick listener for PICKUP SCHEDULE
        findViewById(R.id.ll_pickupSchedule).setOnClickListener(this);

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
        btn_submitBooking.setTextColor(Color.parseColor("#FFFFFF"));
        btn_submitBooking.setEnabled(true);
        int padding = CommonUtils.convertDpToPixels(this, 24);
        btn_submitBooking.setPadding(0, padding, 0, padding);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ll_from:
                startSenderAddressLocationActivity();
                break;

            case R.id.ll_to:
                startRecipientLocationActivity();
                break;

            case R.id.ll_packageDetails:
                startPackageDetailsActivity();
                break;

            case R.id.ll_packageSize:
                startDimensionAndWeightActivity();
                break;

            case R.id.ll_pickupSchedule:
                startPickUpScheduleActivity();
                break;

            case R.id.btn_submitBooking:
                handleSubmitBooking();
                break;

            default:
                break;
        }
    }

    private void startRecipientLocationActivity() {
        Intent intent = new Intent(this, RecipientLocationListActivity.class);
        startActivityForResult(intent, RequestCode.RCA_RECIPIENT_ADDRESS_LOCATION);
    }

    private void startPackageDetailsActivity() {
        Intent intent = new Intent(this, PackageDetailsActivity2.class);
        startActivityForResult(intent, RequestCode.RCA_PACKAGE_DETAILS);
    }

    private void startPickUpScheduleActivity() {
        Intent intent = new Intent(this, PickUpScheduleActivity.class);
        startActivityForResult(intent, RequestCode.RCA_PICKUP_SCHEDULE);
    }

    private void startSenderAddressLocationActivity() {
        Intent intent = new Intent(this, AddressLocationListActiviy.class);
        startActivityForResult(intent, RequestCode.RCA_SENDER_ADDRESS_LOCATION);
    }

    private void startDimensionAndWeightActivity()
    {
        Intent intent = new Intent(this, DimensionAndWeightActivity.class);
        startActivityForResult(intent, RequestCode.RCA_PACKAGE_SIZE);
    }

    private void handleSubmitBooking() {

//        String endpoint = BuildConfig.DOMAIN + "/"
//                + ApiEndpoint.DELIVERY_API + "/"
//                + ApiEndpoint.DELIVERY_BOOK;

//        String endpoint = BuildConfig.DOMAIN + "/"
//                + ApiEndpoint.PACKAGES_API + "/"
//                + ApiEndpoint.PACKAGES_ADD;


//        bookDeliveryRequest = new BookDeliveryRequest(
//                this,
//                endpoint,
//                OAuthPrefHelper.getAccessToken(this),
//                evBookDeliveryReq,
//                new BookDeliveryRequest.BookingDeliveryListener() {
//                    @Override
//                    public void onBookingSuccessful(String response) {
//                        handleBookDeliveryResponse(response);
//                    }
//
//                    @Override
//                    public void onBookingFailed(String message) {
//                        Toast.makeText(BookDeliveryActivity.this, message, Toast.LENGTH_SHORT).show();
//                    }
//        });
//        bookDeliveryRequest.execute();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading_booking_delivery));
        progressDialog.setCancelable(false);
        progressDialog.show();

        Request request = DeliveryApi.bookDelivery(RequestCode.RCR_BOOK_DELIVERY, OAuthPrefHelper.getAccessToken(this), evBookDeliveryReq, this);
        BaseApplication.getInstance().getRequestQueue().add(request);
    }

    private void handleBookDeliveryResponse(String response) {
        Bundle bundle = new Bundle();
//        bundle.putString(BundleKey.BOOK_DELIVERY_RESPONSE, response);
        bundle.putString(BundleKey.WAYBILL_NUMBER, waybillNumber);

        Intent intent = new Intent(this, BookingSuccessFulActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);

        finish();
    }

    private void handleUploadWaybillImages(String waybillNumber) {

        String endpoint = BuildConfig.DOMAIN + "/"
                + ApiEndpoint.PACKAGES_API + "/"
                + ApiEndpoint.PACKAGES_IMAGES + "/"
                + ApiEndpoint.PACKAGES_ADD + "?access_token="
                + OAuthPrefHelper.getAccessToken(this);

        bookDeliveryRequest = new BookDeliveryRequest(
                this,
                endpoint,
                OAuthPrefHelper.getAccessToken(this),
                evBookDeliveryReq.getImages(), waybillNumber,
                new BookDeliveryRequest.BookingDeliveryListener() {
                    @Override
                    public void onBookingSuccessful(String response) {
                        handleBookDeliveryResponse(response);
                    }

                    @Override
                    public void onBookingFailed(String message) {
                        Toast.makeText(BookDeliveryActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                });
        bookDeliveryRequest.execute();

    }

    private void handlePickupSchedule(Intent data) {
        bookingSyncModel.setIsPickUpScheduleReady(true);

        Bundle bundle = data.getExtras();

        Date pickUpDate = (Date) bundle.getSerializable(BundleKey.PICKUP_DATE);
        String pickUpScheduleRaw = bundle.getString(BundleKey.PACKAGE_PICKUP_SCHEDULE);

        Gson gson = new GsonBuilder().create();
        PickUpSchedule pickUpSchedule = gson.fromJson(pickUpScheduleRaw, PickUpSchedule.class);

        evBookDeliveryReq.setPickUpDate(pickUpDate);
        evBookDeliveryReq.setPackagePickupScheduleId(pickUpSchedule.getId());

        // Update UI
        ((TextView) findViewById(R.id.tv_pickUpDate)).setText(DateUtils.displayDateAsReadable(pickUpDate));
        ((TextView) findViewById(R.id.tv_pickUpTime)).setText(pickUpSchedule.getSchedule());
        findViewById(R.id.tv_pickupScheduleLabelMessage).setVisibility(View.GONE);
        findViewById(R.id.ll_pickupScheduleContainer).setVisibility(View.VISIBLE);
        ((ImageView) findViewById(R.id.iv_pickupScheduleCheckStatus)).setImageResource(R.drawable.ic_check);
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
        ((TextView) findViewById(R.id.tv_packageContainer)).setText(R.string.declared_weight);
        ((TextView) findViewById(R.id.tv_weight)).setText(String.format("%s%s", weight, "kg"));
        ((ImageView) findViewById(R.id.iv_packageSizeCheckStatus)).setImageResource(R.drawable.ic_check);
        findViewById(R.id.tv_packageSizeLabelMessage).setVisibility(View.GONE);
        findViewById(R.id.ll_packageSizeContainer).setVisibility(View.VISIBLE);
    }

    private void handlePackageDetails(Intent data) {
        bookingSyncModel.setIsPackageDetailsReady(true);

        Bundle bundle = data.getExtras();

        String packageName = bundle.getString(BundleKey.PACKAGE_NAME);
        String declaredValue = bundle.getString(BundleKey.DECLARED_VALUE);
        int quantity = bundle.getInt(BundleKey.QUANTITY);
        String paidBy = bundle.getString(BundleKey.PAID_BY);
        boolean isFragile = bundle.getBoolean(BundleKey.IS_FRAGILE);
        List<String> photoFilepathList = bundle.getStringArrayList(BundleKey.PHOTO_FILEPATH_LIST);
        if(photoFilepathList == null)
        {
            photoFilepathList = new ArrayList<>();
        }

        evBookDeliveryReq.setPackageName(packageName);
        evBookDeliveryReq.setDeclaredValue(declaredValue);
        evBookDeliveryReq.setQuantity(quantity);
        evBookDeliveryReq.setImages(photoFilepathList);
        evBookDeliveryReq.setFragile(isFragile);
//        evBookDeliveryReq.setPaidBy(paidBy);
        if(paidBy.equals("sender"))
        {
            evBookDeliveryReq.setPaidBy("true");
        } else
        {
            evBookDeliveryReq.setPaidBy("false");
        }

        // Update UI
        ((TextView) findViewById(R.id.tv_packageName)).setText(packageName);
        ((TextView) findViewById(R.id.tv_declaredValue)).setText("Declared Value " + declaredValue);
        ((TextView) findViewById(R.id.tv_imageCount)).setText(photoFilepathList.size() + " Images uploaded");
        ((TextView) findViewById(R.id.tv_paidBy)).setText("Paid by " + paidBy);
        ((ImageView) findViewById(R.id.iv_packageDetailsCheckStatus)).setImageResource(R.drawable.ic_check);

        findViewById(R.id.tv_packageDetailsLabelMessage).setVisibility(View.GONE);
        findViewById(R.id.ll_packageDetailsContainer).setVisibility(View.VISIBLE);
    }

    private void handleRecipientAddressLocation(Intent data) {
        bookingSyncModel.setIsRecipientReady(true);

        Bundle bundle = data.getExtras();

        String addressJson = bundle.getString(BundleKey.ADDRESS);
        Address address = new GsonBuilder().create().fromJson(addressJson, Address.class);


//        evBookDeliveryReq.setRecipientConsumerId(address.getConsumerId());
//        evBookDeliveryReq.setRecipientAddressId(address.getAddressId());
        evBookDeliveryReq.setRecipientConsumerAddressId(address.getId());

        // Update UI
        ((TextView) findViewById(R.id.tv_recipientName)).setText(address.getContactPerson());
        ((TextView) findViewById(R.id.tv_recipientContactNumber)).setText(address.getContactPersonNumber());
        ((TextView) findViewById(R.id.tv_recipientAddress)).setText(address.getAddress());
        ((ImageView) findViewById(R.id.iv_recipientCheckStatus)).setImageResource(R.drawable.ic_check);
        findViewById(R.id.tv_toLabelMessage).setVisibility(View.GONE);
        findViewById(R.id.ll_toContainer).setVisibility(View.VISIBLE);
    }

    private void handleSenderAddressLocation(Intent data) {
        bookingSyncModel.setIsSenderReady(true);

        Bundle bundle = data.getExtras();

        String addressJson = bundle.getString(BundleKey.ADDRESS);
        Address address = new GsonBuilder().create().fromJson(addressJson, Address.class);


//        evBookDeliveryReq.setSenderConsumerId(address.getConsumerId());
//        evBookDeliveryReq.setSenderAddressId(address.getAddressId());
        evBookDeliveryReq.setSenderConsumerAddressId(address.getId());

        // Update UI
        ((TextView) findViewById(R.id.tv_senderName)).setText(address.getContactPerson());
        ((TextView) findViewById(R.id.tv_senderContactNumber)).setText(address.getContactPersonNumber());
        ((TextView) findViewById(R.id.tv_senderAddress)).setText(address.getAddress());
        ((ImageView) findViewById(R.id.iv_senderCheckStatus)).setImageResource(R.drawable.ic_check);
        findViewById(R.id.tv_fromLabelMessage).setVisibility(View.GONE);
        findViewById(R.id.ll_fromContainer).setVisibility(View.VISIBLE);
    }

    private String processWaybillNumber(EvBookDeliveryResponse object) {

        EvBookDeliveryResponse.Data deliveryResponse = object.data;

        return deliveryResponse.waybillNumber;
    }

    @Override
    public void onResponse(int requestCode, Object object) {

        if(progressDialog.isShowing())
        {
            progressDialog.dismiss();
        }

        switch(requestCode)
        {
            case RequestCode.RCR_BOOK_DELIVERY:
                waybillNumber = processWaybillNumber((EvBookDeliveryResponse) object);
                handleUploadWaybillImages(waybillNumber);
                break;
        }
    }

    @Override
    public void onErrorResponse(int requestCode, String message) {

        if(progressDialog.isShowing())
        {
            progressDialog.dismiss();
        }

        Toast.makeText(this.getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}
