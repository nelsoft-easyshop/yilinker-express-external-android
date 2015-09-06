package com.yilinker.expresspublic.core.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.yilinker.expresspublic.core.models.User;

import java.util.logging.Logger;

public class UserPrefHelper
{
    private static final Logger logger = Logger.getLogger(UserPrefHelper.class.getSimpleName());

    private static final String PREF_USER                               = "pref_user";
    private static final String ID                                      = "id";
    private static final String FIRST_NAME                              = "first_name";
    private static final String LAST_NAME                               = "last_name";
    private static final String FULL_NAME                               = "full_name";
    private static final String USERNAME                                = "username";
    private static final String EMAIL                                   = "email";
    private static final String CONTACT_NUMBER                          = "contact_number";

    /**
     * TODO
     * @param context
     * @return
     */
    public static User getUser(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_USER, Context.MODE_PRIVATE);
        Long id = sharedPreferences.getLong(ID, 0);
        String firstname = sharedPreferences.getString(FIRST_NAME, "");
        String lastname = sharedPreferences.getString(LAST_NAME, "");
        String fullname = sharedPreferences.getString(FULL_NAME, "");
        String username = sharedPreferences.getString(USERNAME, "");
        String emailAddress = sharedPreferences.getString(EMAIL, "");
        String contactNumber = sharedPreferences.getString(CONTACT_NUMBER, "");

        return new User(id, username, firstname, lastname, fullname, emailAddress, contactNumber);
    }

    /**
     * TODO
     * @param context
     * @param user
     */
    public static void setUser(Context context, User user)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(ID, user.getId());
        editor.putString(FIRST_NAME, user.getFirstname());
        editor.putString(LAST_NAME, user.getLastname());
        editor.putString(FULL_NAME, user.getFullname());
        editor.putString(USERNAME, user.getUsername());
        editor.putString(EMAIL, user.getEmail());
        editor.putString(CONTACT_NUMBER, user.getContactNumber());
        editor.apply();
    }

    /**
     * TODO
     * @param context
     */
    public static void clearUser(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
