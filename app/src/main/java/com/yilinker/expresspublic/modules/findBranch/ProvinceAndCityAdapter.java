package com.yilinker.expresspublic.modules.findBranch;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.yilinker.expresspublic.R;
import com.yilinker.expresspublic.core.models.City;

import java.util.List;

/**
 * Created by Jeico.
 */
public class ProvinceAndCityAdapter extends ExpandableRecyclerAdapter<ProvinceAndCityAdapter.ProvinceViewHolder, ProvinceAndCityAdapter.CityViewHolder>
{
    private LayoutInflater layoutInflater;

    private OnCityListener listener;

    public interface OnCityListener
    {
        void onCitySelected(Long id, String name);
    }

    public ProvinceAndCityAdapter(Context context, List parentItemList, OnCityListener listener) {
        super(context, parentItemList);

        this.layoutInflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    @Override
    public ProvinceViewHolder onCreateParentViewHolder(ViewGroup viewGroup) {
        View view = layoutInflater.inflate(R.layout.holder_province, viewGroup, false);
        return new ProvinceViewHolder(view);
    }

    @Override
    public CityViewHolder onCreateChildViewHolder(ViewGroup viewGroup) {
        View view = layoutInflater.inflate(R.layout.holder_city, viewGroup, false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindParentViewHolder(ProvinceViewHolder provinceViewHolder, int i, Object parentObject) {
        ProvinceModel provinceModel = (ProvinceModel) parentObject;
        provinceViewHolder.tv_province.setText(provinceModel.getName());
    }

    @Override
    public void onBindChildViewHolder(CityViewHolder cityViewHolder, int i, Object childObject) {
        City city = (City) childObject;
        cityViewHolder.id = city.getId();
        cityViewHolder.name = city.getName();
        cityViewHolder.tv_city.setText(city.getName());
    }

    public class ProvinceViewHolder extends ParentViewHolder
    {
        public TextView tv_province;

        public ProvinceViewHolder(View itemView) {
            super(itemView);

            tv_province = (TextView) itemView.findViewById(R.id.tv_province);
        }
    }

    public class CityViewHolder extends ChildViewHolder implements View.OnClickListener
    {
        private Long id;

        private String name;

        private TextView tv_city;

        public CityViewHolder(View itemView) {
            super(itemView);

            tv_city = (TextView) itemView.findViewById(R.id.tv_city);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onCitySelected(id, name);
        }
    }
}
