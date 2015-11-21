package com.yilinker.expresspublic.core.models;

import com.google.gson.annotations.SerializedName;
import com.yilinker.expresspublic.core.contants.ApiKey;

/**
 * Created by Jeico.
 */
public class OAuth
{
    @SerializedName(ApiKey.ACCESS_TOKEN)
    private String accessToken;
    @SerializedName(ApiKey.TOKEN_TYPE)
    private String tokenType;
    @SerializedName(ApiKey.EXPIRES_IN)
    private Long expiresIn;
    @SerializedName(ApiKey.REFRESH_TOKEN)
    private String refreshToken;

    public OAuth() {
    }

    public OAuth(String accessToken, String tokenType, Long expiresIn, String refreshToken) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
