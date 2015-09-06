package com.yilinker.expresspublic.core.api;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.yilinker.expresspublic.BuildConfig;
import com.yilinker.expresspublic.ResponseHandler;
import com.yilinker.expresspublic.core.GsonRequest;
import com.yilinker.expresspublic.core.contants.ApiEndpoint;
import com.yilinker.expresspublic.core.helpers.VolleyErrorHelper;
import com.yilinker.expresspublic.core.responses.EvDeliveryPackageListResp;
import com.yilinker.expresspublic.core.responses.EvDeliveryPackageResp;
import com.yilinker.expresspublic.core.responses.bases.EvBaseResp;

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
                + ApiEndpoint.TRACK_API + "/"
                + ApiEndpoint.TRACK_ONGOING;

        // Build request
        GsonRequest<EvBaseResp> gsonRequest = new GsonRequest<>(Request.Method.GET, accessToken, endpoint, null, EvBaseResp.class,
                new GsonRequest.GsonResponseListener<EvBaseResp>() {
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
                + ApiEndpoint.TRACK_API + "/"
                + ApiEndpoint.TRACK_DELIVERED;

        // Build request
        GsonRequest<EvBaseResp> gsonRequest = new GsonRequest<>(Request.Method.GET, accessToken, endpoint, null, EvBaseResp.class,
                new GsonRequest.GsonResponseListener<EvBaseResp>() {
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

    /**
     * TODO
     * @param trackingNumber
     * @param requestCode
     * @param handler
     * @return
     */
    public static Request searchTrackingNumber(String trackingNumber, final int requestCode, final ResponseHandler handler)
    {
        // Build endpoint
        String endpoint = BuildConfig.DOMAIN + "/"
                + ApiEndpoint.TRACK_API + "/"
                + trackingNumber;

        // Build request
        GsonRequest<EvDeliveryPackageResp> gsonRequest = new GsonRequest<>(Request.Method.GET, null, endpoint, null, EvDeliveryPackageResp.class,
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
