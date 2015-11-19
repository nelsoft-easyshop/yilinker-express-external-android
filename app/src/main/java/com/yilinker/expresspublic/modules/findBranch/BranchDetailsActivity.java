package com.yilinker.expresspublic.modules.findBranch;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yilinker.expresspublic.BaseApplication;
import com.yilinker.expresspublic.R;
import com.yilinker.expresspublic.ResponseHandler;
import com.yilinker.expresspublic.core.api.BranchApi;
import com.yilinker.expresspublic.core.contants.BundleKey;
import com.yilinker.expresspublic.core.contants.RequestCode;
import com.yilinker.expresspublic.core.helpers.OAuthPrefHelper;
import com.yilinker.expresspublic.core.models.Branch;
import com.yilinker.expresspublic.core.models.Contacts;
import com.yilinker.expresspublic.core.models.Schedule;
import com.yilinker.expresspublic.core.responses.bases.EvBaseResp;
import com.yilinker.expresspublic.modules.BaseActivity;

import java.util.logging.Logger;

/**
 * Created by Jeico.
 */
public class BranchDetailsActivity extends BaseActivity implements View.OnClickListener, ResponseHandler {
    private static final Logger logger = Logger.getLogger(BranchDetailsActivity.class.getSimpleName());

    private Branch branch;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(this);

        Bundle bundle = getIntent().getExtras();
        // Get branch
        String BranchJson = bundle.getString(BundleKey.BRANCH, "");
        Gson gson = new GsonBuilder().create();
        branch = gson.fromJson(BranchJson, Branch.class);

        // Set branch name
                ((TextView) findViewById(R.id.tv_branchName)).setText(branch.getName());
        // Set branch address
        ((TextView) findViewById(R.id.tv_branchAddress)).setText(branch.getAddress());
        ((TextView) findViewById(R.id.tv_address)).setText(branch.getAddress());
        // Set operating hours
        // Modified due to change of model and variable name of openingTime and closingTime
        StringBuilder builder = new StringBuilder();
        if(branch.getSchedule().isEmpty()){
            builder.append("-");
        }else {
            for (Schedule schedule : branch.getSchedule()) {
                String value = schedule.getOpeningHour() + " to " + schedule.getClosingHour();
                builder.append(value).append('\n');
            }
        }
        ((TextView) findViewById(R.id.tv_operatingHours)).setText(builder);
        //((TextView) findViewById(R.id.tv_operatingHours)).setText(branch.getOpeningTime() + " to " + branch.getClosingTime());
        // Set phone number

        //Modified due to change of model and variable of contacts
        StringBuilder contactBuilder = new StringBuilder();
        if(branch.getContacts().isEmpty()){
            contactBuilder.append("-");
        }else {
            for (Contacts contacts : branch.getContacts()) {
                String value = contacts.getContactNumber();
                contactBuilder.append(value).append('\n');
            }
        }
        ((TextView) findViewById(R.id.tv_contactNumber)).setText(contactBuilder);
        //((TextView) findViewById(R.id.tv_contactNumber)).setText("+" + branch.getContactNumber());
    }

    @Override
    protected int getBaseLayout() {
        return R.layout.activity_base_rl;
    }

    @Override
    protected int getToolbarTitle() {
        return R.string.title_branch_details;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_branch_details;
    }

    @Override
    protected void initListeners() {
        // Set bookmarked onclick listener
        findViewById(R.id.btn_bookmarkBranch).setOnClickListener(this);
    }

    @Override
    protected Intent resultIntent() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_bookmarkBranch:
                volleyBookmark();
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
            case RequestCode.RCR_BOOKMARK:
            case RequestCode.RCR_UNBOOKMARK:
                EvBaseResp evBaseResp = (EvBaseResp) object;
                Toast.makeText(BranchDetailsActivity.this, evBaseResp.message, Toast.LENGTH_SHORT).show();
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
            case RequestCode.RCR_BOOKMARK:
            case RequestCode.RCR_UNBOOKMARK:
                Toast.makeText(BranchDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }

    private void volleyBookmark()
    {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading_processing));
        progressDialog.setCancelable(false);
        progressDialog.show();

        String accessToken = OAuthPrefHelper.getAccessToken(this);

        Request request = BranchApi.bookmark(accessToken, branch.getId(), RequestCode.RCR_BOOKMARK, this);
        BaseApplication.getInstance().getRequestQueue().add(request);
    }
}
