package com.yilinker.expresspublic.modules.bookDelivery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.yilinker.expresspublic.R;
import com.yilinker.expresspublic.core.contants.BundleKey;
import com.yilinker.expresspublic.core.interfaces.PackageImageListener;
import com.yilinker.expresspublic.modules.BaseActivity;

import java.util.ArrayList;

/**
 * Created by rlcoronado on 17/11/2015.
 */
public class PackageDetailsImagesActivity extends BaseActivity implements PackageImageListener {

    private ArrayList<String> photoFilePathList = new ArrayList<>();
    private PackageImageAdapter packageImageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handlePhotoListData();

    }

    @Override
    protected int getBaseLayout() {
        return R.layout.activity_base_ll;
    }

    @Override
    protected int getToolbarTitle() {
        return R.string.title_package_details;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_package_details_images;
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected Intent resultIntent() {
        return null;
    }


    private void handlePhotoListData() {

        photoFilePathList = getIntent().getExtras().getStringArrayList(BundleKey.PHOTO_FILEPATH_LIST);

        packageImageAdapter = new PackageImageAdapter(this, photoFilePathList, this);

        RecyclerView rv_packageImageList = (RecyclerView) findViewById(R.id.rv_photoList);
        rv_packageImageList.setHasFixedSize(true);
        rv_packageImageList.setAdapter(packageImageAdapter);
        rv_packageImageList.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onImageClick(String imagePath) {

        handleImageViewer(imagePath);

    }

    private void handleImageViewer(String imagePath) {

        Intent intent = new Intent(this, PackageImageViewer.class);
        intent.putExtra(BundleKey.PHOTO_FILEPATH_LIST, imagePath);
        startActivity(intent);
    }
}


