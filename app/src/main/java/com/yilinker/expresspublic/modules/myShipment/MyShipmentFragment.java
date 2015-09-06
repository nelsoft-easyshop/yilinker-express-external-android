package com.yilinker.expresspublic.modules.myShipment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.yilinker.expresspublic.BaseApplication;
import com.yilinker.expresspublic.R;
import com.yilinker.expresspublic.ResponseHandler;
import com.yilinker.expresspublic.core.api.TrackApi;
import com.yilinker.expresspublic.core.enums.ShipmentType;
import com.yilinker.expresspublic.core.helpers.OAuthPrefHelper;

import java.util.logging.Logger;

/**
 * Created by Jeico.
 */
public class MyShipmentFragment extends Fragment implements ResponseHandler {
    private static final Logger logger = Logger.getLogger(MyShipmentFragment.class.getSimpleName());

    private static final String ARG_SHIPMENT_TYPE = "ARG_SHIPMENT_TYPE";

    private ShipmentType shipmentType;

    public static MyShipmentFragment newInstance(ShipmentType shipmentType)
    {
        Bundle args = new Bundle();
        args.putSerializable(ARG_SHIPMENT_TYPE, shipmentType);
        MyShipmentFragment fragment = new MyShipmentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shipmentType = (ShipmentType) getArguments().getSerializable(ARG_SHIPMENT_TYPE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_shipment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String accessToken = OAuthPrefHelper.getAccessToken(getActivity());

        logger.severe("access token: " + accessToken);
        if(shipmentType == ShipmentType.DELIVERED)
        {
            Request request = TrackApi.delivered(accessToken, 1, this);
            BaseApplication.getInstance().getRequestQueue().add(request);
        }

        if(shipmentType == ShipmentType.ONGOING)
        {
            Request request = TrackApi.ongoing(accessToken, 1, this);
            BaseApplication.getInstance().getRequestQueue().add(request);
        }
    }

    @Override
    public void onResponse(int requestCode, Object object) {

    }

    @Override
    public void onErrorResponse(int requestCode, String message) {

    }
}
