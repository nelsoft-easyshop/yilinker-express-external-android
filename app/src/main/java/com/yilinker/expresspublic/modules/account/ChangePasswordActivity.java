package com.yilinker.expresspublic.modules.account;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AlertDialog;
import android.view.View;
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
import com.yilinker.expresspublic.core.responses.bases.EvBaseResp;
import com.yilinker.expresspublic.core.utilities.InputValidator;
import com.yilinker.expresspublic.modules.BaseActivity;

import java.util.logging.Logger;

/**
 * Created by Jeico.
 */
public class ChangePasswordActivity extends BaseActivity implements View.OnClickListener, ResponseHandler {
    private static final Logger logger = Logger.getLogger(ChangePasswordActivity.class.getSimpleName());

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(this);
    }

    @Override
    protected int getBaseLayout() {
        return R.layout.activity_base_ll;
    }

    @Override
    protected int getToolbarTitle() {
        return R.string.title_change_password;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_change_password;
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
                handleSubmitAndUpdate();
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
            case RequestCode.RCR_UPDATE_PASSWORD:
                EvBaseResp evBaseResp = (EvBaseResp) object;
                //processUpdatePasswordResp(evMeResp);
                if (evBaseResp.isSuccessful)
                    Toast.makeText(this, getString(R.string.profile_message_password_successful), Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(this, evBaseResp.message, Toast.LENGTH_LONG).show();
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
            case RequestCode.RCR_UPDATE_PASSWORD:
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                break;

            default:
                break;
        }
    }


    private void handleSubmitAndUpdate() {
        String oldPassword = ((EditText) findViewById(R.id.et_oldPassword)).getText().toString().trim();
        String newPassword = ((EditText) findViewById(R.id.et_newPassword)).getText().toString().trim();
        String passwordConfirmation = ((EditText) findViewById(R.id.et_confirm)).getText().toString().trim();

        String errorMessage = validateUserInput(oldPassword, newPassword, passwordConfirmation);

        if(errorMessage != null)
        {
            AlertDialog alertDialog = DialogHelper.createOkDialog(this, false, getString(R.string.error), errorMessage);
            alertDialog.show();
        }
        else
        {
            volleyChangePassword(oldPassword, newPassword, passwordConfirmation);
        }
    }

    private void volleyChangePassword(String oldPassword, String newPassword, String passwordConfirmation) {

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading_changing_password));
        progressDialog.setCancelable(false);
        progressDialog.show();

        Request request = UserApi.updatePassword(OAuthPrefHelper.getAccessToken(this),
                oldPassword, newPassword, passwordConfirmation, RequestCode.RCR_UPDATE_PASSWORD, this);
        BaseApplication.getInstance().getRequestQueue().add(request);

    }

    private String validateUserInput(String oldPassword, String newPassword, String passwordConfirmation) {

        String errorMessage = InputValidator.isOldPasswordValid(oldPassword);

        if(errorMessage != null)
        {
            return errorMessage;
        }

        errorMessage = InputValidator.isNewPasswordValid(newPassword);

        if(errorMessage != null)
        {
            return errorMessage;
        }

        errorMessage = InputValidator.isPasswordMatch(newPassword, passwordConfirmation);

        if(errorMessage != null)
        {
            return errorMessage;
        }

        errorMessage = InputValidator.isPasswordAtLeastEight(newPassword, passwordConfirmation);

        if(errorMessage != null)
        {
            return errorMessage;
        }

        return errorMessage;
    }

    private void processUpdatePasswordResp(EvMeResp object) {

        User user = object.data;

        UserPrefHelper.setUser(this, user);
    }
}
