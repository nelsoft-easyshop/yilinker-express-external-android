package com.yilinker.expresspublic.modules.profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yilinker.expresspublic.R;
import com.yilinker.expresspublic.core.models.AddressGroup;
import com.yilinker.expresspublic.core.models.AddressLocation;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Jeico.
 */
public class MyRecipientsLocationsAdapter extends BaseExpandableListAdapter
{
    private static final Logger logger = Logger.getLogger(MyRecipientsLocationsAdapter.class.getSimpleName());

    private Context context;

    private LayoutInflater layoutInflater;

    private List<MyAddressLocationModel> myAddressLocationModelList;

    public MyRecipientsLocationsAdapter(Context context, List<MyAddressLocationModel> myAddressLocationModelList) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.myAddressLocationModelList = myAddressLocationModelList;
    }

    @Override
    public int getGroupCount() {
        return myAddressLocationModelList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return myAddressLocationModelList.get(groupPosition).getAddressLocationList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return myAddressLocationModelList.get(groupPosition).getAddressGroup();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return myAddressLocationModelList.get(groupPosition).getAddressLocationList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return myAddressLocationModelList.get(groupPosition).getAddressGroup().getId();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return myAddressLocationModelList.get(groupPosition).getAddressLocationList().get(childPosition).getId();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        AddressGroupHolder addressGroupHolder;

        if(convertView == null)
        {
            convertView = layoutInflater.inflate(R.layout.holder_address_group_one, parent, false);

            addressGroupHolder = new AddressGroupHolder(convertView);
            convertView.setTag(addressGroupHolder);
        }
        else
        {
            addressGroupHolder = (AddressGroupHolder) convertView.getTag();
        }

        AddressGroup addressGroup = (AddressGroup) getGroup(groupPosition);
        addressGroupHolder.tv_name.setText(addressGroup.getName());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        AddressLocationHolder addressLocationHolder;

        if(convertView == null)
        {
            convertView = layoutInflater.inflate(R.layout.holder_profile_recipient_location, parent, false);

            addressLocationHolder = new AddressLocationHolder(convertView);
            convertView.setTag(addressLocationHolder);
        }
        else
        {
            addressLocationHolder = (AddressLocationHolder) convertView.getTag();
        }

        AddressLocation addressLocation = (AddressLocation) getChild(groupPosition, childPosition);

        addressLocationHolder.tv_name.setText(addressLocation.getContactPerson());
        addressLocationHolder.tv_contactNumber.setText(addressLocation.getContactPersonNumber());
        addressLocationHolder.tv_address.setText(addressLocation.getAddress());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    /**
     * TODO
     */
    private class AddressGroupHolder
    {
        public TextView tv_name;

        public AddressGroupHolder(View view) {
            this.tv_name = (TextView) view.findViewById(R.id.tv_name);
        }
    }

    /**
     * TODO
     */
    private class AddressLocationHolder
    {
        public TextView tv_name;
        public TextView tv_contactNumber;
        public TextView tv_address;

        public AddressLocationHolder(View view) {
            this.tv_name = (TextView) view.findViewById(R.id.tv_name);
            this.tv_contactNumber = (TextView) view.findViewById(R.id.tv_contactNumber);
            this.tv_address = (TextView) view.findViewById(R.id.tv_address);
        }
    }
}
