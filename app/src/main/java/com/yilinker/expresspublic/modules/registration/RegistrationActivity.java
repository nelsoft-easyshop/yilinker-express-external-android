package com.yilinker.expresspublic.modules.registration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.yilinker.expresspublic.BaseApplication;
import com.yilinker.expresspublic.R;
import com.yilinker.expresspublic.ResponseHandler;
import com.yilinker.expresspublic.core.api.OAuthApi;
import com.yilinker.expresspublic.core.contants.RequestCode;
import com.yilinker.expresspublic.core.helpers.CommonPrefHelper;
import com.yilinker.expresspublic.core.helpers.DialogHelper;
import com.yilinker.expresspublic.core.helpers.OAuthPrefHelper;
import com.yilinker.expresspublic.core.models.OAuth;
import com.yilinker.expresspublic.core.responses.EvRegisterResp;
import com.yilinker.expresspublic.core.responses.bases.EvBaseResp;
import com.yilinker.expresspublic.core.utilities.InputValidator;
import com.yilinker.expresspublic.modules.BaseActivity;

import java.util.logging.Logger;

/**
 * Created by Jeico on 8/30/2015.
 */
public class RegistrationActivity extends BaseActivity implements View.OnClickListener, ResponseHandler {
    private static final Logger logger = Logger.getLogger(RegistrationActivity.class.getSimpleName());

    private ProgressDialog progressDialog;

    private String currentEmailAddress;
    private String currentPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(this);

        // Remove toolbar elements
        (toolbar.findViewById(R.id.toolbar_avatar)).setVisibility(View.GONE);
    }

    @Override
    protected int getBaseLayout() {
        return R.layout.activity_base_ll;
    }

    @Override
    protected int getToolbarTitle() {
        return R.string.title_registration;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_registration;
    }

    @Override
    protected void initListeners() {
        findViewById(R.id.btn_submitAndUpdate).setOnClickListener(this);
    }

    @Override
    protected Intent resultIntent() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_submitAndUpdate:
                handleSubmit();
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

        switch (requestCode)
        {
            case RequestCode.RCR_REGISTER:
                EvBaseResp evBaseResp = (EvBaseResp) object;
                volleyToken(currentEmailAddress, currentPassword);
                break;

            case RequestCode.RCR_TOKEN:
                OAuth oAuth = (OAuth) object;
                // Save OAuth data in preferences
                OAuthPrefHelper.setOAuth(this, oAuth);
                // Set login status
                CommonPrefHelper.setLoginStatus(this, true);
                // Set resultCode to OK
                resultCode = RESULT_OK;
                finish();
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
            case RequestCode.RCR_REGISTER:
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                break;

            case RequestCode.RCR_TOKEN:
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                break;

            default:
                break;
        }
    }

    private void handleSubmit() {

        String firstname = ((EditText) findViewById(R.id.et_firstname)).getText().toString().trim();
        String lastname = ((EditText) findViewById(R.id.et_lastname)).getText().toString().trim();
        String emailAddress = ((EditText) findViewById(R.id.et_email)).getText().toString().trim();
        String contactNumber = ((EditText) findViewById(R.id.et_contact)).getText().toString().trim();
        String password = ((EditText) findViewById(R.id.et_password)).getText().toString().trim();

        String errorMessage = validateUserInput(firstname, lastname, emailAddress, contactNumber, password);

        if(errorMessage != null)
        {
            AlertDialog alertDialog = DialogHelper.createOkDialog(this, true, getString(R.string.error), errorMessage);
            alertDialog.show();
        }
        else
        {

            // Initially hide keyboard
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            volleyRegister(firstname, lastname, emailAddress, contactNumber, password);
        }
    }

    private void volleyRegister(String firstname, String lastname, String emailAddress, String contactNumber, String password)
    {
        currentEmailAddress = emailAddress;
        currentPassword = password;

        // Show loading
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading_registration));
        progressDialog.setCancelable(false);
        progressDialog.show();

        // Make request
        Request request = OAuthApi.register(firstname, lastname, emailAddress, contactNumber, password, RequestCode.RCR_REGISTER, this);
        BaseApplication.getInstance().getRequestQueue().add(request);
    }

    private void volleyToken(String emailAddress, String password)
    {
        // Show loading
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading_login));
        progressDialog.setCancelable(false);
        progressDialog.show();

        Request request = OAuthApi.token(emailAddress, password, RequestCode.RCR_TOKEN, this);
        BaseApplication.getInstance().getRequestQueue().add(request);
    }

    private String validateUserInput(String firstname, String lastname, String emailAddress, String contactNumber, String password)
    {
        // Check firstname
        String errorMessage = InputValidator.isFirstnameValid(firstname);

        if(errorMessage != null)
        {
            return errorMessage;
        }

        // Check lastname
        errorMessage = InputValidator.isLastnameValid(lastname);

        if(errorMessage != null)
        {
            return errorMessage;
        }

        // Check email
        errorMessage = InputValidator.isEmailValid(emailAddress);

        if(errorMessage != null)
        {
            return errorMessage;
        }

        // Check contact
        errorMessage = InputValidator.isContactNumberValid(contactNumber);

        if(errorMessage != null)
        {
            return errorMessage;
        }

        // Check password
        errorMessage = InputValidator.isPasswordValid(password);

        if(errorMessage != null)
        {
            return errorMessage;
        }

        return errorMessage;
    }
}
