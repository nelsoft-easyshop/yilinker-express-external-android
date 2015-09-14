package com.yilinker.expresspublic.core.api;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yilinker.expresspublic.BuildConfig;
import com.yilinker.expresspublic.ResponseHandler;
import com.yilinker.expresspublic.core.GsonJsonRequest;
import com.yilinker.expresspublic.core.GsonRequest;
import com.yilinker.expresspublic.core.contants.ApiEndpoint;
import com.yilinker.expresspublic.core.contants.ApiKey;
import com.yilinker.expresspublic.core.helpers.VolleyErrorHelper;
import com.yilinker.expresspublic.core.requests.EvAddAddressLocationReq;
import com.yilinker.expresspublic.core.responses.EvAddressGroupListResp;
import com.yilinker.expresspublic.core.responses.EvAddressLocationListResp;
import com.yilinker.expresspublic.core.responses.EvBarangayListResp;
import com.yilinker.expresspublic.core.responses.EvCityListResp;
import com.yilinker.expresspublic.core.responses.EvGetAllProvinceAndCityResp;
import com.yilinker.expresspublic.core.responses.EvMyAddressLocationModelListResp;
import com.yilinker.expresspublic.core.responses.EvProvinceListResp;
import com.yilinker.expresspublic.core.responses.bases.EvBaseResp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by Jeico.
 */
public class LocationApi
{
    private static final Logger logger = Logger.getLogger(LocationApi.class.getSimpleName());

    /**
     * TODO
     * @param requestCode
     * @param handler
     * @return
     */
    public static Request getAllProvinceAndCity(final int requestCode, final ResponseHandler handler)
    {
        // Build endpoint
        String endpoint = BuildConfig.DOMAIN + "/"
                + ApiEndpoint.LOCATION_API + "/"
                + ApiEndpoint.LOCATION_AREA;

        // Build request
        GsonRequest<EvGetAllProvinceAndCityResp> gsonRequest = new GsonRequest<>(Request.Method.GET, null, endpoint, null, EvGetAllProvinceAndCityResp.class,
                new GsonRequest.GsonResponseListener<EvGetAllProvinceAndCityResp>() {
                    @Override
                    public void onResponse(EvGetAllProvinceAndCityResp object) {
                        handler.onResponse(requestCode, object);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handler.onErrorResponse(requestCode, VolleyErrorHelper.getErrorMessage(error));
                    }
                });

        return gsonRequest;
    }

    /**
     * TODO
     * @param requestCode
     * @param handler
     * @return
     */
    public static Request getMyAddressLocationsAsGroup(String accessToken, final int requestCode, final ResponseHandler handler)
    {
        // Build endpoint
        String endpoint = BuildConfig.DOMAIN + "/"
                + ApiEndpoint.LOCATION_API + "/"
                + ApiEndpoint.LOCATION_SENDER_ADDRESS + "?group_by=address_group";

        // Build request
        GsonRequest<EvMyAddressLocationModelListResp> gsonRequest = new GsonRequest<>(Request.Method.GET, accessToken, endpoint, null, EvMyAddressLocationModelListResp.class,
                new GsonRequest.GsonResponseListener<EvMyAddressLocationModelListResp>() {
                    @Override
                    public void onResponse(EvMyAddressLocationModelListResp object) {
                        handler.onResponse(requestCode, object);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        logger.severe(error.toString());
                        handler.onErrorResponse(requestCode, VolleyErrorHelper.getErrorMessage(error));
                    }
                });

        return gsonRequest;
    }

    /**
     * TODO
     * @param requestCode
     * @param handler
     * @return
     */
    public static Request getMyAddressLocations(String accessToken, final int requestCode, final ResponseHandler handler)
    {
        // Build endpoint
        String endpoint = BuildConfig.DOMAIN + "/"
                + ApiEndpoint.LOCATION_API + "/"
                + ApiEndpoint.LOCATION_SENDER_ADDRESS;

        // Build request
        GsonRequest<EvAddressLocationListResp> gsonRequest = new GsonRequest<>(Request.Method.GET, accessToken, endpoint, null, EvAddressLocationListResp.class,
                new GsonRequest.GsonResponseListener<EvAddressLocationListResp>() {
                    @Override
                    public void onResponse(EvAddressLocationListResp object) {
                        handler.onResponse(requestCode, object);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        logger.severe(error.toString());
                        handler.onErrorResponse(requestCode, VolleyErrorHelper.getErrorMessage(error));
                    }
                });

        return gsonRequest;
    }

    /**
     * TODO
     * @param accessToken
     * @param requestCode
     * @param handler
     * @return
     */
    public static Request getRecipientLocationsAsGroup(String accessToken, final int requestCode, final ResponseHandler handler)
    {
        // Build endpoint
        String endpoint = BuildConfig.DOMAIN + "/"
                + ApiEndpoint.LOCATION_API + "/"
                + ApiEndpoint.LOCATION_RECIPIENT_ADDRESS + "?group_by=address_group";

        // Build request
        GsonRequest<EvMyAddressLocationModelListResp> gsonRequest = new GsonRequest<>(Request.Method.GET, accessToken, endpoint, null, EvMyAddressLocationModelListResp.class,
                new GsonRequest.GsonResponseListener<EvMyAddressLocationModelListResp>() {
                    @Override
                    public void onResponse(EvMyAddressLocationModelListResp object) {
                        handler.onResponse(requestCode, object);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        logger.severe(error.toString());
                        handler.onErrorResponse(requestCode, VolleyErrorHelper.getErrorMessage(error));
                    }
                });

        return gsonRequest;
    }

    /**
     * TODO
     * @param accessToken
     * @param requestCode
     * @param handler
     * @return
     */
    public static Request getMyRecipientLocations(String accessToken, final int requestCode, final ResponseHandler handler)
    {
        // Build endpoint
        String endpoint = BuildConfig.DOMAIN + "/"
                + ApiEndpoint.LOCATION_API + "/"
                + ApiEndpoint.LOCATION_RECIPIENT_ADDRESS;

        // Build request
        GsonRequest<EvAddressLocationListResp> gsonRequest = new GsonRequest<>(Request.Method.GET, accessToken, endpoint, null, EvAddressLocationListResp.class,
                new GsonRequest.GsonResponseListener<EvAddressLocationListResp>() {
                    @Override
                    public void onResponse(EvAddressLocationListResp object) {
                        handler.onResponse(requestCode, object);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        logger.severe(error.toString());
                        handler.onErrorResponse(requestCode, VolleyErrorHelper.getErrorMessage(error));
                    }
                });

        return gsonRequest;
    }

    /**
     * TODO
     * @param accessToken
     * @param requestCode
     * @param handler
     * @return
     */
    public static Request getAddressGroup(String accessToken, final int requestCode, final ResponseHandler handler)
    {
        // Build endpoint
        String endpoint = BuildConfig.DOMAIN + "/"
                + ApiEndpoint.LOCATION_API + "/"
                + ApiEndpoint.LOCATION_ADDRESS_GROUP;

        logger.severe(endpoint);
        // Build request
        GsonRequest<EvAddressGroupListResp> gsonRequest = new GsonRequest<>(Request.Method.GET, accessToken, endpoint, null, EvAddressGroupListResp.class,
                new GsonRequest.GsonResponseListener<EvAddressGroupListResp>() {
                    @Override
                    public void onResponse(EvAddressGroupListResp object) {
                        handler.onResponse(requestCode, object);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handler.onErrorResponse(requestCode, VolleyErrorHelper.getErrorMessage(error));
                    }
                });

        return gsonRequest;
    }

    /**
     * TODO
     * @param accessToken
     * @param name
     * @param requestCode
     * @param handler
     * @return
     */
    public static Request addAddressGroup(String accessToken, String name, final int requestCode, final ResponseHandler handler)
    {
        // Build endpoint
        String endpoint = BuildConfig.DOMAIN + "/"
                + ApiEndpoint.LOCATION_API + "/"
                + ApiEndpoint.LOCATION_ADDRESS + "/"
                + ApiEndpoint.LOCATION_GROUP + "/"
                + ApiEndpoint.LOCATION_STORE;

        // Build request parameters
        Map<String, String> params = new HashMap<>();
        params.put(ApiKey.TYPE, name);

        // Build request
        GsonRequest<EvAddressGroupListResp> gsonRequest = new GsonRequest<>(Request.Method.POST, accessToken, endpoint, params, EvAddressGroupListResp.class,
                new GsonRequest.GsonResponseListener<EvAddressGroupListResp>() {
                    @Override
                    public void onResponse(EvAddressGroupListResp object) {
                        handler.onResponse(requestCode, object);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handler.onErrorResponse(requestCode, VolleyErrorHelper.getErrorMessage(error));
                    }
                });

        return gsonRequest;
    }

    /**
     * TODO
     * @param requestCode
     * @param handler
     * @return
     */
    public static Request getProvinceList(final int requestCode, final ResponseHandler handler)
    {
        // Build endpoint
        String endpoint = BuildConfig.DOMAIN + "/"
                + ApiEndpoint.LOCATION_PROVINCE;

        // Build request
        GsonRequest<EvProvinceListResp> gsonRequest = new GsonRequest<>(Request.Method.GET, null, endpoint, null, EvProvinceListResp.class,
                new GsonRequest.GsonResponseListener<EvProvinceListResp>() {
                    @Override
                    public void onResponse(EvProvinceListResp object) {
                        handler.onResponse(requestCode, object);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handler.onErrorResponse(requestCode, VolleyErrorHelper.getErrorMessage(error));
                    }
                });

        return gsonRequest;
    }

    /**
     * TODO
     * @param provinceId
     * @param requestCode
     * @param handler
     * @return
     */
    public static Request getCityList(Long provinceId, final int requestCode, final ResponseHandler handler)
    {
        // Build endpoint
        String endpoint = BuildConfig.DOMAIN + "/"
                + ApiEndpoint.LOCATION_CITY + "/"
                + Long.toString(provinceId);

        // Build request
        GsonRequest<EvCityListResp> gsonRequest = new GsonRequest<>(Request.Method.GET, null, endpoint, null, EvCityListResp.class,
                new GsonRequest.GsonResponseListener<EvCityListResp>() {
                    @Override
                    public void onResponse(EvCityListResp object) {
                        handler.onResponse(requestCode, object);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handler.onErrorResponse(requestCode, VolleyErrorHelper.getErrorMessage(error));
                    }
                });

        return gsonRequest;
    }

    /**
     * TODO
     * @param cityId
     * @param requestCode
     * @param handler
     * @return
     */
    public static Request getBarangayList(Long cityId, final int requestCode, final ResponseHandler handler)
    {
        // Build endpoint
        String endpoint = BuildConfig.DOMAIN + "/"
                + ApiEndpoint.LOCATION_BARANGAY + "/"
                + Long.toString(cityId);

        // Build request
        GsonRequest<EvBarangayListResp> gsonRequest = new GsonRequest<>(Request.Method.GET, null, endpoint, null, EvBarangayListResp.class,
                new GsonRequest.GsonResponseListener<EvBarangayListResp>() {
                    @Override
                    public void onResponse(EvBarangayListResp object) {
                        handler.onResponse(requestCode, object);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handler.onErrorResponse(requestCode, VolleyErrorHelper.getErrorMessage(error));
                    }
                });

        return gsonRequest;
    }

    /**
     * TODO
     * @param accessToken
     * @param fullName
     * @param contactNumber
     * @param addressGroup
     * @param unitNumber
     * @param buildingName
     * @param streetNumber
     * @param streetName
     * @param village
     * @param provinceId
     * @param cityId
     * @param barangayId
     * @param zipCode
     * @param latitude
     * @param longitude
     * @param addressType
     * @param requestCode
     * @param handler
     * @return
     */
    public static Request addAddressLocation(String accessToken, String fullName, String contactNumber, List<Long> addressGroup,
        String unitNumber, String buildingName, String streetNumber, String streetName, String village, Long provinceId,
        Long cityId, Long barangayId, String zipCode, Double latitude, Double longitude, String addressType,
                                             final int requestCode, final ResponseHandler handler)
    {
        // Build endpoint
        String endpoint = BuildConfig.DOMAIN + "/"
                + ApiEndpoint.LOCATION_API + "/"
                + ApiEndpoint.LOCATION_ADDRESS + "/"
                + ApiEndpoint.LOCATION_STORE;

        // Build request parameters
        EvAddAddressLocationReq evAddAddressLocationReq = new EvAddAddressLocationReq();
        evAddAddressLocationReq.fullName = fullName;
        evAddAddressLocationReq.contactNumber = contactNumber;
        evAddAddressLocationReq.addressGroup = addressGroup;
        evAddAddressLocationReq.unitNumber = unitNumber;
        evAddAddressLocationReq.buildingName = buildingName;
        evAddAddressLocationReq.streetNumber = streetNumber;
        evAddAddressLocationReq.streetName = streetName;
        evAddAddressLocationReq.village = village;
        evAddAddressLocationReq.provinceId = provinceId;
        evAddAddressLocationReq.cityId = cityId;
        evAddAddressLocationReq.barangayId = barangayId;
        evAddAddressLocationReq.zipCode = zipCode;
        evAddAddressLocationReq.latitude = latitude;
        evAddAddressLocationReq.longitude = longitude;
        evAddAddressLocationReq.addressType = addressType;

        Gson gson = new GsonBuilder().create();
        String requestBody = gson.toJson(evAddAddressLocationReq);

        logger.severe("request body: " + requestBody);

        // Build request
        GsonJsonRequest<EvBaseResp> gsonRequest = new GsonJsonRequest<>(Request.Method.POST, accessToken, endpoint, requestBody, EvBaseResp.class,
                new GsonJsonRequest.GsonResponseListener<EvBaseResp>() {
                    @Override
                    public void onResponse(EvBaseResp object) {
                        handler.onResponse(requestCode, object);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handler.onErrorResponse(requestCode, VolleyErrorHelper.getErrorMessage(error));
                    }
                });

        return gsonRequest;
    }
}
