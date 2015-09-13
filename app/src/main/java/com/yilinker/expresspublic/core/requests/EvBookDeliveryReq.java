package com.yilinker.expresspublic.core.requests;

import java.util.Date;
import java.util.List;

/**
 * Created by Jeico.
 */
public class EvBookDeliveryReq
{
    // Sender Details
    private Long senderConsumerId;
    private Long senderAddressId;
    private Long senderConsumerAddressId;

    // Recipient Details
    private Long recipientConsumerId;
    private Long recipientAddressId;
    private Long recipientConsumerAddressId;

    // Package Details
    private String packageName;
    private String sku;
    private Integer quantity;
    private List<String> images;
    private Boolean fragile;
    private String paidBy;

    // Dimension and Weight
    private String length;
    private String height;
    private String width;
    private String weight;

    // Pickup Schedule
    private Date pickUpDate;
    private Long packagePickupScheduleId;


    public EvBookDeliveryReq() {
    }

    public Long getSenderConsumerId() {
        return senderConsumerId;
    }

    public void setSenderConsumerId(Long senderConsumerId) {
        this.senderConsumerId = senderConsumerId;
    }

    public Long getSenderAddressId() {
        return senderAddressId;
    }

    public void setSenderAddressId(Long senderAddressId) {
        this.senderAddressId = senderAddressId;
    }

    public Long getSenderConsumerAddressId() {
        return senderConsumerAddressId;
    }

    public void setSenderConsumerAddressId(Long senderConsumerAddressId) {
        this.senderConsumerAddressId = senderConsumerAddressId;
    }

    public Long getRecipientConsumerId() {
        return recipientConsumerId;
    }

    public void setRecipientConsumerId(Long recipientConsumerId) {
        this.recipientConsumerId = recipientConsumerId;
    }

    public Long getRecipientAddressId() {
        return recipientAddressId;
    }

    public void setRecipientAddressId(Long recipientAddressId) {
        this.recipientAddressId = recipientAddressId;
    }

    public Long getRecipientConsumerAddressId() {
        return recipientConsumerAddressId;
    }

    public void setRecipientConsumerAddressId(Long recipientConsumerAddressId) {
        this.recipientConsumerAddressId = recipientConsumerAddressId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public Boolean getFragile() {
        return fragile;
    }

    public void setFragile(Boolean fragile) {
        this.fragile = fragile;
    }

    public String getPaidBy() {
        return paidBy;
    }

    public void setPaidBy(String paidBy) {
        this.paidBy = paidBy;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public Date getPickUpDate() {
        return pickUpDate;
    }

    public void setPickUpDate(Date pickUpDate) {
        this.pickUpDate = pickUpDate;
    }

    public Long getPackagePickupScheduleId() {
        return packagePickupScheduleId;
    }

    public void setPackagePickupScheduleId(Long packagePickupScheduleId) {
        this.packagePickupScheduleId = packagePickupScheduleId;
    }
}
