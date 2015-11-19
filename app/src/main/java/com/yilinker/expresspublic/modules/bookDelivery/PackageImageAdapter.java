package com.yilinker.expresspublic.modules.bookDelivery;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yilinker.expresspublic.R;
import com.yilinker.expresspublic.core.helpers.ImageUtility;
import com.yilinker.expresspublic.core.interfaces.PackageImageListener;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Created by rlcoronado on 18/11/2015.
 */
public class PackageImageAdapter extends RecyclerView.Adapter<PackageImageAdapter.PackageImageAdapterViewHolder>
        implements View.OnClickListener {

    private static final Logger logger = Logger.getLogger(PackageImageAdapter.class.getSimpleName());

    private Context context;
    private ArrayList<String> photoFilePathList = new ArrayList<>();

    private PackageImageListener listener;

    PackageImageAdapter(Context context, ArrayList<String> photoFilePathList, PackageImageListener listener) {
        this.context = context;
        this.photoFilePathList = photoFilePathList;
        this.listener = listener;
    }

    @Override
    public PackageImageAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(context).inflate(R.layout.holder_package_image, parent, false);

            return new PackageImageAdapterViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return this.photoFilePathList.size();
    }
    public class PackageImageAdapterViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivPackageImage;

        public PackageImageAdapterViewHolder(View itemView) {
            super(itemView);

            ivPackageImage = (ImageView) itemView.findViewById(R.id.ivPackageImage);
        }
    }

    @Override
    public void onBindViewHolder(PackageImageAdapterViewHolder holder, int position) {

        String image = photoFilePathList.get(position);
        Bitmap bm = ImageUtility.convertFileToBitmap(image);

        holder.ivPackageImage.setImageBitmap(bm);

        holder.ivPackageImage.setOnClickListener(this);
        holder.ivPackageImage.setTag(R.string.package_image_path, image);

    }

    @Override
    public void onClick(View v) {

        listener.onImageClick(v.getTag(R.string.package_image_path).toString());

    }

}
