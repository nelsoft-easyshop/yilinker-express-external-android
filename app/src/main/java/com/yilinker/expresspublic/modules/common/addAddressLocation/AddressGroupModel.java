package com.yilinker.expresspublic.modules.common.addAddressLocation;

/**
 * Created by Jeico.
 */
public class AddressGroupModel
{
    private boolean isSelected;
    private Long id;
    private String name;

    public AddressGroupModel() {
    }

    public AddressGroupModel(boolean isSelected, Long id, String name) {
        this.isSelected = isSelected;
        this.id = id;
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
