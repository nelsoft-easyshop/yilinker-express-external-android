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
import com.yilinker.expresspublic.core.responses.EvBranchListResp;
import com.yilinker.expresspublic.core.responses.EvMeResp;
import com.yilinker.expresspublic.core.responses.bases.EvBaseResp;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class UserApi
{
    private static final Logger logger = Logger.getLogger(UserApi.class.getSimpleName());

    /**
     * TODO
     * @param requestCode
     * @param handler
     * @return
     */
    public static Request getUserProfile(String accessToken, final int requestCode, final ResponseHandler handler)
    {
        // Build endpoint
        String endpoint = BuildConfig.DOMAIN + "/"
                + ApiEndpoint.USER_API;

        // Build request
        GsonRequest<EvMeResp> gsonRequest = new GsonRequest<>(Request.Method.GET, accessToken, endpoint, null, EvMeResp.class,
                new GsonRequest.GsonResponseListener<EvMeResp>() {
                    @Override
                    public void onResponse(EvMeResp object) {
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
     * @param firstname
     * @param lastname
     * @param emailAddress
     * @param requestCode
     * @param handler
     * @return
     */
    public static Request updateProfile(String accessToken, String firstname, String lastname, String emailAddress, final int requestCode, final ResponseHandler handler)
    {
        // Build endpoint
        String endpoint = BuildConfig.DOMAIN + "/"
                + ApiEndpoint.USER_API + "/"
                + ApiEndpoint.USER_UPDATE_PROFILE;

        // Build request parameters
        Map<String, String> params = new HashMap<>();
        params.put(ApiKey.FIRST_NAME, firstname);
        params.put(ApiKey.LAST_NAME, lastname);
        params.put(ApiKey.EMAIL_ADDRESS, emailAddress);

        // Build request
        GsonRequest<EvMeResp> gsonRequest = new GsonRequest<>(Request.Method.POST, accessToken, endpoint, params, EvMeResp.class,
                new GsonRequest.GsonResponseListener<EvMeResp>() {
                    @Override
                    public void onResponse(EvMeResp object) {
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
     * @param contactNumber
     * @param verificationCode
     * @param requestCode
     * @param handler
     * @return
     */
    public static Request updateMobile(String accessToken, String contactNumber, String verificationCode, final int requestCode, final ResponseHandler handler)
    {
        // Build endpoint
        String endpoint = BuildConfig.DOMAIN + "/"
                + ApiEndpoint.USER_API + "/"
                + ApiEndpoint.USER_UPDATE_MOBILE;

        // Build request parameters
        Map<String, String> params = new HashMap<>();
        params.put(ApiKey.CONTACT_NUMBER, contactNumber);
        params.put(ApiKey.VERIFICATION_CODE, verificationCode);

        // Build request
        GsonRequest<EvMeResp> gsonRequest = new GsonRequest<>(Request.Method.POST, accessToken, endpoint, params, EvMeResp.class,
                new GsonRequest.GsonResponseListener<EvMeResp>() {
                    @Override
                    public void onResponse(EvMeResp object) {
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
     * @param oldPassword
     * @param password
     * @param passwordConfirmation
     * @param requestCode
     * @param handler
     * @return
     */
    public static Request updatePassword(String accessToken, String oldPassword, String password, String passwordConfirmation, final int requestCode, final ResponseHandler handler)
    {
        // Build endpoint
        String endpoint = BuildConfig.DOMAIN + "/"
                + ApiEndpoint.USER_API + "/"
                + ApiEndpoint.USER_UPDATE_PASSWORD;

        // Build request parameters
        Map<String, String> params = new HashMap<>();
        params.put(ApiKey.OLD_PASSWORD, oldPassword);
        params.put(ApiKey.PASSWORD, password);
        params.put(ApiKey.PASSWORD_CONFIRMATION, passwordConfirmation);

        // Build request
        GsonRequest<EvMeResp> gsonRequest = new GsonRequest<>(Request.Method.POST, accessToken, endpoint, params, EvMeResp.class,
                new GsonRequest.GsonResponseListener<EvMeResp>() {
                    @Override
                    public void onResponse(EvMeResp object) {
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
