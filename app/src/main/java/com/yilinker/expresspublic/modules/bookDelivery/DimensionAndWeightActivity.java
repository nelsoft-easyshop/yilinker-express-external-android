package com.yilinker.expresspublic.modules.bookDelivery;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yilinker.expresspublic.BaseApplication;
import com.yilinker.expresspublic.R;
import com.yilinker.expresspublic.ResponseHandler;
import com.yilinker.expresspublic.core.api.DeliveryApi;
import com.yilinker.expresspublic.core.contants.BundleKey;
import com.yilinker.expresspublic.core.contants.RequestCode;
import com.yilinker.expresspublic.core.deserializer.DateDeserializer;
import com.yilinker.expresspublic.core.helpers.DialogHelper;
import com.yilinker.expresspublic.core.models.PackageContainer;
import com.yilinker.expresspublic.core.responses.EvPackageContainerResp;
import com.yilinker.expresspublic.core.serializer.DateSerializer;
import com.yilinker.expresspublic.core.utilities.InputValidator;
import com.yilinker.expresspublic.modules.BaseActivity;

import java.util.Date;
import java.util.logging.Logger;

/**
 * Created by Jeico.
 */
public class DimensionAndWeightActivity extends BaseActivity implements View.OnClickListener, ResponseHandler {
    private static final Logger logger = Logger.getLogger(DimensionAndWeightActivity.class.getSimpleName());

    private ProgressDialog progressDialog;

    private String length;
    private String width;
    private String height;
    private String weight;
    private String packageContainerUI;
    private String weightUI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(this);
        handleIntentData();

    }

    @Override
    protected int getBaseLayout() {
        return R.layout.activity_base_ll;
    }

    @Override
    protected int getToolbarTitle() {
        return R.string.title_dimension_and_weight;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_dimension_and_weight;
    }

    @Override
    protected void initListeners() {
        // Set onclick listener for submit
        findViewById(R.id.btn_submit).setOnClickListener(this);
    }

    private void handleIntentData() {

        if(getIntent().getExtras() != null)
        {
            Bundle bundle = getIntent().getExtras();

            ((EditText) findViewById(R.id.et_length)).setText(bundle.getString(BundleKey.LENGTH));
            ((EditText) findViewById(R.id.et_height)).setText(bundle.getString(BundleKey.HEIGHT));
            ((EditText) findViewById(R.id.et_width)).setText(bundle.getString(BundleKey.WIDTH));
            ((EditText) findViewById(R.id.et_weight)).setText(bundle.getString(BundleKey.WEIGHT));

        }
    }


    @Override
    protected Intent resultIntent() {

        Bundle bundle = new Bundle();
        bundle.putString(BundleKey.LENGTH, length);
        bundle.putString(BundleKey.WIDTH, width);
        bundle.putString(BundleKey.HEIGHT, height);
        bundle.putString(BundleKey.WEIGHT, weight);
        bundle.putString(BundleKey.PACKAGE_CONTAINER_UI, packageContainerUI);
        bundle.putString(BundleKey.WEIGHT_UI, weightUI);

        Intent intent = new Intent();
        intent.putExtras(bundle);

        return intent;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_submit:
                handleSubmit();
                break;

            default:
                break;
        }
    }

    private void handleSubmit() {
        String length = ((EditText) findViewById(R.id.et_length)).getText().toString().trim();
        String height = ((EditText) findViewById(R.id.et_height)).getText().toString().trim();
        String width = ((EditText) findViewById(R.id.et_width)).getText().toString().trim();
        String weight = ((EditText) findViewById(R.id.et_weight)).getText().toString().trim();

        String errorMessage = validateUserInput(length, height, width, weight);

        if(errorMessage != null)
        {
            AlertDialog alertDialog = DialogHelper.createOkDialog(this, true, getString(R.string.error), errorMessage);
            alertDialog.show();
        }
        else
        {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

            this.length = length;
            this.height = height;
            this.width = width;
            this.weight = weight;

//            volleyGetDimensionAndWeight(length, height, width, weight);

            //finish activity
            resultCode = RESULT_OK;
            finish();
        }
    }

    private String validateUserInput(String length, String height, String width, String weight)
    {
        String errorMessage = InputValidator.isLengthValid(length);

        if(errorMessage != null)
        {
            return errorMessage;
        }

        errorMessage = InputValidator.isHeightValid(height);

        if(errorMessage != null)
        {
            return errorMessage;
        }

        errorMessage = InputValidator.isWidthValid(width);

        if(errorMessage != null)
        {
            return errorMessage;
        }

        errorMessage = InputValidator.isWeightValid(weight);


        if(errorMessage != null)
        {
            return errorMessage;
        }

        return errorMessage;
    }

    @Override
    public void onResponse(int requestCode, Object object) {
        if(progressDialog.isShowing())
        {
            progressDialog.dismiss();
        }

        switch (requestCode)
        {
            case RequestCode.RCR_GET_PACKAGE_CONTAINER:
                processPackageContainerResp((EvPackageContainerResp) object);
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

        switch (requestCode)
        {
            case RequestCode.RCR_GET_PACKAGE_CONTAINER:
                Toast.makeText(DimensionAndWeightActivity.this, message, Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }

    private void volleyGetDimensionAndWeight(String length, String height, String width, String weight)
    {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading_getting_package_container));
        progressDialog.setCancelable(false);
        progressDialog.show();

        Request request = DeliveryApi.getPackageContainer(width, height, length, weight, RequestCode.RCR_GET_PACKAGE_CONTAINER, this);
        BaseApplication.getInstance().getRequestQueue().add(request);
    }


    private void processPackageContainerResp(EvPackageContainerResp object) {
        PackageContainer packageContainer = object.data;

        packageContainerUI = packageContainer.getPackageContainer();
        weightUI = packageContainer.getWeight();

        resultCode = RESULT_OK;
        finish();
    }
}
