/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yilinker.expresspublic.core.services.gcm;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.android.volley.Request;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.yilinker.expresspublic.R;
import com.yilinker.expresspublic.ResponseHandler;
import com.yilinker.expresspublic.core.api.DeviceApi;
import com.yilinker.expresspublic.core.contants.RequestCode;

import java.util.logging.Logger;

public class RegistrationIntentService extends IntentService implements ResponseHandler
{
    private static final Logger logger = Logger.getLogger(RegistrationIntentService.class.getSimpleName());

    private static final String TAG = "RegIntentService";

    public RegistrationIntentService()
    {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        try
        {
            // In the (unlikely) event that multiple refresh operations occur simultaneously,
            // ensure that they are processed sequentially.
            synchronized (TAG)
            {

                // [START register_for_gcm]
                // Initially this call goes out to the network to retrieve the token, subsequent calls
                // are local.
                // [START get_token]
                InstanceID instanceID = InstanceID.getInstance(this);
                String registrationId = instanceID.getToken(getString(R.string.gcm_senderId),
                        GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                // [END get_token]
                logger.severe("GCM Registration Token: " + registrationId);

                // TODO: Implement this method to send any registration to your app's servers.
                sendRegistrationToServer(registrationId);
                // [END register_for_gcm]
            }
        }
        catch (Exception e)
        {
            logger.severe("Failed to complete token refresh");
        }
    }

    /**
     * Persist registration to third-party servers.
     *
     * Modify this method to associate the user's GCM registration token with any server-side account
     * maintained by your application.
     *
     * @param registrationId The new token.
     */
    private void sendRegistrationToServer(String registrationId)
    {
        Request request = DeviceApi.addRegistrationId(registrationId, RequestCode.RCR_ADD_REGISTRATION_ID, this);
        Volley.newRequestQueue(getApplicationContext()).add(request);
    }

    @Override
    public void onResponse(int requestCode, Object object) {

    }

    @Override
    public void onErrorResponse(int requestCode, String message) {

    }
}
