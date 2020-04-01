package com.yrs.campus;

import com.google.gson.Gson;
import com.yrs.campus.http.SundrayHttpClient;
import com.yrs.campus.http.response.AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: when
 * @create: 2019-12-26  16:43
 **/
public class SundrayClient {
    private static final Logger logger = LoggerFactory.getLogger(SundrayClient.class);
    private SundrayHttpClient httpClient;
    private AccessToken accessToken = null;
    private LocalDateTime expireTime;
    private Gson gson;

    public SundrayClient(SundrayHttpClient client) {
        this.httpClient = client;
        this.gson = new Gson();
        getAccessToken();
    }

    public String execute(String url, String data) throws IOException {
        keepAccessTokenIsValid();
        return httpClient.execute(url, data, accessToken.getAccessToken());
    }

    private void getAccessToken() {
        logger.info("Try to get access token");

        Map<String, String> request = new HashMap<>();
        request.put("api_token", Constant.API_TOKEN);
        String requestParams = gson.toJson(request);

        try {
            String response = httpClient.execute("/accessToken", requestParams);
            this.accessToken = gson.fromJson(response, AccessToken.class);
            this.expireTime = LocalDateTime.now().plusSeconds(accessToken.getExpiresIn());
        } catch (IOException e) {
            logger.error("Get access token failed.", e);
        }
    }

    private void keepAccessTokenIsValid() {
        if (LocalDateTime.now().isAfter(expireTime)) {
            getAccessToken();
        }
    }

}
