package com.yrs.campus.http;

import java.io.IOException;

public interface SundrayHttpClient {

    String execute(String url, String requestParams) throws IOException;

    /**
     * Given request url and data, then execute the request
     * and return response from sundray server
     *
     * @param url  request url without host and access token
     * @param requestParams json string of request
     * @param accessToken access token for sundray request
     * @return response from sundray server
     * @throws IOException
     */
    String execute(String url, String requestParams, String accessToken) throws IOException;
}
