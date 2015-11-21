package com.yilinker.expresspublic.core.enums;

/**
 * Created by Jeico on 8/18/2015.
 */
public enum TokenType
{
    BEARER
            {
                @Override
                public String getValue()
                {
                    return "Bearer";
                }
            };

    public abstract String getValue();
}
