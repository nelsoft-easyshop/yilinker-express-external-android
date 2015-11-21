package com.yilinker.expresspublic.core.enums;

/**
 * Created by Jeico.
 */
public enum AddressType
{
    SENDER {
        @Override
        public String getValue() {
            return "sender";
        }
    },
    RECIPIENT {
        @Override
        public String getValue() {
            return "recipient";
        }
    };

    public abstract String getValue();
}
