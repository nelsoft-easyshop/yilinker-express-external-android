package com.yilinker.expresspublic.core.api;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.yilinker.expresspublic.BuildConfig;
import com.yilinker.expresspublic.ResponseHandler;
import com.yilinker.expresspublic.core.GsonRequest;
import com.yilinker.expresspublic.core.contants.ApiEndpoint;
import com.yilinker.expresspublic.core.helpers.VolleyErrorHelper;
import com.yilinker.expresspublic.core.responses.EvSliderListResp;

import java.util.logging.Logger;

/**
 * Created by Jeico.
 */
public class HomeApi
{
    private static final Logger logger = Logger.getLogger(HomeApi.class.getSimpleName());

    /**
     * TODO
     * @param requestCode
     * @param handler
     * @return
     */
    public static Request getSliderList(final int requestCode, final ResponseHandler handler)
    {
        // Build endpoint
        String endpoint = BuildConfig.DOMAIN + "/"
                + ApiEndpoint.HOME_API;

        // Build request
        GsonRequest<EvSliderListResp> gsonRequest = new GsonRequest<>(Request.Method.GET, null, endpoint, null, EvSliderListResp.class,
                new GsonRequest.GsonResponseListener<EvSliderListResp>() {
                    @Override
                    public void onResponse(EvSliderListResp object) {
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
