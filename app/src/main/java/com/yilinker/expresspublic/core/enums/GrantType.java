package com.yilinker.expresspublic.core.enums;

/**
 * Created by Jeico on 8/18/2015.
 */
public enum GrantType
{
    PASSWORD
            {
                @Override
                public String getValue()
                {
                    return "password";
                }
            },
    REFRESH_TOKEN
            {
                @Override
                public String getValue()
                {
                    return "refresh_token";
                }
            };

    public abstract String getValue();
}
