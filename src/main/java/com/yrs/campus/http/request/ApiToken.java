package com.yrs.campus.http.request;

import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Variant;
import java.util.Date;
import java.util.List;

/**
 * @author: when
 * @create: 2020-03-26  16:43
 **/
public class ApiToken implements Request {
    @Override
    public String getMethod() {
        return null;
    }

    @Override
    public Variant selectVariant(List<Variant> list) {
        return null;
    }

    @Override
    public Response.ResponseBuilder evaluatePreconditions(EntityTag entityTag) {
        return null;
    }

    @Override
    public Response.ResponseBuilder evaluatePreconditions(Date date) {
        return null;
    }

    @Override
    public Response.ResponseBuilder evaluatePreconditions(Date date, EntityTag entityTag) {
        return null;
    }

    @Override
    public Response.ResponseBuilder evaluatePreconditions() {
        return null;
    }
}
