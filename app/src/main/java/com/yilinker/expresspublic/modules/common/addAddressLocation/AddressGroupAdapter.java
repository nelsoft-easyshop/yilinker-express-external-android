package com.yilinker.expresspublic.modules.common.addAddressLocation;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yilinker.expresspublic.R;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Jeico.
 */
public class AddressGroupAdapter extends RecyclerView.Adapter<AddressGroupAdapter.AddressGroupViewHolder>{

    private static final Logger logger = Logger.getLogger(AddressGroupAdapter.class.getSimpleName());

    private Context context;

    private LayoutInflater layoutInflater;

    private List<AddressGroupModel> addressGroupModelList;


    public AddressGroupAdapter(Context context, List<AddressGroupModel> addressGroupModelList) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.addressGroupModelList = addressGroupModelList;

        logger.severe("count: " + addressGroupModelList.size());
    }

    @Override
    public AddressGroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.holder_address_group, parent, false);
        return new AddressGroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AddressGroupViewHolder holder, int position) {
        AddressGroupModel addressGroupModel = addressGroupModelList.get(position);

        holder.tv_addressGroup.setText(addressGroupModel.getAddressGroupName());

        if(addressGroupModel.isSelected())
        {
            holder.tv_addressGroup.setBackgroundResource(R.drawable.bg_rounded_orange_red);
            holder.tv_addressGroup.setTextColor(Color.parseColor("#ffffff"));
        }
        else
        {
            holder.tv_addressGroup.setBackgroundResource(R.drawable.bg_rounded_white_stroked_marigold);
            holder.tv_addressGroup.setTextColor(Color.parseColor("#ffc107"));
        }
    }

    @Override
    public int getItemCount() {
        return addressGroupModelList.size();
    }

    class AddressGroupViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tv_addressGroup;

        public AddressGroupViewHolder(View itemView) {
            super(itemView);

            tv_addressGroup = (TextView) itemView.findViewById(R.id.tv_addressGroup);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            boolean isSelected = addressGroupModelList.get(getAdapterPosition()).isSelected();
            addressGroupModelList.get(getAdapterPosition()).setIsSelected(!isSelected);

            notifyDataSetChanged();
        }
    }
}
