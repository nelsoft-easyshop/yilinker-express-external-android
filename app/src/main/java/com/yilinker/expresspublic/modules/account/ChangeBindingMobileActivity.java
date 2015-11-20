package com.yilinker.expresspublic.modules.account;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.yilinker.expresspublic.BaseApplication;
import com.yilinker.expresspublic.R;
import com.yilinker.expresspublic.ResponseHandler;
import com.yilinker.expresspublic.core.api.UserApi;
import com.yilinker.expresspublic.core.contants.RequestCode;
import com.yilinker.expresspublic.core.helpers.DialogHelper;
import com.yilinker.expresspublic.core.helpers.OAuthPrefHelper;
import com.yilinker.expresspublic.core.helpers.UserPrefHelper;
import com.yilinker.expresspublic.core.models.User;
import com.yilinker.expresspublic.core.responses.EvMeResp;
import com.yilinker.expresspublic.core.utilities.CommonUtils;
import com.yilinker.expresspublic.core.utilities.InputValidator;
import com.yilinker.expresspublic.modules.BaseActivity;

import java.util.logging.Logger;

/**
 * Created by Jeico.
 */
public class ChangeBindingMobileActivity
        extends BaseActivity
        implements View.OnClickListener, ResponseHandler
{

    private static final Logger logger = Logger.getLogger(ChangeBindingMobileActivity.class.getSimpleName());

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(this);

        // Fill in old number
        String oldNumber = UserPrefHelper.getUser(this).getContactNumber();
        ((EditText) findViewById(R.id.et_oldMobile)).setText(oldNumber);
    }

    @Override
    protected int getBaseLayout()
    {
        return R.layout.activity_base_ll;
    }

    @Override
    protected int getToolbarTitle()
    {
        return R.string.title_change_binding_mobile;
    }

    @Override
    protected int getLayoutResource()
    {
        return R.layout.activity_change_binding_mobile;
    }

    @Override
    protected void initListeners()
    {
        // Set onclick listener for confirm entries
        findViewById(R.id.btn_confirmEntries).setOnClickListener(this);
        // Set onclick listener for resend verification code
        findViewById(R.id.btn_resendVerificationCode).setOnClickListener(this);
        // Set onclick listener for submit and update
        findViewById(R.id.btn_submitAndUpdate).setOnClickListener(this);
    }

    @Override
    protected Intent resultIntent()
    {
        return null;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_confirmEntries:
                handleConfirmEntries();
                break;

            case R.id.btn_resendVerificationCode:
                volleyGetVerificationCode();
                break;

            case R.id.btn_submitAndUpdate:
                handleSubmitAndUpdate();
                break;

            default:
                break;
        }
    }

    @Override
    public void onResponse(int requestCode, Object object)
    {
        if (progressDialog.isShowing())
        {
            progressDialog.dismiss();
        }

        switch (requestCode)
        {
            case RequestCode.RCR_UPDATE_MOBILE:
                EvMeResp evMeResp = (EvMeResp) object;

                if (evMeResp.isSuccessful)
                    handleUpdateMobileResp(evMeResp.data, getString(R.string.profile_message_mobile_successful));
                else
                    Toast.makeText(this, evMeResp.message, Toast.LENGTH_LONG).show();

                break;

            case RequestCode.RCR_VERIFY_MOBILE:
                handleVerifyMobileResp();
                break;

            default:
                break;
        }
    }

    @Override
    public void onErrorResponse(int requestCode, String message)
    {
        if (progressDialog.isShowing())
        {
            progressDialog.dismiss();
        }

        switch (requestCode)
        {
            case RequestCode.RCR_UPDATE_MOBILE:
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                break;

            case RequestCode.RCR_VERIFY_MOBILE:
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                break;

            default:
                break;
        }
    }

    private void volleyGetVerificationCode()
    {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading_requesting_verification_code));
        progressDialog.setCancelable(false);
        progressDialog.show();

        // Get new contact number
        String newContactNumber = ((EditText) findViewById(R.id.et_newMobile)).getText().toString().trim();

        Request request = UserApi.verifyMobile(OAuthPrefHelper.getAccessToken(this), newContactNumber, RequestCode.RCR_VERIFY_MOBILE, this);
        BaseApplication.getInstance().getRequestQueue().add(request);
    }

    private void handleVerifyMobileResp()
    {
        findViewById(R.id.btn_confirmEntries).setVisibility(View.GONE);
        findViewById(R.id.ll_enterVerificationCode).setVisibility(View.VISIBLE);

        // Disable new mobile
        findViewById(R.id.et_newMobile).setEnabled(false);
        // Enable submit and update button
        Button btn_submitAndUpdate = (Button) findViewById(R.id.btn_submitAndUpdate);
        btn_submitAndUpdate.setEnabled(true);
        btn_submitAndUpdate.setBackgroundResource(R.drawable.bg_rect_marigold);
        int padding = CommonUtils.convertDpToPixels(this, 24);
        btn_submitAndUpdate.setPadding(0, padding, 0, padding);
    }

    private void handleSubmitAndUpdate()
    {
//        String verificationCode = ((EditText) findViewById(R.id.et_verificationCode)).getText().toString().trim();
//
//        if (TextUtils.isEmpty(verificationCode))
//        {
//            Toast.makeText(this, getString(R.string.error_invalid_verification_code), Toast.LENGTH_LONG).show();
//        }
//        else
//        {
//            volleyUpdateMobile();
//        }
        String contactNumber = ((EditText) findViewById(R.id.et_newMobile)).getText().toString().trim();

        String errorMessage = validateUserInput(contactNumber);

        if(errorMessage != null)
        {
            AlertDialog alertDialog = DialogHelper.createOkDialog(this, false, getString(R.string.error), errorMessage);
            alertDialog.show();
        }
        else
        {
            volleyUpdateMobile();
        }
    }

    private void handleUpdateMobileResp(User user, String message)
    {
        UserPrefHelper.setUser(this, user);
        Toast.makeText(ChangeBindingMobileActivity.this, message, Toast.LENGTH_SHORT).show();
        finish();
    }

    private void volleyUpdateMobile()
    {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading_updating_mobile));
        progressDialog.setCancelable(false);
        progressDialog.show();

        // Get new contact number
        String newContactNumber = ((EditText) findViewById(R.id.et_newMobile)).getText().toString().trim();
        // Get verification code
        String verificationCode = ((EditText) findViewById(R.id.et_verificationCode)).getText().toString().trim();

        Request request = UserApi.updateMobile(OAuthPrefHelper.getAccessToken(this), newContactNumber, verificationCode, RequestCode.RCR_UPDATE_MOBILE, this);
        BaseApplication.getInstance().getRequestQueue().add(request);
    }


    private void handleConfirmEntries()
    {

        // Get new contact number
        String newContactNumber = ((EditText) findViewById(R.id.et_newMobile)).getText().toString().trim();

        if (TextUtils.isEmpty(newContactNumber))
        {
            Toast.makeText(ChangeBindingMobileActivity.this, getString(R.string.error_invalid_mobile_number), Toast.LENGTH_SHORT).show();
        }
        else
        {
            volleyGetVerificationCode();
        }
    }

    private String validateUserInput(String contactNumber) {

        String errorMessage = InputValidator.isNewContactNumberValid(contactNumber);

        if(errorMessage != null)
        {
            return errorMessage;
        }

        errorMessage = InputValidator.isContactNumberAtLeastEleven(contactNumber);

        if(errorMessage != null)
        {
            return errorMessage;
        }

        return errorMessage;
    }
}
