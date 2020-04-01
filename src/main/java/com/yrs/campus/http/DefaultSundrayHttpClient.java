package com.yrs.campus.http;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * @author: when
 * @create: 2019-12-26  15:46
 **/
public class DefaultSundrayHttpClient implements SundrayHttpClient {
    private static final Logger logger = LoggerFactory.getLogger(DefaultSundrayHttpClient.class);
    private HttpClientExecutor httpClientGenerator;
    private String host;
    private Gson gson;

    public DefaultSundrayHttpClient(String host, HttpClientExecutor httpClientGenerator) {
        this.host = host;
        this.gson = new Gson();
        this.httpClientGenerator = httpClientGenerator;
    }

    @Override
    public String execute(String url, String requestParams) throws IOException {
        return execute(url, requestParams, null);
    }

    @Override
    public String execute(String url, String requestParams, String accessToken) throws IOException {
        String requestUrl = getRequestUrl(url, accessToken);

        Map<String, Object> response = httpClientGenerator.doPost(requestUrl, requestParams);
        return parseResponse(response);
    }

    private String getRequestUrl(String url, String accessToken) {
        StringBuffer buffer = new StringBuffer(host);
        buffer.append(url);
        if (accessToken != null && !accessToken.isEmpty()) {
            buffer.append("?access_token=");
            buffer.append(accessToken);
        }
        return buffer.toString();
    }

    private String parseResponse(Map<String, Object> response) {
        if ((int) response.get("code") != 200) {
            String json = gson.toJson(response);
            logger.error(json);
        }
        String body = (String) response.get("body");
        logger.info("Response body:" + body);
        return body;
    }
}
