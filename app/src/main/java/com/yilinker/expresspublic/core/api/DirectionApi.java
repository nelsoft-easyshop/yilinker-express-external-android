package com.yilinker.expresspublic.core.api;

import android.net.Uri;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.maps.model.LatLng;
import com.yilinker.expresspublic.ResponseHandler;
import com.yilinker.expresspublic.core.GsonRequest;
import com.yilinker.expresspublic.core.helpers.VolleyErrorHelper;
import com.yilinker.expresspublic.core.responses.directions.Direction;

import java.util.logging.Logger;

/**
 * Created by Jeico.
 */
public class DirectionApi
{
    private static final Logger logger = Logger.getLogger(DirectionApi.class.getSimpleName());

    private static final String SCHEME                                     = "https";
    private static final String AUTHORITY                                  = "maps.googleapis.com";
    private static final String PATH_MAPS                                  = "maps";
    private static final String PATH_API                                   = "api";
    private static final String PATH_DIRECTIONS                            = "directions";
    private static final String PATH_JSON                                  = "json";

    private static final String PARAM_ORIGIN                               = "origin";
    private static final String PARAM_DESTINATION                          = "destination";
    private static final String PARAM_KEY                                  = "key";

    public static Request getDirection(LatLng origin, LatLng destination, String key, final int requestCode, final ResponseHandler handler)
    {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(PATH_MAPS)
                .appendPath(PATH_API)
                .appendPath(PATH_DIRECTIONS)
                .appendPath(PATH_JSON)
                .appendQueryParameter(PARAM_ORIGIN, origin.latitude + "," + origin.longitude)
                .appendQueryParameter(PARAM_DESTINATION, destination.latitude + "," + destination.longitude)
                .appendQueryParameter(PARAM_KEY, key);

        String endpoint = builder.build().toString();
        logger.severe("endpoint: " + endpoint);

        GsonRequest<Direction> gsonRequest = new GsonRequest<>(Request.Method.GET, null, endpoint, null, Direction.class,
                new GsonRequest.GsonResponseListener<Direction>() {
                    @Override
                    public void onResponse(Direction object) {
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
