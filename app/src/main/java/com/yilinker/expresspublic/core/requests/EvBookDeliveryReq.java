package com.yilinker.expresspublic.core.requests;

import java.util.Date;

/**
 * Created by Jeico.
 */
public class EvBookDeliveryReq
{
    private Long senderConsumerId;
    private Long recipientConsumerId;
    private Long senderAddressId;
    private Long recipientAddressId;
    private Long senderConsumerAddressId;
    private Long recipientConsumerAddressId;
    private Long packagePickupScheduleId;
    private Long branchId;
    private String packageName;
    private Integer quantity;
    private String fragile;
    private String paidBy;
    private Date pickUpDate;
    private String length;
    private String height;
    private String width;
    private String weight;

    public EvBookDeliveryReq() {
    }

    public Long getSenderConsumerId() {
        return senderConsumerId;
    }

    public void setSenderConsumerId(Long senderConsumerId) {
        this.senderConsumerId = senderConsumerId;
    }

    public Long getRecipientConsumerId() {
        return recipientConsumerId;
    }

    public void setRecipientConsumerId(Long recipientConsumerId) {
        this.recipientConsumerId = recipientConsumerId;
    }

    public Long getSenderAddressId() {
        return senderAddressId;
    }

    public void setSenderAddressId(Long senderAddressId) {
        this.senderAddressId = senderAddressId;
    }

    public Long getRecipientAddressId() {
        return recipientAddressId;
    }

    public void setRecipientAddressId(Long recipientAddressId) {
        this.recipientAddressId = recipientAddressId;
    }

    public Long getSenderConsumerAddressId() {
        return senderConsumerAddressId;
    }

    public void setSenderConsumerAddressId(Long senderConsumerAddressId) {
        this.senderConsumerAddressId = senderConsumerAddressId;
    }

    public Long getRecipientConsumerAddressId() {
        return recipientConsumerAddressId;
    }

    public void setRecipientConsumerAddressId(Long recipientConsumerAddressId) {
        this.recipientConsumerAddressId = recipientConsumerAddressId;
    }

    public Long getPackagePickupScheduleId() {
        return packagePickupScheduleId;
    }

    public void setPackagePickupScheduleId(Long packagePickupScheduleId) {
        this.packagePickupScheduleId = packagePickupScheduleId;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getFragile() {
        return fragile;
    }

    public void setFragile(String fragile) {
        this.fragile = fragile;
    }

    public String getPaidBy() {
        return paidBy;
    }

    public void setPaidBy(String paidBy) {
        this.paidBy = paidBy;
    }

    public Date getPickUpDate() {
        return pickUpDate;
    }

    public void setPickUpDate(Date pickUpDate) {
        this.pickUpDate = pickUpDate;
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
}
