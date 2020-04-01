package com.yrs.campus.http;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DefaultSundrayHttpClientTest {
    private SundrayHttpClient httpClient;
    private HttpClientExecutor generator;

    @Before
    public void setUp() {
        generator = mock(HttpClientExecutor.class);
        httpClient = new DefaultSundrayHttpClient("https://localhost/api/v1", generator);
    }

    @Test
    public void testExecuteWithoutAccessToken() throws IOException {
        String requestUrl = "https://localhost/api/v1/accessToken";
        String requestData = "{\"api_token\":\"098f6bcd4621d373cade4e832627b4f6\"}";
        String response = "{\"accessToken\" : \"a6e5d7c4c372299fcd4a0c444c6c55de\", \"expiresIn\" : 7200}";
        mockResult(requestUrl, requestData, response);

        String responseBody = httpClient.execute("/accessToken", requestData);

        assertEquals(responseBody, response);
    }

    @Test
    public void testExecuteWithAccessToken() throws IOException {
        String requestUrl = "https://localhost/api/v1/getAppList?access_token=a6e5d7c4c372299fcd4a0c444c6c55de";
        String requestData = "{\"current\":1,\"rowCount\":20}";
        String response = "{\"data\":{\"current\":1,\"rowCount\":20,\"total\":1,\"apps\":[{\"appID\":2," +
                "\"appName\":\"郑州师范学院附属小学\"}]},\"success\":true}";
        String accessToken = "a6e5d7c4c372299fcd4a0c444c6c55de";
        mockResult(requestUrl, requestData, response);


        String responseBody = httpClient.execute("/getAppList", requestData, accessToken);

        assertEquals(responseBody, response);
    }

    private void mockResult(String url, String data, String response) throws IOException {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("body", response);
        when(generator.doPost(url, data))
                .thenReturn(result);
    }
}