package com.yilinker.expresspublic.modules.myShipment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yilinker.expresspublic.BaseApplication;
import com.yilinker.expresspublic.R;
import com.yilinker.expresspublic.ResponseHandler;
import com.yilinker.expresspublic.core.api.TrackApi;
import com.yilinker.expresspublic.core.contants.BundleKey;
import com.yilinker.expresspublic.core.contants.RequestCode;
import com.yilinker.expresspublic.core.deserializer.DateDeserializer;
import com.yilinker.expresspublic.core.enums.ShipmentType;
import com.yilinker.expresspublic.core.helpers.OAuthPrefHelper;
import com.yilinker.expresspublic.core.interfaces.DeliveryPackageListener;
import com.yilinker.expresspublic.core.models.DeliveryPackage;
import com.yilinker.expresspublic.core.responses.EvDeliveryPackageListResp;
import com.yilinker.expresspublic.core.responses.EvDeliveryPackageResp;
import com.yilinker.expresspublic.core.serializer.DateSerializer;
import com.yilinker.expresspublic.modules.trackDelivery.DeliveryPackageAdapter;
import com.yilinker.expresspublic.modules.trackDelivery.TrackDetailsActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Jeico.
 */
public class MyShipmentFragment extends Fragment implements ResponseHandler, DeliveryPackageListener {
    private static final Logger logger = Logger.getLogger(MyShipmentFragment.class.getSimpleName());

    private static final String ARG_SHIPMENT_TYPE = "ARG_SHIPMENT_TYPE";

    private ShipmentType shipmentType;

    private List<DeliveryPackage> deliveryPackageList;

    private DeliveryPackageAdapter deliveryPackageAdapter;


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

        deliveryPackageList = new ArrayList<>();

        deliveryPackageAdapter = new DeliveryPackageAdapter(getActivity(), deliveryPackageList, this);

        RecyclerView rv_deliveryPackageList = (RecyclerView) getView().findViewById(R.id.rv_deliveryPackageList);
        rv_deliveryPackageList.setHasFixedSize(true);
        rv_deliveryPackageList.setAdapter(deliveryPackageAdapter);
        rv_deliveryPackageList.setLayoutManager(new LinearLayoutManager(getActivity()));

        //String accessToken = OAuthPrefHelper.getAccessToken(getActivity());

        String accessToken = "MgLxx6HgGgJ62uzFSbtFbZCbg8eK4iIxjt1qSqeH";

        logger.severe("access token: " + accessToken);
        if(shipmentType == ShipmentType.DELIVERED)
        {
            Request request = TrackApi.delivered(accessToken, RequestCode.RCR_TRACK_DELIVERED, this);
            BaseApplication.getInstance().getRequestQueue().add(request);
        }

        if(shipmentType == ShipmentType.ONGOING)
        {
            Request request = TrackApi.ongoing(accessToken, RequestCode.RCR_TRACK_ONGOING, this);
            BaseApplication.getInstance().getRequestQueue().add(request);
        }
    }

    @Override
    public void onResponse(int requestCode, Object object) {

        switch (requestCode)
        {
            case RequestCode.RCR_TRACK_DELIVERED:
            case RequestCode.RCR_TRACK_ONGOING:
                EvDeliveryPackageListResp evDeliveryPackageListResp = (EvDeliveryPackageListResp) object;
                populateList(evDeliveryPackageListResp.data);
                break;

            default:
                break;
        }

        if(getView() != null)
        {
            ((RecyclerView) getView().findViewById(R.id.rv_deliveryPackageList)).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onErrorResponse(int requestCode, String message) {

        switch (requestCode)
        {
            case RequestCode.RCR_TRACK_DELIVERED:
            case RequestCode.RCR_TRACK_ONGOING:
                //Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                break;

            default:
                break;
        }

        if(getView() != null)
        {
            ((RecyclerView) getView().findViewById(R.id.rv_deliveryPackageList)).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDeliveryPackageSelected(DeliveryPackage deliveryPackage) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateDeserializer())
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create();
        String deliveryPackageRaw = gson.toJson(deliveryPackage);

        Bundle bundle = new Bundle();
        bundle.putString(BundleKey.DELIVERY_PACKAGE, deliveryPackageRaw);

        Intent intent = new Intent(getActivity(), TrackDetailsActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void populateList(List<DeliveryPackage> tempDeliveryPackageList)
    {
        if(deliveryPackageList != null)
        {
            deliveryPackageList.clear();

            for (DeliveryPackage deliveryPackage : tempDeliveryPackageList)
            {
                deliveryPackageList.add(deliveryPackage);
            }

            deliveryPackageAdapter.notifyDataSetChanged();
        }
    }
}
