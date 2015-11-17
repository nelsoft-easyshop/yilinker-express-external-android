package com.yilinker.expresspublic.modules.findBranch;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.android.PolyUtil;
import com.yilinker.expresspublic.BaseApplication;
import com.yilinker.expresspublic.R;
import com.yilinker.expresspublic.ResponseHandler;
import com.yilinker.expresspublic.core.api.DirectionApi;
import com.yilinker.expresspublic.core.contants.BundleKey;
import com.yilinker.expresspublic.core.contants.RequestCode;
import com.yilinker.expresspublic.core.models.Branch;
import com.yilinker.expresspublic.core.responses.directions.Direction;
import com.yilinker.expresspublic.core.utilities.CommonUtils;
import com.yilinker.expresspublic.modules.BaseFragmentActivity;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

/**
 * Created by Jeico.
 */
public class DirectionActivity extends BaseFragmentActivity
        implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, LocationListener,
        GoogleApiClient.OnConnectionFailedListener, ResponseHandler, GoogleMap.OnInfoWindowClickListener, Observer {
    private static final Logger logger = Logger.getLogger(DirectionActivity.class.getSimpleName());

    /**
     * Set interval to 10seconds
     */
    private static final long INTERVAL = 1000 * 10;

    /**
     * Set fastest interval to 5seconds
     */
    private static final long FASTEST_INTERVAL = 1000 * 5;

    private GoogleApiClient googleApiClient;

    private LocationRequest locationRequest;

    private GoogleMap googleMap;

    private Marker branchMarker;

    private Branch branch;

    private Location currentLocation;

    private ProgressDialog progressDialog;

    private MapSyncModel mapSyncModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(this);

        mapSyncModel = new MapSyncModel();
        mapSyncModel.addObserver(this);

        Bundle bundle = getIntent().getExtras();
        // Get branch
        String BranchJson = bundle.getString(BundleKey.BRANCH, "");
        Gson gson = new GsonBuilder().create();
        branch = gson.fromJson(BranchJson, Branch.class);

        /**
         * Create location request settings
         * This includes the interval of request
         */
        createLocationRequest();

        /**
         * Setup Google API Client
         */
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        /**
         * Get reference to google map
         */
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.googleMap);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected int getBaseLayout() {
        return R.layout.activity_base_ll;
    }

    @Override
    protected int getToolbarTitle() {
        return R.string.title_direction;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_direction;
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        startLocationUpdates();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(googleApiClient != null) {
            googleApiClient.disconnect();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.setMyLocationEnabled(true);
        this.googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        this.googleMap.getUiSettings().setCompassEnabled(true);
        this.googleMap.getUiSettings().setZoomControlsEnabled(false);
        this.googleMap.getUiSettings().setMapToolbarEnabled(false);
        // Set listener when info window is clicked
        this.googleMap.setOnInfoWindowClickListener(this);

        // Setup position of MyLocation button
        setupMyLocationButton();

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(new Criteria(), false));
        if(location != null)
        {
            // Move to current location
            this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(location.getLatitude(), location.getLongitude()), 13));
        }
        else
        {
            // Move to Philippines
            this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(12.403267, 122.924997), 5));
        }

        // Set map is ready
        mapSyncModel.setIsMapReady(true);
    }


    @Override
    public void onInfoWindowClick(Marker marker) {
        if(branchMarker.equals(marker))
        {
            Bundle bundle = new Bundle();
            Gson gson = new GsonBuilder().create();
            bundle.putString(BundleKey.BRANCH, gson.toJson(branch));

            Intent intent = new Intent(this, BranchDetailsActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        else
        {
            logger.severe("not equal");
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {
        /**
         * TODO what will happen if connection is suspended?
         */
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        /**
         * TODO what will happen if connection failed
         */
}

    @Override
    public void onLocationChanged (Location location)
    {
        if(currentLocation == null)
        {
            currentLocation = location;
            // Set location is ready
            mapSyncModel.setIsLocationReady(true);
        }
    }

    @Override
    public void onResponse(int requestCode, Object object) {
        if(progressDialog.isShowing())
        {
            progressDialog.dismiss();
        }

        switch (requestCode)
        {
            case RequestCode.RCR_GET_DIRECTION:
                drawDirection((Direction) object);
                break;

            default:
                break;
        }
    }

    @Override
    public void onErrorResponse(int requestCode, String message) {
        if(progressDialog.isShowing())
        {
            progressDialog.dismiss();
        }

        switch (requestCode)
        {
            case RequestCode.RCR_GET_DIRECTION:
                Toast.makeText(DirectionActivity.this, message, Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }

    private void volleyGetDirection(LatLng origin, LatLng destination, String key) {
        // Show loading
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading_getting_direction));
        progressDialog.setCancelable(false);
        progressDialog.show();

        Request request = DirectionApi.getDirection(origin, destination, key, RequestCode.RCR_GET_DIRECTION, this);
        BaseApplication.getInstance().getRequestQueue().add(request);
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

    /**
     * This method creates location request
     * @return
     */
    private void createLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    /**
     * This method is used to start receiving location updates
     */
    private void startLocationUpdates() {
        if(googleApiClient.isConnected())
        {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        }
    }

    /**
     * This method is used to stop receiving location updates
     */
    private void stopLocationUpdates() {
        if(googleApiClient.isConnected())
        {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        }
    }

    private void drawDirection(Direction direction)
    {
        String encodedPoints = direction.routes.get(0).overview_polyline.points;

        List<LatLng> latLngList = PolyUtil.decode(encodedPoints);

        LatLng origin = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        // Modified due to change of datatype within latitude and longitude
        //LatLng destination = new LatLng(branch.getLatitude(), branch.getLongitude());
        LatLng destination = new LatLng(Double.parseDouble(String.valueOf(branch.getLatitude())),
                Double.parseDouble(String.valueOf(branch.getLongitude())));
        if(latLngList.size() == 0)
        {
            latLngList.add(origin);
            latLngList.add(destination);
        }
        else
        {
            latLngList.add(0, origin);
            latLngList.add(destination);
        }

        Polyline polyline = googleMap.addPolyline(new PolylineOptions()
                .width(CommonUtils.convertDpToPixels(this, 5))
                .color(Color.parseColor("#ff5722")));
        polyline.setPoints(latLngList);

        // Get bounds
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        List<LatLng> arr = polyline.getPoints();
        for(int i = 0; i < arr.size();i++)
        {
            builder.include(arr.get(i));
        }

        LatLngBounds bounds = builder.build();
        int padding = CommonUtils.convertDpToPixels(this, 80); // offset from edges of the map in pixels
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        this.googleMap.animateCamera(cameraUpdate);
    }

    @Override
    public void update(Observable observable, Object data)
    {
        // Place destination marker
        branchMarker = this.googleMap.addMarker(new MarkerOptions()
                .title(branch.getName())
                .snippet(branch.getAddress())
                .visible(true)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker))
                // Modified due to change of datatype within latitude and longitude
                //.position(new LatLng(branch.getLatitude(), branch.getLongitude())));
                .position(new LatLng(Double.parseDouble(String.valueOf(branch.getLatitude())),
                        Double.parseDouble(String.valueOf(branch.getLongitude())))));
        // Place origin marker
        this.googleMap.addMarker(new MarkerOptions()
                .visible(true)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_route_origin))
                .position(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude())));

        // Make request
        volleyGetDirection(
                new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                // Modified due to change of datatype within latitude and longitude
                //new LatLng(branch.getLatitude(), branch.getLongitude()),
                new LatLng(Double.parseDouble(String.valueOf(branch.getLatitude())),
                        Double.parseDouble(String.valueOf(branch.getLongitude()))),
                        getString(R.string.google_api_key));
    }
}
