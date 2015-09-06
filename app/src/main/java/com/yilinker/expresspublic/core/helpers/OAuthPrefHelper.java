package com.yilinker.expresspublic.core.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.yilinker.expresspublic.core.models.OAuth;

import java.util.logging.Logger;

/**
 * Created by Jeico.
 */
public class OAuthPrefHelper
{
    private static final Logger logger = Logger.getLogger(OAuthPrefHelper.class.getSimpleName());

    private static final String PREF_OAUTH                              = "pref_oauth";
    private static final String ACCESS_TOKEN                            = "access_token";
    private static final String TOKEN_TYPE                              = "token_type";
    private static final String EXPIRES_IN                              = "expires_in";
    private static final String REFRESH_TOKEN                           = "refresh_token";

    /**
     * TODO
     * @param context
     * @return
     */
    public static OAuth getOAuth(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_OAUTH, Context.MODE_PRIVATE);
        String accessToken = sharedPreferences.getString(ACCESS_TOKEN, "");
        String tokenType = sharedPreferences.getString(TOKEN_TYPE, "");
        Long expiresIn = sharedPreferences.getLong(EXPIRES_IN, 0);
        String refreshToken = sharedPreferences.getString(REFRESH_TOKEN, "");

        return new OAuth(accessToken, tokenType, expiresIn, refreshToken);
    }

    /**
     * TODO
     * @param context
     * @param oAuth
     */
    public static void setOAuth(Context context, OAuth oAuth)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_OAUTH, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ACCESS_TOKEN, oAuth.getAccessToken());
        editor.putString(TOKEN_TYPE, oAuth.getTokenType());
        editor.putLong(EXPIRES_IN, oAuth.getExpiresIn());
        editor.putString(REFRESH_TOKEN, oAuth.getRefreshToken());
        editor.apply();
    }

    /**
     * TODO
     * @param context
     */
    public static void clearOAuth(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_OAUTH, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    /**
     * TODO
     * @param context
     * @return
     */
    public static String getAccessToken(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_OAUTH, Context.MODE_PRIVATE);
        String accessToken = sharedPreferences.getString(ACCESS_TOKEN, "");

        return accessToken;
    }
}
