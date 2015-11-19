package com.yilinker.expresspublic.core.contants;

public class ApiEndpoint
{
    /*******************************************************************************
     * Mobile Endpoint
     ******************************************************************************/
    public static final String MOBILE_API                               = "m";
    /*******************************************************************************
     * Home Endpoint
     ******************************************************************************/
    public static final String HOME_API                                 = "home";
    /*******************************************************************************
     * User Endpoint
     ******************************************************************************/
//    public static final String USER_API                                 = "me";
    public static final String USERS_API                                = "m/users";
    public static final String USER_API                                 = "m/consumer";
    // Method
    public static final String USER_PROFILE                             = "profile";
    public static final String USER_UPDATE_PROFILE                      = "update-profile";
    public static final String USER_UPDATE_PASSWORD                     = "update-password";
    public static final String USER_UPDATE_MOBILE                       = "update-contact-number";
    public static final String USER_VERIFY_MOBILE                       = "verify_mobile";
    //temp
    public static final String USER_APP_DEV                             = "http://api.express.api.easydeal.ph/app_dev.php/v1";
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
    // Modified Branch API and added endpoint
    //public static final String BRANCH_API                               = "branch";
    public static final String BRANCH_API                               = "branches";
    public static final String BRANCH_API_GET                           = "get";
    public static final String M_BRANCH                                 = "m";
    // Method
    public static final String BRANCH_BOOKMARK                          = "bookmark";
    public static final String BRANCH_UNBOOKMARK                        = "unbookmark";
    public static final String BRANCH_AREA                              = "area";
    public static final String BRANCH_SEARCH                            = "search";
    public static final String BRANCH_BOOKMARKED                        = "bookmarked";
    public static final String BRANCH_ME                                = "me";
    /*******************************************************************************
     * Track Endpoint
     ******************************************************************************/
    public static final String TRACK_API                                = "track";
    public static final String TRACKING_API                             = "m/tracking";
    // Method
    public static final String TRACK_PACKAGE                            = "package";
    public static final String TRACKING_PACKAGES                        = "packages";
    public static final String TRACK_ONGOING                            = "ongoing";
    public static final String TRACK_DELIVERED                          = "delivered";
    /*******************************************************************************
     * Location Endpoint
     ******************************************************************************/
    public static final String LOCATION_API                             = "location";
    // Method
    public static final String LOCATION_AREA                            = "area";
    public static final String LOCATION_SENDER_ADDRESS                  = "sender_address";
    public static final String LOCATION_RECIPIENT_ADDRESS               = "recipient_address";
    public static final String LOCATION_ADDRESS                         = "address";
    public static final String LOCATION_ADDRESS_GROUP                   = "address_group";
    public static final String LOCATION_GROUP                           = "group";
    public static final String LOCATION_STORE                           = "store";
    public static final String LOCATION_PROVINCE                        = "province";
    public static final String LOCATION_CITY                            = "city";
    public static final String LOCATION_BARANGAY                        = "barangay";
    /*******************************************************************************
     * Delivery Endpoint
     ******************************************************************************/
    public static final String DELIVERY_API                             = "delivery";
    // Path
    public static final String DELIVERY_BOOK                            = "book";
    public static final String DELIVERY_PACKAGE_CONTAINER               = "package_container";
    public static final String DELIVERY_PICKUP_SCHEDULE                 = "pickup_schedule";
    /*******************************************************************************
     * Consumer Endpoint
     ******************************************************************************/
    public static final String CONSUMER_API                             = "m/consumer";
    // Method
    public static final String CONSUMER_ADDRESS_TAG                     = "addressTag";
    public static final String CONSUMER_ADDRESS_GROUP                   = "address-group";
    public static final String CONSUMER_ADD_ADDRESS                     = "add-address";
    /*******************************************************************************
     * Address Endpoint
     ******************************************************************************/
    public static final String ADDRESS_API                             = "m/address";
    // Method
    public static final String ADDRESS_GET                             = "get";
    public static final String ADDRESS_GET_PROVINCES                   = "get-provinces";
    public static final String ADDRESS_GET_CITIES                      = "get-cities";
    public static final String ADDRESS_GET_BARANGAYS                   = "get-barangays";
    /*******************************************************************************
     * Package Endpoint
     ******************************************************************************/
    public static final String PACKAGES_API                             = "m/packages";
    public static final String PACKAGES_ADD                             = "add";
    public static final String PACKAGES_IMAGES                          = "images";

}
