package com.yilinker.expresspublic.core.api;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.yilinker.expresspublic.BuildConfig;
import com.yilinker.expresspublic.ResponseHandler;
import com.yilinker.expresspublic.core.GsonRequest;
import com.yilinker.expresspublic.core.contants.ApiEndpoint;
import com.yilinker.expresspublic.core.contants.ApiKey;
import com.yilinker.expresspublic.core.enums.GrantType;
import com.yilinker.expresspublic.core.helpers.VolleyErrorHelper;
import com.yilinker.expresspublic.core.models.OAuth;
import com.yilinker.expresspublic.core.responses.EvRegisterResp;
import com.yilinker.expresspublic.core.responses.bases.EvBaseResp;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class OAuthApi
{
    private static final Logger logger = Logger.getLogger(OAuthApi.class.getSimpleName());

    /**
     * TODO
     * @param username
     * @param password
     * @param requestCode
     * @param handler
     * @return
     */
    public static Request token(String username, String password, final int requestCode, final ResponseHandler handler)
    {
        // Build endpoint
//        String endpoint = BuildConfig.DOMAIN + "/"
//                + ApiEndpoint.OAUTH_API + "/"
//                + ApiEndpoint.OAUTH_TOKEN;

        //new endpoint
        String endpoint = BuildConfig.DOMAIN + "/"
                + ApiEndpoint.OAUTH_TOKEN;


        // Build request parameters
        Map<String, String> params = new HashMap<>();
        params.put(ApiKey.GRANT_TYPE, GrantType.PASSWORD.getValue());
        params.put(ApiKey.CLIENT_ID, BuildConfig.CLIENT_ID);
        params.put(ApiKey.CLIENT_SECRET, BuildConfig.CLIENT_SECRET);
        params.put(ApiKey.USERNAME, username);
        params.put(ApiKey.PASSWORD, password);

        // Build request
        GsonRequest<OAuth> gsonRequest = new GsonRequest<>(Request.Method.POST, null, endpoint, params, OAuth.class,
                new GsonRequest.GsonResponseListener<OAuth>() {
                    @Override
                    public void onResponse(OAuth object) {
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
     * @param firstname
     * @param lastname
     * @param emailAddress
     * @param contactNumber
     * @param password
     * @param requestCode
     * @param handler
     * @return
     */
    public static Request register(String firstname, String lastname, String emailAddress,
                                   String contactNumber, String password, final int requestCode, final ResponseHandler handler)
    {
        // Build endpoint
        String endpoint = BuildConfig.DOMAIN + "/"
                + ApiEndpoint.OAUTH_API + "/"
                + ApiEndpoint.OAUTH_REGISTER;

        // Build request parameters
        Map<String, String> params = new HashMap<>();
        params.put(ApiKey.EMAIL_ADDRESS, emailAddress);
        params.put(ApiKey.PASSWORD, password);
        params.put(ApiKey.FIRST_NAME, firstname);
        params.put(ApiKey.LAST_NAME, lastname);
        params.put(ApiKey.CONTACT_NUMBER, contactNumber);

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
