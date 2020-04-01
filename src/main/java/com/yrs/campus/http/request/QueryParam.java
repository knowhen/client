package com.yrs.campus.http.request;

import java.util.HashSet;
import java.util.Set;

/**
 * @author: when
 * @create: 2020-03-27  10:44
 **/
public class QueryParam {
    private Boolean all;
    private Set<String> group_ids;
    private Set<String> device_ids;

    public QueryParam(Boolean all) {
        this.all = all;
        this.group_ids = new HashSet<>();
        this.device_ids = new HashSet<>();
    }

    public QueryParam(Boolean all, Set<String> group_ids, Set<String> device_ids) {
        this.all = all;
        this.group_ids = group_ids;
        this.device_ids = device_ids;
    }

    public void addGroupId(String groupId) {
        this.group_ids.add(groupId);
    }

    public void addDeviceId(String deviceId) {
        this.device_ids.add(deviceId);
    }

    public Boolean getAll() {
        return all;
    }

    public void setAll(Boolean all) {
        this.all = all;
    }

    public Set<String> getGroup_ids() {
        return group_ids;
    }

    public void setGroup_ids(Set<String> group_ids) {
        this.group_ids = group_ids;
    }

    public Set<String> getDevice_ids() {
        return device_ids;
    }

    public void setDevice_ids(Set<String> device_ids) {
        this.device_ids = device_ids;
    }
}
