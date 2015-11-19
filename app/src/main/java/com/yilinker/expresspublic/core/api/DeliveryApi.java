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
import com.yilinker.expresspublic.core.helpers.VolleyErrorHelper;
import com.yilinker.expresspublic.core.requests.EvBookDeliveryReq;
import com.yilinker.expresspublic.core.responses.EvBookDeliveryResponse;
import com.yilinker.expresspublic.core.responses.EvDeliveryPackageListResp;
import com.yilinker.expresspublic.core.responses.EvDeliveryPackageResp;
import com.yilinker.expresspublic.core.responses.EvPackageContainerResp;
import com.yilinker.expresspublic.core.responses.EvPickUpScheduleListResp;
import com.yilinker.expresspublic.core.responses.bases.EvBaseResp;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by Jeico.
 */
public class DeliveryApi
{
    private static final Logger logger = Logger.getLogger(DeliveryApi.class.getSimpleName());

    /**
     * TODO
     * @param width
     * @param height
     * @param length
     * @param weight
     * @param requestCode
     * @param handler
     * @return
     */
    public static Request getPackageContainer(String width, String height, String length, String weight, final int requestCode, final ResponseHandler handler)
    {
        // Build endpoint
        String endpoint = BuildConfig.DOMAIN + "/"
                + ApiEndpoint.DELIVERY_API + "/"
                + ApiEndpoint.DELIVERY_BOOK + "/"
                + ApiEndpoint.DELIVERY_PACKAGE_CONTAINER;

        // Build request parameters
        Map<String, String> params = new HashMap<>();
        params.put(ApiKey.LENGTH, length);
        params.put(ApiKey.WIDTH, width);
        params.put(ApiKey.HEIGHT, height);
        params.put(ApiKey.WEIGHT, weight);

        // Build request
        GsonRequest<EvPackageContainerResp> gsonRequest = new GsonRequest<>(Request.Method.POST, null, endpoint, params, EvPackageContainerResp.class,
                new GsonRequest.GsonResponseListener<EvPackageContainerResp>() {
                    @Override
                    public void onResponse(EvPackageContainerResp object) {
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


    public static Request getPickUpScheduleList(final int requestCode, final ResponseHandler handler)
    {
        // Build endpoint
        String endpoint = BuildConfig.DOMAIN + "/"
                + ApiEndpoint.DELIVERY_API + "/"
                + ApiEndpoint.DELIVERY_PICKUP_SCHEDULE;

        // Build request
        GsonRequest<EvPickUpScheduleListResp> gsonRequest = new GsonRequest<>(Request.Method.GET, null, endpoint, null, EvPickUpScheduleListResp.class,
                new GsonRequest.GsonResponseListener<EvPickUpScheduleListResp>() {
                    @Override
                    public void onResponse(EvPickUpScheduleListResp object) {
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

    public static Request bookDelivery(final int requestCode, String accessToken, EvBookDeliveryReq evBookDeliveryReq, final ResponseHandler handler)
    {
        // Build endpoint
        String endpoint = BuildConfig.DOMAIN + "/"
                + ApiEndpoint.PACKAGES_API + "/"
                + ApiEndpoint.PACKAGES_ADD;

        // Build request parameters
        Map<String, String> params = new HashMap<>();
//        params.put(ApiKey.ACCESS_TOKEN, accessToken);
        params.put(ApiKey.SENDER_ADDRESS, String.valueOf(evBookDeliveryReq.getRecipientAddressId()));
        params.put(ApiKey.RECIPIENT_ADDRESS, String.valueOf(evBookDeliveryReq.getSenderAddressId()));
        params.put(ApiKey.PACKAGE_DESCRIPTION, evBookDeliveryReq.getPackageName());
        params.put(ApiKey.IS_PAID_BY_RECIPIENT, evBookDeliveryReq.getPaidBy());
        params.put(ApiKey.DECLARED_VALUE, evBookDeliveryReq.getDeclaredValue());
        params.put(ApiKey.PACKAGE_COUNT, String.valueOf(evBookDeliveryReq.getQuantity()));
        params.put(ApiKey.WEIGHT, String.valueOf(evBookDeliveryReq.getWeight()));
        params.put(ApiKey.LENGTH, String.valueOf(evBookDeliveryReq.getLength()));
        params.put(ApiKey.WIDTH, String.valueOf(evBookDeliveryReq.getLength()));
        params.put(ApiKey.HEIGHT, String.valueOf(evBookDeliveryReq.getHeight()));

        // Build request
        GsonRequest<EvBookDeliveryResponse> gsonRequest = new GsonRequest<>(Request.Method.POST, accessToken, endpoint, params, EvBookDeliveryResponse.class,
                new GsonRequest.GsonResponseListener<EvBookDeliveryResponse>() {
                    @Override
                    public void onResponse(EvBookDeliveryResponse object) {
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
