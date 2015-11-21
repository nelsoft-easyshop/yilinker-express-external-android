package com.yilinker.expresspublic.modules.trackDelivery;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yilinker.expresspublic.R;
import com.yilinker.expresspublic.core.interfaces.DeliveryPackageListener;
import com.yilinker.expresspublic.core.models.DeliveryPackage;
import com.yilinker.expresspublic.core.utilities.DateUtils;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Jeico.
 */
public class DeliveryPackageAdapter extends RecyclerView.Adapter<DeliveryPackageAdapter.DeliveryPackageViewHolder>
{
    private static final Logger logger = Logger.getLogger(DeliveryPackageAdapter.class.getSimpleName());

    private Context context;

    private LayoutInflater layoutInflater;

    private List<DeliveryPackage> deliveryPackageList;

    private DeliveryPackageListener listener;

    public DeliveryPackageAdapter(Context context, List<DeliveryPackage> deliveryPackageList, DeliveryPackageListener listener) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.deliveryPackageList = deliveryPackageList;
        this.listener = listener;
    }

    @Override
    public DeliveryPackageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.holder_delivery_package, parent, false);
        return new DeliveryPackageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DeliveryPackageViewHolder holder, int position) {
        DeliveryPackage deliveryPackage = deliveryPackageList.get(position);

        holder.tv_trackingNumber.setText(deliveryPackage.getTrackingNumber());
        holder.tv_riderDetails.setText(deliveryPackage.getRiderDetails());
        Date date = DateUtils.parseDate(deliveryPackage.getDeliveryStatusList().get(0).getDate());
        holder.tv_deliveryDate.setVisibility(date != null ? View.VISIBLE : View.GONE);
        if (date != null) {
            holder.tv_deliveryDate.setText(DateUtils.displayDateAsReadable(date));
        }
//        holder.tv_deliveryDate.setText(DateUtils.displayDateAsReadable(deliveryPackage.getDeliveryDate()));
        holder.tv_packageContainer.setText(deliveryPackage.getPackageContainer());
//        holder.tv_eta.setText("ETA " + deliveryPackage.getEta());
//        holder.tv_status.setText(deliveryPackage.getStatus());
        holder.tv_status.setText(deliveryPackage.getStatus().toUpperCase());
    }

    @Override
    public int getItemCount() {
        return deliveryPackageList.size();
    }


    class DeliveryPackageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tv_trackingNumber;
        public TextView tv_riderDetails;
        public TextView tv_deliveryDate;
        public TextView tv_packageContainer;
        public TextView tv_eta;
        public TextView tv_status;

        public DeliveryPackageViewHolder(View itemView) {
            super(itemView);

            tv_trackingNumber = (TextView) itemView.findViewById(R.id.tv_trackingNumber);
            tv_riderDetails = (TextView) itemView.findViewById(R.id.tv_riderDetails);
            tv_deliveryDate = (TextView) itemView.findViewById(R.id.tv_deliveryDate);
            tv_packageContainer = (TextView) itemView.findViewById(R.id.tv_packageContainer);
            tv_eta = (TextView) itemView.findViewById(R.id.tv_eta);
            tv_status = (TextView) itemView.findViewById(R.id.tv_status);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onDeliveryPackageSelected(deliveryPackageList.get(getAdapterPosition()));
        }
    }
}
