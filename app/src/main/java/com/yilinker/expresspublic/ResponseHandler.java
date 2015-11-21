package com.yilinker.expresspublic;

import com.android.volley.VolleyError;

public interface ResponseHandler
{
    public void onResponse(int requestCode, Object object);
    public void onErrorResponse(int requestCode, String message);
}
