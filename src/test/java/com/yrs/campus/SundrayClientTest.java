package com.yrs.campus;

import com.google.gson.Gson;
import com.yrs.campus.http.request.Pagination;
import com.yrs.campus.http.request.Param;
import com.yrs.campus.http.request.QueryParam;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * {"api_token":"098f6bcd4621d373cade4e832627b4f6"}
 * access token {"accessToken" : "5c860a0a6a23f72b516877f679758cba", "expiresIn" : 3890}
 */
public class SundrayClientTest {
    private SundrayClient client;

    @Before
    public void setUp() {
        client = SundrayClients.create("http://192.168.31.77:8088/api/v1/");
    }

    @Test
    public void testGetAllDevType() throws IOException {
        Pagination pagination = new Pagination(1, 100);

        String data = toJson(pagination);

        execute(data, "/device/getAllDevType");
    }

    @Test
    public void testGetOneWaySwitchStatus() throws IOException {
        QueryParam param = new QueryParam(Boolean.TRUE);
        Map<String, QueryParam> queryParam = new HashMap<>();
        queryParam.put("OneWayLoraSwitch", param);

        String data = toJson(queryParam);

        execute(data, "/device/getStatus");
    }

    @Test
    public void testGetTempHumidStatus() throws IOException {
        QueryParam param = new QueryParam(Boolean.TRUE);
        Map<String, QueryParam> queryParam = new HashMap<>();
        queryParam.put("LoRaTempHumid", param);

        String data = toJson(queryParam);

        execute(data, "/device/getStatus");
    }

    @Test
    public void testSetDevStatus() throws IOException {
        Param param = new Param();
        param.addDeviceId("GOK9320002");
        param.putData("DEV_SWITCH_STA_1", "1");
        Map<String, Object> params = new HashMap<>();
        params.put("TwoWayLoraSwitch", param);

        String data = toJson(params);

        execute(data, "/device/setStatus");
    }

    private String toJson(Object data) {
        String json = new Gson().toJson(data);
        print(json);
        return json;
    }

    private void print(String data) {
        System.out.println("Json query param:\n" + data);
    }

    private void execute(String data, String url) throws IOException {
        String response = client.execute(url, data);
        System.out.println(response);
    }


}