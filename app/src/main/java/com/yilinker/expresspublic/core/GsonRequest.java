package com.yilinker.expresspublic.core;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.yilinker.expresspublic.BuildConfig;
import com.yilinker.expresspublic.core.deserializer.DateDeserializer;
import com.yilinker.expresspublic.core.enums.TokenType;
import com.yilinker.expresspublic.core.serializer.DateSerializer;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by Arci.Malabanan on 3/2/2015.
 */
public class GsonRequest<T> extends Request<T>
{
    private static final Logger logger = Logger.getLogger(GsonRequest.class.getSimpleName());

    private static final String CONTENT_TYPE = "application/x-www-form-urlencoded";

    /**
     * Default timeout in milliseconds
     */
//    private final int TIMEOUT_MS = 3000;
    private final int TIMEOUT_MS = 5000;

    /**
     * Default max retries
     */
    private final int MAX_RETRIES = 3;

    /**
     * Default backoff multiplier
     */
    private final float BACKOFF_MULTIPLIER = 1f;

    /**
     * Access Token
     */
    private String accessToken;

    /**
     * Request parameters
     */
    private Map<String, String> params;

    /**
     * The class of response
     */
    private final Class<T> clazz;

    /**
     * Used to serialize/deserialize response
     */
    private final Gson gson;

    /**
     * Listener used when a response is received
     */
    private final GsonResponseListener<T> listener;

    /**
     * Listener when a response is received
     *
     * @param <T> type of response
     */
    public interface GsonResponseListener<T>
    {
        public void onResponse(T object);
    }

    /**
     * Constructor
     *
     * @param method
     * @param url
     * @param params
     * @param clazz
     * @param listener
     * @param errorListener
     */
    public GsonRequest(int method, String accessToken, String url, Map<String, String> params, Class<T> clazz,
                       GsonResponseListener<T> listener, Response.ErrorListener errorListener)
    {
        super(method, url, errorListener);

        this.accessToken = accessToken;
        this.params = params;
        this.clazz = clazz;
        this.listener = listener;
        this.gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateDeserializer())
                .registerTypeAdapter(Date.class, new DateSerializer())
                .setPrettyPrinting()
                .create();

        setRetryPolicy(new DefaultRetryPolicy(TIMEOUT_MS, MAX_RETRIES, BACKOFF_MULTIPLIER));
    }

    @Override
    public Map<String, String> getHeaders() throws
            AuthFailureError
    {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", CONTENT_TYPE);

        if(accessToken != null)
        {
            headers.put("Authorization", TokenType.BEARER.getValue() + " " + accessToken);
        }

        return headers;
    }

    @Override
    protected Map<String, String> getParams() throws
            AuthFailureError
    {
        return params != null ? params : super.getParams();
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response)
    {
        try
        {
            String responseRaw = new String(
                    response.data, HttpHeaderParser.parseCharset(response.headers));

            logger.severe("RESPONSE RAW: " + responseRaw);
            T object = gson.fromJson(responseRaw, clazz);

            if(BuildConfig.DEBUG)
            {
                printRequestDetails(object);
            }

            return Response.success(object, HttpHeaderParser.parseCacheHeaders(response));
        }
        catch (UnsupportedEncodingException e)
        {
            return Response.error(new ParseError(e));
        }
        catch (JsonSyntaxException e)
        {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response)
    {
        listener.onResponse(response);
    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError)
    {
        return super.parseNetworkError(volleyError);
    }

    @Override
    public void deliverError(VolleyError error)
    {
        super.deliverError(error);
    }

    /**
     * This method displays the url. request headers, request params and response.
     * @param object
     */
    private void printRequestDetails(T object)
    {
        try
        {
            logger.severe("=============================================");
            logger.severe("Endpoint: " + getUrl());
            logger.severe("=============================================");
            logger.severe("Request Headers: ");
            if (getHeaders() != null)
            {
                for (Map.Entry<String, String> requestHeaders : getHeaders().entrySet())
                {
                    logger.severe(requestHeaders.getKey() + " : " + requestHeaders.getValue());
                }
            }
            logger.severe("=============================================");
            logger.severe("Request Parameters: ");
            if (getParams() != null)
            {
                for (Map.Entry<String, String> requestParams : getParams().entrySet())
                {
                    logger.severe(requestParams.getKey() + " : " + requestParams.getValue());
                }
            }
            logger.severe("=============================================");
            logger.severe("Response: ");
            logger.severe(gson.toJson(object));
        }
        catch (AuthFailureError authFailureError)
        {
            authFailureError.printStackTrace();
        }
    }
}
