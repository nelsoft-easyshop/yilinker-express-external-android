package com.yilinker.expresspublic.modules.common.addAddressLocation;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.yilinker.expresspublic.R;
import com.yilinker.expresspublic.modules.BaseActivity;
import com.yilinker.expresspublic.modules.common.customviews.SubMapFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Jeico.
 */
public class AddAddressLocationActivity extends BaseActivity implements OnMapReadyCallback {
    private static final Logger logger = Logger.getLogger(AddAddressLocationActivity.class.getSimpleName());

    private GoogleMap googleMap;

    private Marker addressMarker;

    private SubMapFragment mapFragment;

    private ScrollView sv_parent;

    private List<AddressGroupModel> addressGroupModelList;

    private AddressGroupAdapter addressGroupAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sv_parent = (ScrollView) findViewById(R.id.sv_parent);


        addressGroupModelList = new ArrayList<>();
        addressGroupModelList.add(new AddressGroupModel(false, "Home"));
        addressGroupModelList.add(new AddressGroupModel(false, "Office"));
        addressGroupModelList.add(new AddressGroupModel(false, "Others"));
        addressGroupModelList.add(new AddressGroupModel(false, "Home"));
        addressGroupModelList.add(new AddressGroupModel(false, "Office"));
        addressGroupModelList.add(new AddressGroupModel(false, "Others"));
        addressGroupModelList.add(new AddressGroupModel(false, "Office"));
        addressGroupModelList.add(new AddressGroupModel(false, "Others"));

        addressGroupAdapter = new AddressGroupAdapter(this, addressGroupModelList);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_addressGroupList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(addressGroupAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        //addressGroupAdapter.notifyDataSetChanged();

        /**
         * Get reference to google map
         */
        mapFragment = (SubMapFragment) getFragmentManager().findFragmentById(R.id.googleMap);
        mapFragment.getMapAsync(this);
        mapFragment.setListener(new SubMapFragment.OnTouchListener() {
            @Override
            public void onTouch() {
                sv_parent.requestDisallowInterceptTouchEvent(true);
            }
        });
    }

    @Override
    protected int getBaseLayout() {
        return R.layout.activity_base_ll;
    }

    @Override
    protected int getToolbarTitle() {
        return R.string.title_new_sender_detail;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_add_address_location;
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected Intent resultIntent() {
        return null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.setMyLocationEnabled(true);
        this.googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        this.googleMap.getUiSettings().setCompassEnabled(true);
        this.googleMap.getUiSettings().setZoomControlsEnabled(false);
        this.googleMap.getUiSettings().setMapToolbarEnabled(false);
        // Set map click listener
        this.googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                changeMarkerPosition(latLng);
            }
        });

        // Setup position of MyLocation button
        setupMyLocationButton();

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(new Criteria(), false));
        if(location != null)
        {
            // Move to current location
            this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(location.getLatitude(), location.getLongitude()), 13));

            addressMarker = this.googleMap.addMarker(new MarkerOptions()
                    .visible(true)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker))
                    .position(new LatLng(location.getLatitude(), location.getLongitude())));
        }
        else
        {
            // Move to Philippines
            this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(12.403267, 122.924997), 5));
        }
    }

    private void changeMarkerPosition(LatLng latLng) {
        if(addressMarker != null)
        {
            addressMarker.setPosition(latLng);
        }
        else
        {
            addressMarker = this.googleMap.addMarker(new MarkerOptions()
                    .visible(true)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker))
                    .position(latLng));
        }
    }

    /**
     * This method changes the location of the "My Location" button
     */
    @SuppressWarnings("ResourceType")
    private void setupMyLocationButton() {
        // Get the button view
        View locationButton = ((View) findViewById(1).getParent()).findViewById(2);

        // and next place it, for example, on bottom right (as Google Maps app)
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
        // position on right bottom
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        rlp.setMargins(0, 0, 30, 30);
    }
}
