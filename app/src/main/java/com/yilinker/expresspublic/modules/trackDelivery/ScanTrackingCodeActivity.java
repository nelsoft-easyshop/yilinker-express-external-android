package com.yilinker.expresspublic.modules.trackDelivery;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PointF;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
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
import com.yilinker.expresspublic.core.models.DeliveryPackage;
import com.yilinker.expresspublic.core.responses.EvDeliveryPackageListResp;
import com.yilinker.expresspublic.core.responses.EvDeliveryPackageResp;
import com.yilinker.expresspublic.core.serializer.DateSerializer;
import com.yilinker.expresspublic.modules.BaseActivity;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Jeico.
 */
public class ScanTrackingCodeActivity extends BaseActivity implements QRCodeReaderView.OnQRCodeReadListener, View.OnClickListener, ResponseHandler {
    private static final Logger logger = Logger.getLogger(ScanTrackingCodeActivity.class.getSimpleName());

    private QRCodeReaderView mydecoderview;

    private ProgressDialog progressDialog;

    private boolean isOngoingRequest = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        progressDialog = new ProgressDialog(this);
//
//        mydecoderview = (QRCodeReaderView) findViewById(R.id.qrdecoderview);
//        mydecoderview.setOnQRCodeReadListener(this);
//
        initViews();
    }

    private void initViews() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        mydecoderview = (QRCodeReaderView) findViewById(R.id.qrdecoderview);
        mydecoderview.setOnQRCodeReadListener(this);

    }

    @Override
    protected int getBaseLayout() {
        return R.layout.activity_base_ll;
    }

    @Override
    protected int getToolbarTitle() {
        return R.string.title_scan_tracking_code;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_scan_tracking_code;
    }

    @Override
    protected void initListeners() {
        findViewById(R.id.ibtn_torch).setOnClickListener(this);
    }

    @Override
    protected Intent resultIntent() {
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
//        mydecoderview.getCameraManager().startPreview();
//        isOngoingRequest = false;
        startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mydecoderview.getCameraManager().stopPreview();
        isOngoingRequest = false;
    }

    @Override
    public void onQRCodeRead(final String trackingNumber, PointF[] pointFs) {
        mydecoderview.getCameraManager().stopPreview();

        if(!isOngoingRequest)
        {
            isOngoingRequest = true;
            volleySearchTrackingNumber(trackingNumber);
        }
    }

    @Override
    public void cameraNotFound() {

    }

    @Override
    public void QRCodeNotFoundOnCamImage() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ibtn_torch:
                handleTorchSwitch();
                break;

            default:
                break;
        }
    }

    @Override
    public void onResponse(int requestCode, Object object) {
        if(progressDialog.isShowing())
        {
            progressDialog.dismiss();
        }

        mydecoderview.getCameraManager().stopPreview();

        switch (requestCode)
        {
            case RequestCode.RCR_SEARCH_TRACKING_NUMBER:
                EvDeliveryPackageListResp evDeliveryPackageListResp = (EvDeliveryPackageListResp) object;
                startTrackDetailsActivity(evDeliveryPackageListResp.data);
//                EvDeliveryPackageResp evDeliveryPackageResp = (EvDeliveryPackageResp) object;
//                startTrackDetailsActivity(evDeliveryPackageResp.data);
                break;

            default:
                break;
        }
    }

    @Override
    public void onErrorResponse(int requestCode, String message) {
        if(progressDialog.isShowing())
        {
            progressDialog.dismiss();
        }

//        mydecoderview.getCameraManager().startPreview();
//        isOngoingRequest = false;
        startCamera();

        switch (requestCode)
        {
            case RequestCode.RCR_SEARCH_TRACKING_NUMBER:
                Toast.makeText(ScanTrackingCodeActivity.this, message, Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }


    private void volleySearchTrackingNumber(String trackingNumber)
    {
        // Show loading
//        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading_tracking));
//        progressDialog.setCancelable(false);
        progressDialog.show();

        String accessToken = OAuthPrefHelper.getAccessToken(this);

        Request request = TrackApi.searchTrackingNumber(accessToken, trackingNumber, RequestCode.RCR_SEARCH_TRACKING_NUMBER, this);
        BaseApplication.getInstance().getRequestQueue().add(request);
    }

    private void handleTorchSwitch() {
        if(mydecoderview.getCameraManager().isOpen())
        {
            Camera.Parameters params = mydecoderview.getCameraManager().getCamera().getParameters();
            if(params != null)
            {
                if(params.getFlashMode().equals(Camera.Parameters.FLASH_MODE_TORCH))
                {
                    params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                }
                else
                {
                    params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                }

                mydecoderview.getCameraManager().getCamera().setParameters(params);
            }
        }
    }

//    private void startTrackDetailsActivity(DeliveryPackage data) {
        private void startTrackDetailsActivity(List<DeliveryPackage> tempDeliveryPackageList) {

            if (tempDeliveryPackageList != null && tempDeliveryPackageList.size() != 0) {

                DeliveryPackage data = tempDeliveryPackageList.get(0);

                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(Date.class, new DateDeserializer())
                        .registerTypeAdapter(Date.class, new DateSerializer())
                        .create();

                String deliveryPackageRaw = gson.toJson(data);

                Bundle bundle = new Bundle();
                bundle.putString(BundleKey.DELIVERY_PACKAGE, deliveryPackageRaw);

                Intent intent = new Intent(this, TrackDetailsActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            } else {

                Toast.makeText(this, getString(R.string.error_invalid_tracking_number), Toast.LENGTH_SHORT).show();
                startCamera();

            }
    }

    private void startCamera() {
        mydecoderview.getCameraManager().startPreview();

        isOngoingRequest = false;
    }
}
