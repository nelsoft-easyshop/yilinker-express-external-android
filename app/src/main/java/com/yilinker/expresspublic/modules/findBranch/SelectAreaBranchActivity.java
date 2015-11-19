package com.yilinker.expresspublic.modules.findBranch;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
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
import com.yilinker.expresspublic.core.interfaces.BranchListener;
import com.yilinker.expresspublic.core.models.Branch;
import com.yilinker.expresspublic.core.models.OAuth;
import com.yilinker.expresspublic.core.responses.EvBranchListResp;
import com.yilinker.expresspublic.modules.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Jeico.
 */
public class SelectAreaBranchActivity extends BaseActivity implements ResponseHandler, BranchListener {
    private static final Logger logger = Logger.getLogger(SelectAreaBranchActivity.class.getSimpleName());

    private List<Branch> branchList;

    private BranchAdapter branchAdapter;

    private boolean isInitial = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        branchList = new ArrayList<>();

        branchAdapter = new BranchAdapter(this, branchList, this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_branchList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(branchAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Bundle bundle = getIntent().getExtras();
        Long id = bundle.getLong(BundleKey.CITY_ID, 0);
        String keyword = bundle.getString(BundleKey.KEYWORD, "");
        // Set default keyword
        EditText et_searchField = (EditText) findViewById(R.id.et_searchField);
        et_searchField.setText(keyword);
        et_searchField.setSelection(keyword.length());

        if(id != 0)
        {
            // Request by id
            Request request = BranchApi.searchBranchesByCity(id, RequestCode.RCR_SEARCH_BRANCHES_BY_CITY, this);
            BaseApplication.getInstance().getRequestQueue().add(request);
        }
        else
        {
            // Request by keyword
            // Modified and added access token to parameter
            Request request = BranchApi.searchBranchesByKeyword(keyword, OAuthPrefHelper.getAccessToken(this), RequestCode.RCR_SEARCH_BRANCHES_BY_KEYWORD, this);
            //Request request = BranchApi.searchBranchesByKeyword(keyword, RequestCode.RCR_SEARCH_BRANCHES_BY_KEYWORD, this);
            BaseApplication.getInstance().getRequestQueue().add(request);
        }
    }

    @Override
    protected int getBaseLayout() {
        return R.layout.activity_base_ll;
    }

    @Override
    protected int getToolbarTitle() {
        return R.string.title_select_area;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_select_area_branch;
    }

    @Override
    protected void initListeners() {
        EditText et_searchField = (EditText) findViewById(R.id.et_searchField);
        et_searchField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE)
                {
                    volleySearchBranchByKeyword(v.getText().toString().trim());
                }

                return false;
            }
        });

        et_searchField.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s)
            {
                // Do nothing
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                // Do nothing
            }

            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if(isInitial)
                {
                    isInitial = false;
                    return;
                }

                String keyword = s.toString().trim();
                if (keyword.length() == 0)
                {
                    volleySearchBranchByKeyword("");
                }
                else
                {
                    if(keyword.length() >= 3)
                    {
                        volleySearchBranchByKeyword(keyword);
                    }
                }
            }
        });
    }

    @Override
    protected Intent resultIntent() {
        return null;
    }

    @Override
    public void onResponse(int requestCode, Object object) {
        switch (requestCode)
        {
            case RequestCode.RCR_SEARCH_BRANCHES_BY_CITY:
            case RequestCode.RCR_SEARCH_BRANCHES_BY_KEYWORD:
                EvBranchListResp evBranchListResp = (EvBranchListResp) object;
                populateList(evBranchListResp.data);
                break;

            default:
                break;
        }
    }

    @Override
    public void onErrorResponse(int requestCode, String message) {
        switch (requestCode)
        {
            case RequestCode.RCR_SEARCH_BRANCHES_BY_CITY:
            case RequestCode.RCR_SEARCH_BRANCHES_BY_KEYWORD:
                branchList.clear();
                branchAdapter.notifyDataSetChanged();
                break;

            default:
                break;
        }

        ((RecyclerView) findViewById(R.id.rv_branchList)).setVisibility(View.VISIBLE);
    }

    private void populateList(List<Branch> tempBranchList)
    {
        if(tempBranchList != null)
        {
            branchList.clear();

            for(Branch branch : tempBranchList)
            {
                branchList.add(branch);
            }

            branchAdapter.notifyDataSetChanged();

            ((RecyclerView) findViewById(R.id.rv_branchList)).setVisibility(View.VISIBLE);
        }
    }

    private void volleySearchBranchByKeyword(String keyword)
    {
        ((RecyclerView) findViewById(R.id.rv_branchList)).setVisibility(View.GONE);

        // Modified and added access token to parameter
        //Request request = BranchApi.searchBranchesByKeyword(keyword, RequestCode.RCR_SEARCH_BRANCHES_BY_KEYWORD, this);
        Request request = BranchApi.searchBranchesByKeyword(keyword, OAuthPrefHelper.getAccessToken(this), RequestCode.RCR_SEARCH_BRANCHES_BY_KEYWORD, this);
        BaseApplication.getInstance().getRequestQueue().add(request);
    }

    @Override
    public void onBranchNameSelected(Branch branch) {

        logger.severe("onBranchNameSelected: " + branch.getName());
        Bundle bundle = new Bundle();
        Gson gson = new GsonBuilder().create();
        bundle.putString(BundleKey.BRANCH, gson.toJson(branch));

        Intent intent = new Intent(this, BranchDetailsActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onBranchContactNumberSelected(Branch branch) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:+"+branch.getContactNumber()));
        startActivity(callIntent);
    }

    @Override
    public void onBranchDirectionSelected(Branch branch) {
        Bundle bundle = new Bundle();
        Gson gson = new GsonBuilder().create();
        bundle.putString(BundleKey.BRANCH, gson.toJson(branch));

        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}

        if(gps_enabled) {
            Intent intent = new Intent(this, DirectionActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }else{
            Toast.makeText(this, R.string.location_services_error_toast, Toast.LENGTH_SHORT).show();
        }
    }
}
