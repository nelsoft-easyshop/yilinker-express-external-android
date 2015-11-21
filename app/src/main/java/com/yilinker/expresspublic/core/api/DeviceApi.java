package com.yilinker.expresspublic.core.api;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.yilinker.expresspublic.ResponseHandler;
import com.yilinker.expresspublic.core.GsonRequest;
import com.yilinker.expresspublic.core.contants.ApiKey;
import com.yilinker.expresspublic.core.helpers.VolleyErrorHelper;
import com.yilinker.expresspublic.core.responses.bases.EvBaseResp;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by Jeico.
 */
public class DeviceApi
{
    private static final Logger logger = Logger.getLogger(DeviceApi.class.getSimpleName());

    /**
     * This method creates a request that will add registration id that is
     * associated with the user
     * @param registrationId
     * @param handler
     * @return
     */
    public static Request addRegistrationId(String registrationId, final int requestCode, final ResponseHandler handler) {
        // Build endpoint
        String endpoint = null;

        // Build request parameters
        Map<String, String> params = new HashMap<String, String>();
        params.put(ApiKey.REGISTRATION_ID, registrationId);

        // Build request
        GsonRequest<EvBaseResp> gsonRequest = new GsonRequest<>(Request.Method.POST, null, endpoint, params, EvBaseResp.class,
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
}
