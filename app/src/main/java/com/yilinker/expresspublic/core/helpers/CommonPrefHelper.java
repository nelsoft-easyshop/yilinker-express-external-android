package com.yilinker.expresspublic.core.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.logging.Logger;

/**
 * Created by Jeico.
 */
public class CommonPrefHelper
{
    private static final Logger logger = Logger.getLogger(CommonPrefHelper.class.getSimpleName());

    private static final String PREF_COMMON                             = "pref_common";
    private static final String LOGIN_STATUS                            = "login_status";

    /**
     * TODO
     * @param context
     */
    public static void clearCommon(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_COMMON, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    /**
     * TODO
     * @param context
     * @return
     */
    public static boolean isUserLoggedIn(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_COMMON, Context.MODE_PRIVATE);
        boolean isLogin = sharedPreferences.getBoolean(LOGIN_STATUS, false);

        return isLogin;
    }

    /**
     * TODO
     * @param context
     * @param isLogin
     */
    public static void setLoginStatus(Context context, boolean isLogin)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_COMMON, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(LOGIN_STATUS, isLogin);
        editor.apply();
    }
}
