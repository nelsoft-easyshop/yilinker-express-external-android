package com.yilinker.expresspublic.core.models;

import com.google.gson.annotations.SerializedName;
import com.yilinker.expresspublic.core.contants.ApiKey;
import com.yilinker.expresspublic.core.utilities.DateUtils;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by Jeico.
 */
public class DeliveryPackage
{
    @SerializedName(ApiKey.ID)
    private Long id;
    @SerializedName(ApiKey.TRACKING_NUMBER)
    private String trackingNumber;
    @SerializedName(ApiKey.FROM_TO)
    private String originToDestination;
    @SerializedName(ApiKey.SHIPPING_TYPE)
    private String shippingType;
    @SerializedName(ApiKey.STATUS)
    private String status;
    @SerializedName(ApiKey.STATUS_REMARKS)
    private String statusRemarks;
    @SerializedName(ApiKey.RIDER_VEHICLE)
    private String riderDetails;
    @SerializedName(ApiKey.PICKUP_DATE)
    private Date deliveryDate;
    @SerializedName(ApiKey.POUCH_TYPE)
    private String packageContainer;
    @SerializedName(ApiKey.ETA)
    private String eta;
    @SerializedName(ApiKey.DELIVERY_STATUS)
    private List<DeliveryStatus> deliveryStatusList;

    public DeliveryPackage() {
    }

    public DeliveryPackage(Long id, String trackingNumber, String originToDestination, String shippingType, String status, String statusRemarks, String riderDetails, Date deliveryDate, String packageContainer, String eta, List<DeliveryStatus> deliveryStatusList) {
        this.id = id;
        this.trackingNumber = trackingNumber;
        this.originToDestination = originToDestination;
        this.shippingType = shippingType;
        this.status = status;
        this.statusRemarks = statusRemarks;
        this.riderDetails = riderDetails;
        this.deliveryDate = deliveryDate;
        this.packageContainer = packageContainer;
        this.eta = eta;
        this.deliveryStatusList = deliveryStatusList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getOriginToDestination() {
        return originToDestination;
    }

    public void setOriginToDestination(String originToDestination) {
        this.originToDestination = originToDestination;
    }

    public String getShippingType() {
        return shippingType;
    }

    public void setShippingType(String shippingType) {
        this.shippingType = shippingType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusRemarks() {
        return statusRemarks;
    }

    public void setStatusRemarks(String statusRemarks) {
        this.statusRemarks = statusRemarks;
    }

    public String getRiderDetails() {
        return riderDetails;
    }

    public void setRiderDetails(String riderDetails) {
        this.riderDetails = riderDetails;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getPackageContainer() {
        return packageContainer;
    }

    public void setPackageContainer(String packageContainer) {
        this.packageContainer = packageContainer;
    }

    public String getEta() {
        return eta;
    }

    public void setEta(String eta) {
        this.eta = eta;
    }

    public List<DeliveryStatus> getDeliveryStatusList() {
        return deliveryStatusList;
    }

    public void setDeliveryStatusList(List<DeliveryStatus> deliveryStatusList) {
        this.deliveryStatusList = deliveryStatusList;
    }

    /**
     * Sort by date descending
     */
    public static Comparator<DeliveryPackage> sortDateDesc = new Comparator<DeliveryPackage>()
    {
        @Override
        public int compare(DeliveryPackage lhs, DeliveryPackage rhs)
        {
            Date lhsDate = DateUtils.parseDate(lhs.getDeliveryStatusList().get(0).getDate());
            Date rhsDate = DateUtils.parseDate(rhs.getDeliveryStatusList().get(0).getDate());

            if (lhsDate == null || rhs == null) {
                return -1;
            }

            return rhsDate.compareTo(lhsDate);
        }
    };

}
