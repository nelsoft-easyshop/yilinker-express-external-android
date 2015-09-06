package com.yilinker.expresspublic.core.helpers;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yilinker.expresspublic.core.responses.bases.EvBaseResp;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

/**
 * Created by Jeico on 8/21/2015.
 */
public class VolleyErrorHelper
{
    private static final Logger logger = Logger.getLogger(VolleyErrorHelper.class.getSimpleName());

    public static String getErrorMessage(VolleyError error)
    {
        String errorMessage = "Error encountered.";

        NetworkResponse response = error.networkResponse;

        if (response != null)
        {
            try
            {
                String responseRaw = new String(
                        response.data, HttpHeaderParser.parseCharset(error.networkResponse.headers));

                logger.severe("Error response: " + responseRaw);

                Gson gson = new GsonBuilder().create();

                EvBaseResp evBaseResp = gson.fromJson(responseRaw, EvBaseResp.class);

                errorMessage = evBaseResp.message;
            }
            catch (UnsupportedEncodingException e1)
            {
                e1.printStackTrace();
            }
        }

        return errorMessage;
    }
}
