package com.yilinker.expresspublic.core.models;

import com.google.gson.annotations.SerializedName;
import com.yilinker.expresspublic.core.contants.ApiKey;

import java.util.Comparator;
import java.util.Date;

/**
 * Created by Jeico.
 */
public class DeliveryStatus
{
    @SerializedName(ApiKey.MESSAGE)
    private String message;
    @SerializedName(ApiKey.DATE)
    private Date date;
    /***added new key/s*/
    @SerializedName(ApiKey.PACKAGE_STATUS)
    private String packageStatus;

    public DeliveryStatus() {
    }

    public DeliveryStatus(String message, Date date, String packageStatus) {
        this.message = message;
        this.date = date;
        this.packageStatus = packageStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    /** added getter and setter for package status**/
    public String getPackageStatus() {
        return packageStatus;
    }

    public void setPackageStatus(String packageStatus) {
        this.packageStatus = packageStatus;
    }

    /**
     * Sort by date descending
     */
    public static Comparator<DeliveryStatus> sortDateDesc = new Comparator<DeliveryStatus>()
    {
        @Override
        public int compare(DeliveryStatus lhs, DeliveryStatus rhs)
        {
            Date lhsDate = lhs.getDate();
            Date rhsDate = rhs.getDate();

            return rhsDate.compareTo(lhsDate);
        }
    };
}
