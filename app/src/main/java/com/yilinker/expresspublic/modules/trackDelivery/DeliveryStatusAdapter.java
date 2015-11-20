package com.yilinker.expresspublic.modules.trackDelivery;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yilinker.expresspublic.R;
import com.yilinker.expresspublic.core.models.DeliveryStatus;
import com.yilinker.expresspublic.core.utilities.DateUtils;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Jeico.
 */
public class DeliveryStatusAdapter extends RecyclerView.Adapter<DeliveryStatusAdapter.DeliveryStatusViewHolder>
{
    private static final Logger logger = Logger.getLogger(DeliveryStatusAdapter.class.getSimpleName());

    private Context context;

    private LayoutInflater layoutInflater;

    private List<DeliveryStatus> deliveryStatusList;

    public DeliveryStatusAdapter(Context context, List<DeliveryStatus> deliveryStatusList) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.deliveryStatusList = deliveryStatusList;
    }

    @Override
    public DeliveryStatusViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.holder_delivery_status, parent, false);
        return new DeliveryStatusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DeliveryStatusViewHolder holder, int position) {

        DeliveryStatus deliveryStatus = deliveryStatusList.get(position);

//        holder.tv_message.setText(deliveryStatus.getMessage());
        holder.tv_message.setText(deliveryStatus.getPackageStatus());

        Date deliveryDate = DateUtils.parseDate(deliveryStatus.getDate());

//        holder.tv_date.setText(DateUtils.displayDateAsReadable(deliveryStatus.getDate()));
//        holder.tv_time.setText(DateUtils.displayTimeAsReadable(deliveryStatus.getDate()));

        holder.tv_date.setText(DateUtils.displayDateAsReadable(deliveryDate));
        holder.tv_time.setText(DateUtils.displayTimeAsReadable(deliveryDate));

        if(position%2 == 0)
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#eeeeee"));
        }
        else
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
        }

        /** set icons **/
        String status = deliveryStatus.getPackageStatus();
        int half = getItemCount()/2;

        if (position == 0) {
            if (getItemCount() == 1) {
                holder.iv_statusIcon.setImageResource(R.drawable.pin_start);
                return;
            }
            if (status.contains("Delivered") || status.contains("Received by Recipient")) {
                holder.iv_statusIcon.setImageResource(R.drawable.pin_flag);
            } else {
                holder.iv_statusIcon.setImageResource(R.drawable.pin_flag_2);
            }
        } else if (position == getItemCount() - 1) {
            holder.iv_statusIcon.setImageResource(R.drawable.pin_start);
        } else if (position == half) {
            holder.iv_statusIcon.setImageResource(R.drawable.pin_2);
        } else if (position > half){
            holder.iv_statusIcon.setImageResource(R.drawable.pin_3);
        } else {
            holder.iv_statusIcon.setImageResource(R.drawable.pin_1);
        }

    }

    @Override
    public int getItemCount() {
        return deliveryStatusList.size();
    }

    class DeliveryStatusViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView iv_statusIcon;
        public TextView tv_message;
        public TextView tv_date;
        public TextView tv_time;

        public DeliveryStatusViewHolder(View itemView) {
            super(itemView);

            iv_statusIcon = (ImageView) itemView.findViewById(R.id.iv_statusIcon);
            tv_message = (TextView) itemView.findViewById(R.id.tv_message);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
        }
    }
}
