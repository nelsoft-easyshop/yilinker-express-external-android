package com.yilinker.expresspublic.modules.login;

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
import com.yilinker.expresspublic.core.utilities.InputValidator;
import com.yilinker.expresspublic.modules.BaseActivity;
import com.yilinker.expresspublic.modules.registration.RegistrationActivity;

import java.util.logging.Logger;

public class LoginActivity
        extends BaseActivity
        implements View.OnClickListener, ResponseHandler
{
    private static final Logger logger = Logger.getLogger(LoginActivity.class.getSimpleName());

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(this);

        // Remove toolbar elements
        (toolbar.findViewById(R.id.toolbar_back)).setVisibility(View.GONE);
        (toolbar.findViewById(R.id.toolbar_title)).setVisibility(View.GONE);
        (toolbar.findViewById(R.id.toolbar_avatar)).setVisibility(View.GONE);
        toolbar.setBackgroundResource(android.R.color.transparent);
    }

    @Override
    protected int getBaseLayout()
    {
        return R.layout.activity_base_rl;
    }

    @Override
    protected int getToolbarTitle()
    {
        return R.string.title_login;
    }

    @Override
    protected int getLayoutResource()
    {
        return R.layout.activity_login;
    }

    @Override
    protected void initListeners()
    {
        findViewById(R.id.btn_signIn).setOnClickListener(this);
        findViewById(R.id.btn_register).setOnClickListener(this);
        findViewById(R.id.btn_backToHome).setOnClickListener(this);
    }

    @Override
    protected Intent resultIntent()
    {
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK)
        {
            if (requestCode == RequestCode.RCA_REGISTRATION)
            {
                // Successful registration
                this.resultCode = RESULT_OK;
                finish();
            }
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_signIn:
                handleSignin();
                break;

            case R.id.btn_register:
                Intent intent = new Intent(this, RegistrationActivity.class);
                startActivityForResult(intent, RequestCode.RCA_REGISTRATION);
                break;

            case R.id.btn_backToHome:
                finish();
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
    public void onErrorResponse(int requestCode, String message)
    {
        if (progressDialog.isShowing())
        {
            progressDialog.dismiss();
        }

        switch (requestCode)
        {
            case RequestCode.RCR_TOKEN:
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                break;

            default:
                break;
        }
    }

    private void handleSignin()
    {
        String emailAddress = ((EditText) findViewById(R.id.et_email)).getText().toString().trim();
        String password = ((EditText) findViewById(R.id.et_password)).getText().toString().trim();

        String errorMessage = validateUserInput(emailAddress, password);

        if (errorMessage != null)
        {
            AlertDialog alertDialog = DialogHelper.createOkDialog(this, true, getString(R.string.error), errorMessage);
            alertDialog.show();
        }
        else
        {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            volleyToken(emailAddress, password);
        }
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

    private String validateUserInput(String emailAddress, String password)
    {
        // Check email
        String errorMessage = InputValidator.isEmailValid(emailAddress);

        if (errorMessage != null)
        {
            return errorMessage;
        }

        // Check password
        errorMessage = InputValidator.isPasswordValid(password);

        if (errorMessage != null)
        {
            return errorMessage;
        }

        return errorMessage;
    }
}
