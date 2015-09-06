package com.yilinker.expresspublic.modules.common.addAddressLocation;

/**
 * Created by Jeico.
 */
public class AddressGroupModel
{
    private boolean isSelected;
    private String addressGroupName;

    public AddressGroupModel() {
    }

    public AddressGroupModel(boolean isSelected, String addressGroupName) {
        this.isSelected = isSelected;
        this.addressGroupName = addressGroupName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public String getAddressGroupName() {
        return addressGroupName;
    }

    public void setAddressGroupName(String addressGroupName) {
        this.addressGroupName = addressGroupName;
    }
}
