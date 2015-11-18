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
import com.yilinker.expresspublic.core.responses.EvBranchListResp;
import com.yilinker.expresspublic.core.responses.bases.EvBaseResp;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by Jeico.
 */
public class BranchApi
{
    private static final Logger logger = Logger.getLogger(BranchApi.class.getSimpleName());

    /**
     * TODO
     * @param requestCode
     * @param handler
     * @return
     */

    // Modified parameter and added access_token
    /*
    public static Request getAllBranch(final int requestCode, final ResponseHandler handler)
    {
        // Build endpoint
        String endpoint = BuildConfig.DOMAIN + "/"
                + ApiEndpoint.BRANCH_API;

        // Build request
        GsonRequest<EvBranchListResp> gsonRequest = new GsonRequest<>(Request.Method.GET, null, endpoint, null, EvBranchListResp.class,
                new GsonRequest.GsonResponseListener<EvBranchListResp>() {
                    @Override
                    public void onResponse(EvBranchListResp object) {
                        handler.onResponse(requestCode, object);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handler.onErrorResponse(requestCode, VolleyErrorHelper.getErrorMessage(error));
                    }
                });*/

    public static Request getAllBranch(final int requestCode, String accessToken, final ResponseHandler handler)
    {


        // Modified endpoint and request method
        // Build endpoint
        String endpoint = BuildConfig.DOMAIN + "/"
                + ApiEndpoint.M_BRANCH + "/"
                + ApiEndpoint.BRANCH_API + "/"
                + ApiEndpoint.BRANCH_API_GET;

        // Build request
        GsonRequest<EvBranchListResp> gsonRequest = new GsonRequest<>(Request.Method.POST, accessToken, endpoint, null, EvBranchListResp.class,
                new GsonRequest.GsonResponseListener<EvBranchListResp>() {
                    @Override
                    public void onResponse(EvBranchListResp object) {
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
     * @param branchId
     * @param requestCode
     * @param handler
     * @return
     */
    public static Request bookmark(String accessToken, Long branchId, final int requestCode, final ResponseHandler handler)
    {
        // Build endpoint
        String endpoint = BuildConfig.DOMAIN + "/"
                + ApiEndpoint.BRANCH_API + "/"
                + ApiEndpoint.BRANCH_BOOKMARK;

        // Build request parameters
        Map<String, String> params = new HashMap<>();
        params.put(ApiKey.BRANCH_ID, Long.toString(branchId));

        // Build request
        GsonRequest<EvBaseResp> gsonRequest = new GsonRequest<>(Request.Method.POST, accessToken, endpoint, params, EvBaseResp.class,
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
     * @param branchId
     * @param requestCode
     * @param handler
     * @return
     */
    public static Request unbookmark(String accessToken, Long branchId, final int requestCode, final ResponseHandler handler)
    {
        // Build endpoint
        String endpoint = BuildConfig.DOMAIN + "/"
                + ApiEndpoint.BRANCH_API + "/"
                + ApiEndpoint.BRANCH_UNBOOKMARK;

        // Build request parameters
        Map<String, String> params = new HashMap<>();
        params.put(ApiKey.BRANCH_ID, Long.toString(branchId));

        // Build request
        GsonRequest<EvBaseResp> gsonRequest = new GsonRequest<>(Request.Method.POST, accessToken, endpoint, params, EvBaseResp.class,
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
     * @param cityId
     * @param requestCode
     * @param handler
     * @return
     */
    public static Request searchBranchesByCity(Long cityId, final int requestCode, final ResponseHandler handler)
    {
        // Build endpoint
        String endpoint = BuildConfig.DOMAIN + "/"
                + ApiEndpoint.BRANCH_API + "/"
                + ApiEndpoint.BRANCH_AREA + "/"
                + Long.toString(cityId);

        // Build request
        GsonRequest<EvBranchListResp> gsonRequest = new GsonRequest<>(Request.Method.GET, null, endpoint, null, EvBranchListResp.class,
                new GsonRequest.GsonResponseListener<EvBranchListResp>() {
                    @Override
                    public void onResponse(EvBranchListResp object) {
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
     * @param keyword
     * @param requestCode
     * @param handler
     * @return
     */
    // Added additional parameter access token
    public static Request searchBranchesByKeyword(String keyword, String accessToken, final int requestCode, final ResponseHandler handler)
    {
    /*public static Request searchBranchesByKeyword(String keyword, final int requestCode, final ResponseHandler handler)
    {*/
        // Modified endpoint and added query string for search
        /*// Build endpoint
        String endpoint = BuildConfig.DOMAIN + "/"
                + ApiEndpoint.BRANCH_API + "/"
                + ApiEndpoint.BRANCH_SEARCH;

        // Build request parameters
        Map<String, String> params = new HashMap<>();
        params.put(ApiKey.KEYWORD, keyword);

        // Build request
        GsonRequest<EvBranchListResp> gsonRequest = new GsonRequest<>(Request.Method.POST, null, endpoint, params, EvBranchListResp.class,
                new GsonRequest.GsonResponseListener<EvBranchListResp>() {
                    @Override
                    public void onResponse(EvBranchListResp object) {
                        handler.onResponse(requestCode, object);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handler.onErrorResponse(requestCode, VolleyErrorHelper.getErrorMessage(error));
                    }
                });*/

        // Build endpoint
        String endpoint = BuildConfig.DOMAIN + "/"
                + ApiEndpoint.M_BRANCH + "/"
                + ApiEndpoint.BRANCH_API + "/"
                + ApiEndpoint.BRANCH_API_GET;

        // Build request parameters
        Map<String, String> params = new HashMap<>();
        params.put(ApiKey.QUERY, keyword);

        // Build request
        GsonRequest<EvBranchListResp> gsonRequest = new GsonRequest<>(Request.Method.POST, accessToken, endpoint, params, EvBranchListResp.class,
                new GsonRequest.GsonResponseListener<EvBranchListResp>() {
                    @Override
                    public void onResponse(EvBranchListResp object) {
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
    public static Request getBookmarkedBranches(String accessToken, final int requestCode, final ResponseHandler handler)
    {
        // Build endpoint
        String endpoint = BuildConfig.DOMAIN + "/"
                + ApiEndpoint.BRANCH_API + "/"
                + ApiEndpoint.BRANCH_ME + "/"
                + ApiEndpoint.BRANCH_BOOKMARKED;

        // Build request
        GsonRequest<EvBranchListResp> gsonRequest = new GsonRequest<>(Request.Method.GET, accessToken, endpoint, null, EvBranchListResp.class,
                new GsonRequest.GsonResponseListener<EvBranchListResp>() {
                    @Override
                    public void onResponse(EvBranchListResp object) {
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
