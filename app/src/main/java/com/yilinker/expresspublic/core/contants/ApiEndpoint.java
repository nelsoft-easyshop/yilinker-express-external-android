package com.yilinker.expresspublic.core.contants;

public class ApiEndpoint
{
    /*******************************************************************************
     * User Endpoint
     ******************************************************************************/
    public static final String USER_API                                 = "me";
    // Method
    public static final String USER_UPDATE_PROFILE                      = "update_profile";
    public static final String USER_UPDATE_PASSWORD                     = "update_password";
    public static final String USER_UPDATE_MOBILE                       = "update_mobile";
    /*******************************************************************************
     * OAuth Endpoint
     ******************************************************************************/
    public static final String OAUTH_API                                = "oauth";
    // Method
    public static final String OAUTH_TOKEN                              = "token";
    public static final String OAUTH_REGISTER                           = "register";
    /*******************************************************************************
     * Branch Endpoint
     ******************************************************************************/
    public static final String BRANCH_API                               = "branch";
    // Method
    public static final String BRANCH_BOOKMARK                          = "follow";
    public static final String BRANCH_UNBOOKMARK                        = "unfollow";
    public static final String BRANCH_AREA                              = "area";
    public static final String BRANCH_SEARCH                            = "search";
    /*******************************************************************************
     * Track Endpoint
     ******************************************************************************/
    public static final String TRACK_API                                = "track";
    // Method
    public static final String TRACK_ONGOING                            = "ongoing";
    public static final String TRACK_DELIVERED                          = "delivered";
    /*******************************************************************************
     * Location Endpoint
     ******************************************************************************/
    public static final String LOCATION_API                             = "location";
    // Method
    public static final String LOCATION_AREA                            = "area";
    /*******************************************************************************
     * Delivery Endpoint
     ******************************************************************************/
    public static final String DELIVERY_API                             = "delivery";
    // Path
    public static final String DELIVERY_BOOK                            = "book";
    public static final String DELIVERY_PACKAGE_CONTAINER               = "package_container";
    public static final String DELIVERY_PICKUP_SCHEDULE                 = "pickup_schedule";
}
