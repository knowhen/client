package com.yrs.campus.http.request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: when
 * @create: 2020-04-01  09:14
 **/
public class Param {
    private List<String> device_ids = new ArrayList<>();
    private Map<String, String> data = new HashMap<>();

    public void addDeviceId(String deviceId) {
        if (!device_ids.contains(deviceId)) {
            device_ids.add(deviceId);
        }
    }

    public void putData(String key, String value) {
        data.put(key, value);
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    public List<String> getDevice_ids() {
        return device_ids;
    }

    public void setDevice_ids(List<String> device_ids) {
        this.device_ids = device_ids;
    }
}
