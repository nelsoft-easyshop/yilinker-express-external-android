package com.yilinker.expresspublic.modules.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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
import com.yilinker.expresspublic.core.responses.EvBranchListResp;
import com.yilinker.expresspublic.modules.BaseActivity;
import com.yilinker.expresspublic.modules.findBranch.BranchAdapter;
import com.yilinker.expresspublic.modules.findBranch.BranchDetailsActivity;
import com.yilinker.expresspublic.modules.findBranch.DirectionActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Jeico.
 */
public class BookmarkedBranchesActivity extends BaseActivity implements BranchListener, ResponseHandler {
    private static final Logger logger = Logger.getLogger(BookmarkedBranchesActivity.class.getSimpleName());

    private List<Branch> branchList;

    private BranchAdapter branchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        branchList = new ArrayList<>();

        branchAdapter = new BranchAdapter(this, branchList, this);

        // Get reference to recyclerview
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_branchList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(branchAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Make request
        Request request = BranchApi.getBookmarkedBranches(OAuthPrefHelper.getAccessToken(this), RequestCode.RCR_GET_BOOKMARKED_BRANCHES, this);
        BaseApplication.getInstance().getRequestQueue().add(request);
    }

    @Override
    protected int getBaseLayout() {
        return R.layout.activity_base_ll;
    }

    @Override
    protected int getToolbarTitle() {
        return R.string.title_bookmarked_branches;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_bookmarked_branches;
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected Intent resultIntent() {
        return null;
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
        callIntent.setData(Uri.parse("tel:+" + branch.getContactNumber()));
        startActivity(callIntent);
    }

    @Override
    public void onBranchDirectionSelected(Branch branch) {
        Bundle bundle = new Bundle();
        Gson gson = new GsonBuilder().create();
        bundle.putString(BundleKey.BRANCH, gson.toJson(branch));

        Intent intent = new Intent(this, DirectionActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onResponse(int requestCode, Object object) {
        switch (requestCode)
        {
            case RequestCode.RCR_GET_BOOKMARKED_BRANCHES:
                EvBranchListResp evBranchListResp = (EvBranchListResp) object;
                populateList(evBranchListResp.data);
                break;

            default:
                break;
        }

        ((RecyclerView) findViewById(R.id.rv_branchList)).setVisibility(View.VISIBLE);
    }

    @Override
    public void onErrorResponse(int requestCode, String message) {
        switch (requestCode)
        {
            case RequestCode.RCR_GET_BOOKMARKED_BRANCHES:
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
            logger.severe("clear branch");
            branchList.clear();

            for(Branch branch : tempBranchList)
            {
                logger.severe("add branch");
                branchList.add(branch);
            }

            branchAdapter.notifyDataSetChanged();
        }
    }
}
