package com.yrs.campus.http.request;

/**
 * @author: when
 * @create: 2020-03-27  10:39
 **/
public class Pagination {
    private Integer current;
    private Integer rowCount;

    public Pagination() {
    }

    public Pagination(Integer current, Integer rowCount) {
        this.current = current;
        this.rowCount = rowCount;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getRowCount() {
        return rowCount;
    }

    public void setRowCount(Integer rowCount) {
        this.rowCount = rowCount;
    }
}
