package com.yilinker.expresspublic.modules.common.addAddressLocation;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.yilinker.expresspublic.BaseApplication;
import com.yilinker.expresspublic.R;
import com.yilinker.expresspublic.ResponseHandler;
import com.yilinker.expresspublic.core.api.LocationApi;
import com.yilinker.expresspublic.core.contants.ApiKey;
import com.yilinker.expresspublic.core.contants.BundleKey;
import com.yilinker.expresspublic.core.contants.RequestCode;
import com.yilinker.expresspublic.core.enums.AddressType;
import com.yilinker.expresspublic.core.helpers.OAuthPrefHelper;
import com.yilinker.expresspublic.core.models.AddressGroup;
import com.yilinker.expresspublic.core.models.AddressTag;
import com.yilinker.expresspublic.core.models.Barangay;
import com.yilinker.expresspublic.core.models.City;
import com.yilinker.expresspublic.core.models.Province;
import com.yilinker.expresspublic.core.responses.EvAddressGroupListResp;
import com.yilinker.expresspublic.core.responses.EvAddressTagListResponse;
import com.yilinker.expresspublic.core.responses.EvBarangayListResp;
import com.yilinker.expresspublic.core.responses.EvCityListResp;
import com.yilinker.expresspublic.core.responses.EvProvinceListResp;
import com.yilinker.expresspublic.core.responses.bases.EvBaseResp;
import com.yilinker.expresspublic.modules.BaseActivity;
import com.yilinker.expresspublic.modules.common.customviews.SubMapFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.xml.transform.Result;

/**
 * Created by Jeico.
 */
public class AddAddressLocationActivity extends BaseActivity implements OnMapReadyCallback, ResponseHandler, View.OnClickListener {

    private static final Logger logger = Logger.getLogger(AddAddressLocationActivity.class.getSimpleName());

    private AddressType addressType;

    private GoogleMap googleMap;

    private Marker addressMarker;

    private SubMapFragment mapFragment;

    private ScrollView sv_parent;

    private List<AddressGroupModel> addressGroupModelList;

    private AddressGroupAdapter addressGroupAdapter;

    private ProgressDialog progressDialog;

    private ArrayAdapter<Province> provinceArrayAdapter;
    private ArrayAdapter<City> cityArrayAdapter;
    private ArrayAdapter<Barangay> barangayArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Bundle bundle = getIntent().getExtras();
        addressType = (AddressType) bundle.getSerializable(BundleKey.ADDRESS_TYPE);

        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(this);

//        Bundle bundle = getIntent().getExtras();
//        addressType = (AddressType) bundle.getSerializable(BundleKey.ADDRESS_TYPE);

        sv_parent = (ScrollView) findViewById(R.id.sv_parent);

        // Setup address group
        addressGroupModelList = new ArrayList<>();

        addressGroupAdapter = new AddressGroupAdapter(this, addressGroupModelList);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_addressGroupList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(addressGroupAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);

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

        volleyGetAddressGroup();
    }

    @Override
    protected int getBaseLayout() {
        return R.layout.activity_base_ll;
    }

    @Override
    protected int getToolbarTitle() {
//        return R.string.title_new_sender_detail;

        switch (addressType){

            case SENDER:

                return R.string.title_new_sender_detail;

            default:
                return R.string.new_recipient_detail;
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_add_address_location;
    }

    @Override
    protected void initListeners() {
        // Set onclick listener for adding address group
        findViewById(R.id.ibtn_addAddressGroup).setOnClickListener(this);
        // Set onclick listener for volleySubmit
        findViewById(R.id.btn_submit).setOnClickListener(this);

        // Set up province
        provinceArrayAdapter = new ArrayAdapter<Province>(this, android.R.layout.simple_spinner_dropdown_item);
        Spinner sp_province = (Spinner) findViewById(R.id.sp_province);
        sp_province.setAdapter(provinceArrayAdapter);
        sp_province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cityArrayAdapter.clear();
                barangayArrayAdapter.clear();

                Province province = provinceArrayAdapter.getItem(position);
//                volleyGetCityList(province.getId());
                volleyGetCityList(Long.valueOf(province.getId()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // Set up city
        cityArrayAdapter = new ArrayAdapter<City>(this, android.R.layout.simple_spinner_dropdown_item);
        Spinner sp_city = (Spinner) findViewById(R.id.sp_city);
        sp_city.setAdapter(cityArrayAdapter);
        sp_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                barangayArrayAdapter.clear();

                City city = cityArrayAdapter.getItem(position);
//                volleyGetBarangayList(city.getId());
                volleyGetBarangayList(Long.valueOf(city.getId()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // Set up barangay
        barangayArrayAdapter = new ArrayAdapter<Barangay>(this, android.R.layout.simple_spinner_dropdown_item);
        Spinner sp_barangay = (Spinner) findViewById(R.id.sp_barangay);
        sp_barangay.setAdapter(barangayArrayAdapter);
        sp_barangay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected Intent resultIntent() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ibtn_addAddressGroup:
                showAddAddressGroupDialog();
                break;

            case R.id.btn_submit:

                if(!hasValidInput())
                    return;

                volleySubmit();
                break;

            default:
                break;
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

    @Override
    public void onResponse(int requestCode, Object object) {
        if(progressDialog.isShowing())
        {
            progressDialog.dismiss();
        }

        switch (requestCode)
        {
            case RequestCode.RCR_GET_ADDRESS_GROUP:
//                EvAddressGroupListResp getAddressGroupResp = (EvAddressGroupListResp) object;
//                populateAddressGroup(getAddressGroupResp.data);
//                volleyGetProvinceList();
                handleAddressGroup(object);
                break;

            case RequestCode.RCR_ADD_ADDRESS_GROUP:
                EvAddressGroupListResp addAddressGroupResp = (EvAddressGroupListResp) object;
                populateAddressGroup(addAddressGroupResp.data);
                break;

            case RequestCode.RCR_GET_PROVINCE_LIST:
                EvProvinceListResp evProvinceListResp = (EvProvinceListResp) object;
                provinceArrayAdapter.addAll(evProvinceListResp.data);
                break;

            case RequestCode.RCR_GET_CITY_LIST:
                EvCityListResp evCityListResp = (EvCityListResp) object;
                cityArrayAdapter.addAll(evCityListResp.data);
                break;

            case RequestCode.RCR_GET_BARANGAY_LIST:
                EvBarangayListResp evBarangayListResp = (EvBarangayListResp) object;
                barangayArrayAdapter.addAll(evBarangayListResp.data);
                break;

            case RequestCode.RCR_ADD_ADDRESS_LOCATION:
                EvBaseResp evBaseResp = (EvBaseResp) object;
//                Toast.makeText(this, evBaseResp.message, Toast.LENGTH_LONG).show();
                resultCode = RESULT_OK;
                finish();
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
            case RequestCode.RCR_GET_ADDRESS_GROUP:
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                break;

            case RequestCode.RCR_ADD_ADDRESS_GROUP:
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                break;

            case RequestCode.RCR_GET_PROVINCE_LIST:
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                break;

            case RequestCode.RCR_GET_CITY_LIST:
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                break;

            case RequestCode.RCR_GET_BARANGAY_LIST:
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                break;

            case RequestCode.RCR_ADD_ADDRESS_LOCATION:
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                break;

            default:
                break;
        }
    }

    private void volleyGetAddressGroup() {
        // Show loading
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading_getting_address_group));
        progressDialog.setCancelable(false);
        progressDialog.show();

        // Request for address group
//        Request request = LocationApi.getAddressGroup(OAuthPrefHelper.getAccessToken(this),
//                RequestCode.RCR_GET_ADDRESS_GROUP, this);
//        BaseApplication.getInstance().getRequestQueue().add(request);

        Request request = null;

        switch (addressType) {

            case SENDER:

                request = LocationApi.getAddressGroup(OAuthPrefHelper.getAccessToken(this),
                        RequestCode.RCR_GET_ADDRESS_GROUP, this);
                break;

            case RECIPIENT:

                request = LocationApi.getAddressTag(OAuthPrefHelper.getAccessToken(this),
                        RequestCode.RCR_GET_ADDRESS_GROUP, this);
                break;
        }


        BaseApplication.getInstance().getRequestQueue().add(request);

    }

    private void volleyGetProvinceList()
    {
        // Show loading
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading_getting_provinces));
        progressDialog.setCancelable(false);
        progressDialog.show();

        // Request for address group
//        Request request = LocationApi.getProvinceList(RequestCode.RCR_GET_PROVINCE_LIST, this);
        Request request = LocationApi.getProvinceList(OAuthPrefHelper.getAccessToken(this), RequestCode.RCR_GET_PROVINCE_LIST, this);
        BaseApplication.getInstance().getRequestQueue().add(request);
    }

    private void volleyGetCityList(Long provinceId)
    {
        // Show loading
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading_getting_cities));
        progressDialog.setCancelable(false);
        progressDialog.show();

        // Request for address group
//        Request request = LocationApi.getCityList(provinceId, RequestCode.RCR_GET_CITY_LIST, this);
        Request request = LocationApi.getCityList(OAuthPrefHelper.getAccessToken(this), provinceId, RequestCode.RCR_GET_CITY_LIST, this);
        BaseApplication.getInstance().getRequestQueue().add(request);
    }

    private void volleyGetBarangayList(Long cityId)
    {
        // Show loading
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading_getting_barangays));
        progressDialog.setCancelable(false);
        progressDialog.show();

        // Request for address group
//        Request request = LocationApi.getBarangayList(cityId, RequestCode.RCR_GET_BARANGAY_LIST, this);
        Request request = LocationApi.getBarangayList(OAuthPrefHelper.getAccessToken(this), cityId, RequestCode.RCR_GET_BARANGAY_LIST, this);
        BaseApplication.getInstance().getRequestQueue().add(request);
    }

    private void populateAddressGroup(List<AddressGroup> addressGroupList) {

        if(addressGroupList != null)
        {
            addressGroupModelList.clear();

            for (AddressGroup addressGroup : addressGroupList)
            {
                AddressGroupModel addressGroupModel = new AddressGroupModel(false,
                        addressGroup.getId(), addressGroup.getName()
                );

                addressGroupModelList.add(addressGroupModel);
            }

            addressGroupAdapter.notifyDataSetChanged();
        }
    }

    private void populateAddressTag(List<AddressTag> addressTagList) {

        if(addressTagList != null)
        {
            addressGroupModelList.clear();

            for (AddressTag addressGroup : addressTagList)
            {
                AddressGroupModel addressGroupModel = new AddressGroupModel(false,
                        String.valueOf(addressGroup.getValue()), addressGroup.getName()
                );

                addressGroupModelList.add(addressGroupModel);
            }

            addressGroupAdapter.notifyDataSetChanged();
        }
    }

    private void showAddAddressGroupDialog() {
        View view = getLayoutInflater().inflate(R.layout.dialog_add_address_group, null);
        final EditText et_addressGroup = (EditText) view.findViewById(R.id.et_addressGroup);

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.add_address_group))
                .setView(view)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.add), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = et_addressGroup.getText().toString().trim();
                        validateAddressGroup(name);
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builder.create().show();
    }

    private void validateAddressGroup(String name) {
        if(TextUtils.isEmpty(name))
        {
            Toast.makeText(this, getString(R.string.error_invalid_address_group_name), Toast.LENGTH_LONG).show();
        }
        else
        {
            volleyAddAddressGroup(name);
        }
    }

    private void volleyAddAddressGroup(String name) {

        // Show loading
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading_adding_address_group));
        progressDialog.setCancelable(false);
        progressDialog.show();

        Request request = LocationApi.addAddressGroup(OAuthPrefHelper.getAccessToken(this), name, RequestCode.RCR_ADD_ADDRESS_GROUP, this);
        BaseApplication.getInstance().getRequestQueue().add(request);
    }

    private void volleySubmit()
    {
        // Show loading
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading_adding_address_location));
        progressDialog.setCancelable(false);
        progressDialog.show();

        String fullName = ((EditText) findViewById(R.id.et_fullName)).getText().toString().trim();
        String contactNumber = ((EditText) findViewById(R.id.et_contactNumber)).getText().toString().trim();
//        List<Long> addressGroup = addressGroupAdapter.getSelectedIds();
        String addressGroup = String.valueOf(addressGroupAdapter.getSelectedId());
        String unitNumber = ((EditText) findViewById(R.id.et_unitNumber)).getText().toString().trim();
        String buildingName = ((EditText) findViewById(R.id.et_buildingName)).getText().toString().trim();
        String streetNumber = ((EditText) findViewById(R.id.et_streetNumber)).getText().toString().trim();
        String streetName = ((EditText) findViewById(R.id.et_streetName)).getText().toString().trim();
        String village = ((EditText) findViewById(R.id.et_village)).getText().toString().trim();

        Province province = (Province) ((Spinner) findViewById(R.id.sp_province)).getSelectedItem();
//        Long provinceId = province.getId();
        Long provinceId = Long.valueOf(province.getId());
        City city = (City) ((Spinner) findViewById(R.id.sp_city)).getSelectedItem();
//        Long cityId = city.getId();
        Long cityId = Long.valueOf(city.getId());
        Barangay barangay = (Barangay) ((Spinner) findViewById(R.id.sp_barangay)).getSelectedItem();
//        Long barangayId = barangay.getId();
        Long barangayId = Long.valueOf(barangay.getId());

        String zipCode = ((EditText) findViewById(R.id.et_zipCode)).getText().toString().trim();

        Double latitude = null;
        Double longitude = null;
        if(addressMarker != null)
        {
            latitude = addressMarker.getPosition().latitude;
            longitude = addressMarker.getPosition().longitude;
        }


//        Request request = LocationApi.addAddressLocation(OAuthPrefHelper.getAccessToken(this), fullName, contactNumber,
//                addressGroup, unitNumber, buildingName, streetNumber, streetName, village, provinceId,
//                cityId, barangayId, zipCode, latitude, longitude, addressType.getValue(), RequestCode.RCR_ADD_ADDRESS_LOCATION, this);

        Request request = null;

        switch (addressType) {

            case SENDER:

                request = LocationApi.addAddress(OAuthPrefHelper.getAccessToken(this), fullName, contactNumber,
                    addressGroup, null, unitNumber, buildingName, streetNumber, streetName, village, provinceId,
                    cityId, barangayId, zipCode, RequestCode.RCR_ADD_ADDRESS_LOCATION, this);
                break;

            case RECIPIENT:

                request = LocationApi.addAddress(OAuthPrefHelper.getAccessToken(this), fullName, contactNumber,
                        null, addressGroup, unitNumber, buildingName, streetNumber, streetName, village, provinceId,
                        cityId, barangayId, zipCode, RequestCode.RCR_ADD_ADDRESS_LOCATION, this);
                break;
        }

        BaseApplication.getInstance().getRequestQueue().add(request);
    }

    private void handleAddressGroup(Object object){

        switch (addressType){

            case SENDER:

                EvAddressGroupListResp getAddressGroupResp = (EvAddressGroupListResp) object;
                populateAddressGroup(getAddressGroupResp.data);
                break;

            case RECIPIENT:

                EvAddressTagListResponse getAddressTagResp = (EvAddressTagListResponse) object;
                populateAddressTag(getAddressTagResp.data);

                break;

        }

        volleyGetProvinceList();

    }

    private boolean hasValidInput(){

        EditText etFullname = (EditText) findViewById(R.id.et_fullName);
        EditText etContactNo = (EditText) findViewById(R.id.et_contactNumber);
        EditText etStreetName = (EditText) findViewById(R.id.et_streetName);

        String fullname = etFullname.getText().toString().trim();
        String contactNumber = etContactNo.getText().toString().trim();
        String streetName = etStreetName.getText().toString().trim();

        Pattern mobilePattern = Pattern.compile("^[0-9]{10,11}$");


        if(fullname.isEmpty()){

            Toast.makeText(getApplicationContext(), "Please enter the full name of the contact", Toast.LENGTH_LONG).show();
            return false;
        }

        if(contactNumber.isEmpty()){

            Toast.makeText(getApplicationContext(), "Please enter a contact number", Toast.LENGTH_LONG).show();
            return false;
        }

        if(!mobilePattern.matcher(contactNumber).matches()){

            Toast.makeText(getApplicationContext(), "The contact number should be 11 digits long.", Toast.LENGTH_LONG).show();
            return false;
        }

        if(addressGroupAdapter.getSelectedId() == AddressGroupAdapter.NO_SELECTION){

            Toast.makeText(getApplicationContext(), "Please select a group", Toast.LENGTH_LONG).show();
            return false;

        }

        if(streetName.isEmpty()){

            Toast.makeText(getApplicationContext(), "Please enter a street name", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

}
