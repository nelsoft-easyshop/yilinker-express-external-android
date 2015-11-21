package com.yilinker.expresspublic.modules.bookDelivery;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yilinker.expresspublic.R;
import com.yilinker.expresspublic.core.interfaces.AddressListener;
import com.yilinker.expresspublic.core.models.Address;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Jeico.
 */
public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressHolder>
{
    private static final Logger logger = Logger.getLogger(AddressAdapter.class.getSimpleName());

    private Context context;

    private LayoutInflater layoutInflater;

    private List<Address> addressList;

    private AddressListener listener;

    public AddressAdapter(Context context, List<Address> addressList, AddressListener listener) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.addressList = addressList;
        this.listener = listener;
    }

    @Override
    public AddressHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.holder_address, parent, false);

        return new AddressHolder(view);
    }

    @Override
    public void onBindViewHolder(AddressHolder holder, int position) {
        Address address = addressList.get(position);

        holder.tv_name.setText(address.getContactPerson());
        holder.tv_contactNumber.setText(address.getContactPersonNumber());
        holder.tv_address.setText(address.getAddress());
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    class AddressHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView tv_name;
        public TextView tv_contactNumber;
        public TextView tv_address;

        public AddressHolder(View itemView)
        {
            super(itemView);

            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_contactNumber = (TextView) itemView.findViewById(R.id.tv_contactNumber);
            tv_address = (TextView) itemView.findViewById(R.id.tv_address);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onAddressSelected(addressList.get(getAdapterPosition()));
        }
    }
}
