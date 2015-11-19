package com.yilinker.expresspublic.core.enums;

/**
 * Created by Jeico.
 */
public enum ShipmentType
{
    ONGOING {
        @Override
        public String getValue() {
//            return "Ongoing";
            return "On-going";
        }
    },
    DELIVERED {
        @Override
        public String getValue() {
            return "Delivered";
        }
    };

    public abstract String getValue();
}
