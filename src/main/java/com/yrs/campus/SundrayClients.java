package com.yrs.campus;

import com.yrs.campus.http.DefaultSundrayHttpClient;
import com.yrs.campus.http.HttpClientExecutor;
import com.yrs.campus.http.SundrayHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: when
 * @create: 2019-12-26  17:00
 **/
public class SundrayClients {
    private static final Logger logger = LoggerFactory.getLogger(SundrayClients.class);

    private SundrayClients() {
    }

    public static SundrayClient create(String host) {
        logger.info("Create sundray client at host:{}", Constant.SUNDRAY_HTTPS);
        SundrayHttpClient httpClient = new DefaultSundrayHttpClient(host, new HttpClientExecutor());

        return new SundrayClient(httpClient);
    }

    public static SundrayClient createDefault() {
        logger.info("Create default sundray client at host:{}", Constant.SUNDRAY_HTTPS);
        return create(Constant.SUNDRAY_HTTPS);
    }

}
