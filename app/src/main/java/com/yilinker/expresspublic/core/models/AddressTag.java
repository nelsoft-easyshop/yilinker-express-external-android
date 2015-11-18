package com.yilinker.expresspublic.core.models;

/**
 * Created by JBautista on 11/18/15.
 */
public class AddressTag {

    private String value;
    private String name;

    public AddressTag() {
    }

    public AddressTag(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(Long id) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
