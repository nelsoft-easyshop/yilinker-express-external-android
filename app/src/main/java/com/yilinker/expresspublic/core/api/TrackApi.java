package com.yilinker.expresspublic.core.api;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.common.api.Api;
import com.yilinker.expresspublic.BuildConfig;
import com.yilinker.expresspublic.ResponseHandler;
import com.yilinker.expresspublic.core.GsonRequest;
import com.yilinker.expresspublic.core.contants.ApiEndpoint;
import com.yilinker.expresspublic.core.contants.ApiKey;
import com.yilinker.expresspublic.core.enums.ShipmentType;
import com.yilinker.expresspublic.core.helpers.VolleyErrorHelper;
import com.yilinker.expresspublic.core.responses.EvDeliveryPackageListResp;
import com.yilinker.expresspublic.core.responses.EvDeliveryPackageResp;
import com.yilinker.expresspublic.core.responses.bases.EvBaseResp;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by Jeico.
 */
public class TrackApi
{
    private static final Logger logger = Logger.getLogger(TrackApi.class.getSimpleName());

    /**
     * TODO
     * @param accessToken
     * @param requestCode
     * @param handler
     * @return
     */
    public static Request ongoing(String accessToken, final int requestCode, final ResponseHandler handler)
    {
        // Build endpoint
        String endpoint = BuildConfig.DOMAIN + "/"
                + ApiEndpoint.TRACKING_API + "/"
                + ApiEndpoint.TRACKING_PACKAGES;
//                + ApiEndpoint.TRACK_PACKAGE + "/"
//                + ApiEndpoint.TRACK_ONGOING;

        Map<String, String> params = new HashMap<>();
        params.put(ApiKey.ACCESS_TOKEN, accessToken);
        params.put(ApiKey.STATUS, ShipmentType.ONGOING.getValue());

        // Build request
        GsonRequest<EvDeliveryPackageListResp> gsonRequest = new GsonRequest<>(Request.Method.POST, accessToken, endpoint,params, EvDeliveryPackageListResp.class,
                new GsonRequest.GsonResponseListener<EvDeliveryPackageListResp>() {
                    @Override
                    public void onResponse(EvDeliveryPackageListResp object) {
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
     * @param requestCode
     * @param handler
     * @return
     */
    public static Request delivered(String accessToken, final int requestCode, final ResponseHandler handler)
    {
        // Build endpoint
        String endpoint = BuildConfig.DOMAIN + "/"
                + ApiEndpoint.TRACKING_API + "/"
                + ApiEndpoint.TRACKING_PACKAGES;
//                + ApiEndpoint.TRACK_PACKAGE + "/"
//                + ApiEndpoint.TRACK_DELIVERED;

        Map<String, String> params = new HashMap<>();
        params.put(ApiKey.ACCESS_TOKEN, accessToken);
        params.put(ApiKey.STATUS, ShipmentType.DELIVERED.getValue());

        // Build request
        GsonRequest<EvDeliveryPackageListResp> gsonRequest = new GsonRequest<>(Request.Method.POST, accessToken, endpoint, params, EvDeliveryPackageListResp.class,
                new GsonRequest.GsonResponseListener<EvDeliveryPackageListResp>() {
                    @Override
                    public void onResponse(EvDeliveryPackageListResp object) {
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
     * @param trackingNumber
     * @param requestCode
     * @param handler
     * @return
     */
    public static Request searchTrackingNumber(String accessToken, String trackingNumber, final int requestCode, final ResponseHandler handler)
//    public static Request searchTrackingNumber(String trackingNumber, final int requestCode, final ResponseHandler handler)
    {
        // Build endpoint
        String endpoint = BuildConfig.DOMAIN + "/"
                + ApiEndpoint.TRACKING_API + "/"
                + ApiEndpoint.TRACKING_PACKAGES;
//                + ApiEndpoint.TRACK_API + "/"
//                + trackingNumber;

        Map<String, String> params = new HashMap<>();
        params.put(ApiKey.ACCESS_TOKEN, accessToken);
        params.put(ApiKey.TRACKING_NUMBER, trackingNumber);

        // Build request
        GsonRequest<EvDeliveryPackageResp> gsonRequest = new GsonRequest<>(Request.Method.POST, accessToken, endpoint, params, EvDeliveryPackageResp.class,
                new GsonRequest.GsonResponseListener<EvDeliveryPackageResp>() {
                    @Override
                    public void onResponse(EvDeliveryPackageResp object) {
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
