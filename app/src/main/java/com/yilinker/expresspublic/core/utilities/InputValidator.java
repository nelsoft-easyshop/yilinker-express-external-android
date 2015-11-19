package com.yilinker.expresspublic.core.utilities;

import android.content.Context;

import java.util.logging.Logger;

/**
 * Created by Jeico on 4/26/2015.
 */
public class InputValidator
{
    private static final Logger logger = Logger.getLogger(InputValidator.class.getSimpleName());

    public static String isFirstnameValid(String firstname)
    {
        String errorMessage = null;

        if (firstname.trim().isEmpty())
        {
            errorMessage = "Firstname should not be empty.";
        }

        return errorMessage;
    }

    public static String isLastnameValid(String lastname)
    {
        String errorMessage = null;

        if (lastname.trim().isEmpty())
        {
            errorMessage = "Lastname should not be empty.";
        }

        return errorMessage;
    }

    public static String isEmailValid(String email)
    {
        String errorMessage = null;

        boolean isEmailInPattern = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();

        if(email.trim().isEmpty())
        {
            errorMessage = "Please enter your email address.";
        }
        else if(!isEmailInPattern)
        {
            errorMessage = "Please enter a valid email address.";
        }

        return errorMessage;
    }

    public static String isContactNumberValid(String contactNumber)
    {
        String errorMessage = null;

        if (contactNumber.trim().isEmpty())
        {
            errorMessage = "Contact number should not be empty.";
        }
        else if (contactNumber.trim().length() != 11)
        {
            errorMessage = "Contact number must be 11 digits.";
        }

        return errorMessage;
    }

    public static String isPasswordValid(String password)
    {
        String errorMessage = null;

        if (password.trim().isEmpty())
        {
            errorMessage = "Password should not be empty.";
        }
        else if (password.trim().length() < 8) {
            errorMessage = "Password must be at least 8 characters.";
        }

        return errorMessage;
    }

    public static String isOldPasswordValid(String oldPassword)
    {
        String errorMessage = null;

        if (oldPassword.trim().isEmpty())
        {
            errorMessage = "Old password should not be empty.";
        }

        return errorMessage;
    }

    public static String isNewPasswordValid(String newPassword)
    {
        String errorMessage = null;

        if (newPassword.trim().isEmpty())
        {
            errorMessage = "New password should not be empty.";
        }

        return errorMessage;
    }

    public static String isPasswordMatch(String password, String confirmPassword)
    {
        String errorMessage = null;

        if (!password.equals(confirmPassword))
        {
            errorMessage = "Password does not match.";
        }

        return errorMessage;
    }

    public static String isPasswordAtLeastEight(String password, String confirmPassword)
    {
        String errorMessage = null;

        if (password.length() < 8 || confirmPassword.length() < 8)
        {
            errorMessage = "Password should be at least 8 characters.";
        }

        return errorMessage;
    }

    public static String isNewContactNumberValid(String contactNumber)
    {
        String errorMessage = null;

        if (contactNumber.trim().isEmpty())
        {
            errorMessage = "New mobile number should not be empty.";
        }

        return errorMessage;
    }

    public static String isFullnameValid(String fullname)
    {
        String errorMessage = null;

        if (fullname.trim().isEmpty())
        {
            errorMessage = "Full name should not be empty.";
        }

        return errorMessage;
    }

    public static String isUnitNumberValid(String unitNumber)
    {
        String errorMessage = null;

        if (unitNumber.trim().isEmpty())
        {
            errorMessage = "Unit number should not be empty.";
        }

        return errorMessage;
    }

    public static String isBuildingNameValid(String buildingName)
    {
        String errorMessage = null;

        if (buildingName.trim().isEmpty())
        {
            errorMessage = "Building name should not be empty.";
        }

        return errorMessage;
    }

    public static String isStreetNumberValid(String streetNumber)
    {
        String errorMessage = null;

        if (streetNumber.trim().isEmpty())
        {
            errorMessage = "Street number should not be empty.";
        }

        return errorMessage;
    }

    public static String isSubdivisionValid(String subdivision)
    {
        String errorMessage = null;

        if (subdivision.trim().isEmpty())
        {
            errorMessage = "Subdivision should not be empty.";
        }

        return errorMessage;
    }

    public static String isBarangayValid(String barangay)
    {
        String errorMessage = null;

        if (barangay.trim().isEmpty())
        {
            errorMessage = "Barangay should not be empty.";
        }

        return errorMessage;
    }

    public static String isCityValid(String city)
    {
        String errorMessage = null;

        if (city.trim().isEmpty())
        {
            errorMessage = "City should not be empty.";
        }

        return errorMessage;
    }

    public static String isProvinceValid(String province)
    {
        String errorMessage = null;

        if (province.trim().isEmpty())
        {
            errorMessage = "ProvinceWithCity should not be empty.";
        }

        return errorMessage;
    }

    public static String isZipCodeValid(String zipCode)
    {
        String errorMessage = null;

        if (zipCode.trim().isEmpty())
        {
            errorMessage = "Zip code should not be empty.";
        }

        return errorMessage;
    }

    public static String isLengthValid(String length) {
        String errorMessage = null;

        if (length.trim().isEmpty())
        {
            errorMessage = "Length should not be empty.";
        }

        return errorMessage;
    }

    public static String isHeightValid(String height) {
        String errorMessage = null;

        if (height.trim().isEmpty())
        {
            errorMessage = "Height should not be empty.";
        }

        return errorMessage;
    }

    public static String isWidthValid(String width) {
        String errorMessage = null;

        if (width.trim().isEmpty())
        {
            errorMessage = "Width should not be empty.";
        }

        return errorMessage;
    }

    public static String isWeightValid(String weight) {
        String errorMessage = null;

        if (weight.trim().isEmpty())
        {
            errorMessage = "Weight should not be empty.";
        }

        return errorMessage;
    }

    public static String isPackageNameValid(String packageName) {

        String errorMessage = null;

        if (packageName.trim().isEmpty())
        {
            errorMessage = "Package Details should not be empty";
        }

        return errorMessage;
    }

    public static String isQuantityValid(String quantity) {

        String errorMessage = null;

        if (quantity.trim().isEmpty())
        {
            errorMessage = "Quantity should not be empty";
        }

        return errorMessage;
    }

    public static String isDeclaredValueValid(String declaredValue) {

        String errorMessage = null;

        if (declaredValue.trim().isEmpty())
        {
            errorMessage = "Declared Value should not be empty";
        }

        return errorMessage;
    }
}
