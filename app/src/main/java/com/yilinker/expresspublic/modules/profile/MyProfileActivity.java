package com.yilinker.expresspublic.modules.profile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.yilinker.expresspublic.BaseApplication;
import com.yilinker.expresspublic.R;
import com.yilinker.expresspublic.ResponseHandler;
import com.yilinker.expresspublic.core.api.UserApi;
import com.yilinker.expresspublic.core.contants.RequestCode;
import com.yilinker.expresspublic.core.helpers.OAuthPrefHelper;
import com.yilinker.expresspublic.core.helpers.UserPrefHelper;
import com.yilinker.expresspublic.core.models.User;
import com.yilinker.expresspublic.core.responses.EvMeResp;
import com.yilinker.expresspublic.modules.BaseActivity;
import com.yilinker.expresspublic.modules.account.MyAccountActivity;

import java.util.logging.Logger;

/**
 * Created by Jeico.
 */
public class MyProfileActivity extends BaseActivity implements ResponseHandler, View.OnClickListener {
    private static final Logger logger = Logger.getLogger(MyProfileActivity.class.getSimpleName());

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(this);

        volleyGetUserProfile();
    }

    @Override
    protected int getBaseLayout() {
        return R.layout.activity_base_ll;
    }

    @Override
    protected int getToolbarTitle() {
        return R.string.title_my_profile;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_my_profile;
    }

    @Override
    protected void initListeners() {
        // My Account button
        findViewById(R.id.iv_myAccount).setOnClickListener(this);
        findViewById(R.id.ll_myBookmarkedBranches).setOnClickListener(this);
        findViewById(R.id.ll_myAddressLocations).setOnClickListener(this);
        findViewById(R.id.ll_myRecipientsLocations).setOnClickListener(this);

    }

    @Override
    protected Intent resultIntent() {
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUi();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.iv_myAccount:
                startMyAccountActivity();
                break;

            case R.id.ll_myBookmarkedBranches:
                startMyBookedmarkBranchesActivity();
                break;

            case R.id.ll_myAddressLocations:
                startMyAddressLocationsActivity();
                break;

            case R.id.ll_myRecipientsLocations:
                startMyRecipientLocationsActivity();
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
            case RequestCode.RCR_GET_USER_PROFILE:
                processGetUserProfileResp((EvMeResp) object);
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
            case RequestCode.RCR_GET_USER_PROFILE:
                Toast.makeText(MyProfileActivity.this, message, Toast.LENGTH_SHORT).show();
                updateUi();
                break;

            default:
                break;
        }
    }

    private void volleyGetUserProfile() {

        // Show loading
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading_account_information));
        progressDialog.setCancelable(false);
        progressDialog.show();

        Request request = UserApi.getUserProfile(OAuthPrefHelper.getAccessToken(this), RequestCode.RCR_GET_USER_PROFILE, this);
        BaseApplication.getInstance().getRequestQueue().add(request);
    }

    private void processGetUserProfileResp(EvMeResp object) {

        User user = object.data;
        // Save to preferences
        UserPrefHelper.setUser(this, user);

        updateUi();
    }

    private void updateUi()
    {
        User user = UserPrefHelper.getUser(this);

        // Update UI
        ((TextView) findViewById(R.id.tv_fullname)).setText(user.getFullname());
        ((TextView) findViewById(R.id.tv_contactNumber)).setText(user.getContactNumber());
    }


    private void startMyAccountActivity() {
        Intent intent = new Intent(this, MyAccountActivity.class);
        startActivity(intent);
    }

    private void startMyBookedmarkBranchesActivity() {
        Intent intent = new Intent(this, BookmarkedBranchesActivity.class);
        startActivity(intent);
    }

    private void startMyAddressLocationsActivity() {
        Intent intent = new Intent(this, MyAddressLocationsActivity.class);
        startActivity(intent);
    }

    private void startMyRecipientLocationsActivity() {
        Intent intent = new Intent(this, MyRecipientsLocationsActivity.class);
        startActivity(intent);
    }
}
