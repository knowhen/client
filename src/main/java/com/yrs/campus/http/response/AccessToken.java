package com.yrs.campus.http.response;

/**
 * @author: when
 * @create: 2019-12-26  16:45
 **/
public class AccessToken {
    private String accessToken;
    private long expiresIn;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }
}
