package com.yilinker.expresspublic.modules.bookDelivery;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yilinker.expresspublic.R;
import com.yilinker.expresspublic.core.interfaces.PickUpScheduleListener;
import com.yilinker.expresspublic.core.models.PickUpSchedule;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Jeico.
 */
public class PickUpScheduleAdapter extends RecyclerView.Adapter<PickUpScheduleAdapter.PickUpScheduleViewHolder>
{
    private static final Logger logger = Logger.getLogger(PickUpScheduleAdapter.class.getSimpleName());

    private Context context;

    private LayoutInflater layoutInflater;

    private List<PickUpSchedule> pickUpScheduleList;

    private PickUpScheduleListener listener;

    public PickUpScheduleAdapter(Context context, List<PickUpSchedule> pickUpScheduleList, PickUpScheduleListener listener) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.pickUpScheduleList = pickUpScheduleList;
        this.listener = listener;
    }


    @Override
    public PickUpScheduleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.holder_pickup_schedule, parent, false);
        return new PickUpScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PickUpScheduleViewHolder holder, int position) {

        PickUpSchedule pickUpSchedule = pickUpScheduleList.get(position);

        holder.tv_time.setText(pickUpSchedule.getSchedule());
    }

    @Override
    public int getItemCount() {
        return pickUpScheduleList.size();
    }


    class PickUpScheduleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tv_time;

        public PickUpScheduleViewHolder(View itemView) {
            super(itemView);

            tv_time = (TextView) itemView.findViewById(R.id.tv_time);

            tv_time.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onPickUpScheduleSelected(pickUpScheduleList.get(getAdapterPosition()));
        }
    }
}
