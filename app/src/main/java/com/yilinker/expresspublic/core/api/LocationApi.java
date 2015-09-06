package com.yilinker.expresspublic.core.api;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.yilinker.expresspublic.BuildConfig;
import com.yilinker.expresspublic.ResponseHandler;
import com.yilinker.expresspublic.core.GsonRequest;
import com.yilinker.expresspublic.core.contants.ApiEndpoint;
import com.yilinker.expresspublic.core.helpers.VolleyErrorHelper;
import com.yilinker.expresspublic.core.responses.EvGetAllProvinceAndCityResp;

import java.util.logging.Logger;

/**
 * Created by Jeico.
 */
public class LocationApi
{
    private static final Logger logger = Logger.getLogger(LocationApi.class.getSimpleName());

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
}
