package com.yilinker.expresspublic.modules.findBranch;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yilinker.expresspublic.BaseApplication;
import com.yilinker.expresspublic.R;
import com.yilinker.expresspublic.ResponseHandler;
import com.yilinker.expresspublic.core.api.BranchApi;
import com.yilinker.expresspublic.core.contants.BundleKey;
import com.yilinker.expresspublic.core.contants.RequestCode;
import com.yilinker.expresspublic.core.helpers.OAuthPrefHelper;
import com.yilinker.expresspublic.core.helpers.SearchHistoryPrefHelper;
import com.yilinker.expresspublic.core.managers.BranchManager;
import com.yilinker.expresspublic.core.models.Branch;
import com.yilinker.expresspublic.core.responses.EvBranchListResp;
import com.yilinker.expresspublic.modules.BaseFragmentActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by Jeico.
 */
public class SearchBranchesActivity extends BaseFragmentActivity
    implements OnMapReadyCallback, ResponseHandler, GoogleApiClient.ConnectionCallbacks, LocationListener,
        GoogleMap.OnInfoWindowClickListener, GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private static final Logger logger = Logger.getLogger(SearchBranchesActivity.class.getSimpleName());

    private final String BACKSTACK_MAP = "MAP";
    private final String BACKSTACK_SEARCH_HISTORY = "SEARCH_HISTORY";

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

    private Location currentLocation;

    private SearchHistoryFragment searchHistoryFragment;

    private GoogleMap googleMap;

    private Map<Marker, Branch> branchMap;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(this);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        currentLocation = locationManager.getLastKnownLocation(locationManager.getBestProvider(new Criteria(), false));

        /**
         * Initialize branch map
         */
        branchMap = new HashMap<>();

        /**
         * Initialize search history fragment
         */
        searchHistoryFragment = SearchHistoryFragment.newInstance();

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
        MapFragment mapFragment = MapFragment.newInstance();
        mapFragment.getMapAsync(this);
        getFragmentManager().beginTransaction()
                .add(R.id.fl_container, mapFragment)
                .commit();
    }

    @Override
    protected int getBaseLayout() {
        return R.layout.activity_base_ll;
    }

    @Override
    protected int getToolbarTitle() {
        return R.string.title_search_branches;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_search_branches;
    }

    @Override
    protected void initListeners() {
        // Set onclick listener for select area
        findViewById(R.id.btn_selectArea).setOnClickListener(this);

        // Set onclick listener for search field
        EditText et_searchField = (EditText) findViewById(R.id.et_searchField);
        et_searchField.setOnClickListener(this);
//        et_searchField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//
//                if (actionId == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE)
//                {
//                    showSelectAreaBranchActivity();
//                }
//
//                return false;
//            }
//        });

        // Set onclick listener for search icon
        findViewById(R.id.iv_search).setOnClickListener(this);

        // Set onclick listener for branch nearest to me
        findViewById(R.id.btn_branchNearestToMe).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_selectArea:
                showSelectCityActivity();
                break;

            case R.id.et_searchField:
                showSearchHistory();
                break;

            case R.id.iv_search:
                showSelectAreaBranchActivity();
                break;

            case R.id.btn_branchNearestToMe:
                showDirectionActivity();
                break;

            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RequestCode.RCA_SEARCH_HISTORY)
        {
            searchHistoryFragment.refreshList();
        }
    }

    @Override
    public void onBackPressed() {

        if(getFragmentManager().getBackStackEntryCount() != 0)
        {
            getFragmentManager().popBackStack();
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public void finish() {

        if(getFragmentManager().getBackStackEntryCount() != 0)
        {
            if(getCurrentFocus() != null)
            {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }

            getFragmentManager().popBackStack();
        }
        else
        {
            super.finish();
        }
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

        // Setup position of MyLocation button
        setupMyLocationButton();

        // Set listener when info window is clicked
        this.googleMap.setOnInfoWindowClickListener(this);

        if(currentLocation != null)
        {
            // Move to current location
            this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), 13));
        }
        else
        {
            // Move to Philippines
            this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(12.403267, 122.924997), 5));
        }

        volleyGetAllBranch();
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
        currentLocation = location;
    }

    @Override
    public void onResponse(int requestCode, Object object) {
        if(progressDialog.isShowing())
        {
            progressDialog.dismiss();
        }

        switch (requestCode)
        {
            case RequestCode.RCR_GET_ALL_BRANCH:
                EvBranchListResp evBranchListResp = (EvBranchListResp) object;
                drawMarkers(evBranchListResp.data);
                BranchManager.getInstance().setBranchMap(evBranchListResp.data);
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
            case RequestCode.RCR_GET_ALL_BRANCH:
                Toast.makeText(SearchBranchesActivity.this, message, Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

        if(branchMap.containsKey(marker))
        {
            Branch branch = branchMap.get(marker);

            Bundle bundle = new Bundle();
            Gson gson = new GsonBuilder().create();
            bundle.putString(BundleKey.BRANCH, gson.toJson(branch));

            Intent intent = new Intent(this, BranchDetailsActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
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

    private void drawMarkers(List<Branch> branchList)
    {
        for (int i = 0; i < branchList.size(); i++)
        {
            Branch branch = branchList.get(i);
            Marker marker = this.googleMap.addMarker(new MarkerOptions()
                    .title(branch.getName())
                    .snippet(branch.getAddress())
                    .visible(true)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker))
                    // Modified due to change of datatype of latutude and longitude
                    //.position(new LatLng(branch.getLatitude(), branch.getLongitude())));
                    .position(new LatLng(Double.parseDouble(branch.getLatitude()), Double.parseDouble(branch.getLongitude()))));
            branchMap.put(marker, branch);
        }
    }

    private void showSearchHistory() {

        if(getFragmentManager().getBackStackEntryCount() == 0)
        {
            getFragmentManager().beginTransaction()
                .add(R.id.fl_container, searchHistoryFragment)
                .addToBackStack(BACKSTACK_SEARCH_HISTORY)
                .commit();
        }
    }

    private void showSelectCityActivity()
    {
        Intent intent = new Intent(this, SelectAreaCityActivity.class);
        startActivity(intent);
    }

    private void showSelectAreaBranchActivity()
    {
        String keyword = ((EditText) findViewById(R.id.et_searchField)).getText().toString().trim();

        // Add keyword to history if not empty
        if(!TextUtils.isEmpty(keyword))
        {
            SearchHistoryPrefHelper.addKeywordToHistory(this, keyword);
        }

        Bundle bundle = new Bundle();
        bundle.putLong(BundleKey.CITY_ID, 0);
        bundle.putString(BundleKey.KEYWORD, keyword);

        Intent intent = new Intent(this, SelectAreaBranchActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, RequestCode.RCA_SEARCH_HISTORY);
    }

    private void showDirectionActivity()
    {
        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}

        if(gps_enabled) {

            if (currentLocation == null) {
                Toast.makeText(this, getString(R.string.error_no_location), Toast.LENGTH_SHORT).show();
                return;
            }

            if (BranchManager.getInstance().getBranchMap().size() > 0) {
                Bundle bundle = new Bundle();
                Branch nearestBranch = BranchManager.getInstance().getNearestBranch(currentLocation);
                Gson gson = new GsonBuilder().create();
                bundle.putString(BundleKey.BRANCH, gson.toJson(nearestBranch));

                Intent intent = new Intent(this, DirectionActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }

        }else{
            Toast.makeText(this, R.string.location_services_error_toast, Toast.LENGTH_SHORT).show();
        }
    }

    private void volleyGetAllBranch() {
        // Show loading
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading_searching_branches));
        progressDialog.setCancelable(false);
        progressDialog.show();
        // Added additional parameter "access token"
        //Request request = BranchApi.getAllBranch(RequestCode.RCR_GET_ALL_BRANCH, this);
        Request request = BranchApi.getAllBranch(RequestCode.RCR_GET_ALL_BRANCH, OAuthPrefHelper.getAccessToken(this), this);
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
}
