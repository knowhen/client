package com.yrs.campus.http;

import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.Closeable;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author: when
 * @create: 2019-12-26  16:10
 **/
public class HttpClientExecutor {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientExecutor.class);
    private static final String CHARSET = "UTF-8";
    public static final String HTTPS = "https";


    public Map<String, Object> doGet(String url) throws Exception {
        return doGet(url, null, null);
    }

    public Map<String, Object> doGet(String url, Map<String, String> params) throws Exception {
        return doGet(url, null, params);
    }

    public Map<String, Object> doGet(String url, Map<String, String> headers, Map<String, String> params) throws Exception {
        checkUrl(url);

        CloseableHttpClient httpClient = createHttpClient(url);

        URIBuilder builder = createUriBuilder(url, params);

        HttpGet httpGet = new HttpGet(builder.build());

        wrapHeaders(headers, httpGet);

        return handleHttpResponse(httpClient, execute(httpClient, httpGet));
    }

    public Map<String, Object> doPost(String url, String json) throws IOException {
        return doPost(url, null, json);
    }

    public Map<String, Object> doPost(String url, Map<String, String> headers, String json) throws IOException {
        checkUrl(url);

        CloseableHttpClient httpClient = createHttpClient(url);

        HttpPost httpPost = new HttpPost(url);

        wrapHeaders(headers, httpPost);

        wrapParams(json, httpPost);

        CloseableHttpResponse httpResponse = execute(httpClient, httpPost);

        return handleHttpResponse(httpClient, httpResponse);
    }

    private void wrapParams(String json, HttpPost httpPost) {
        StringEntity paramEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
        httpPost.setEntity(paramEntity);
    }

    private Map<String, Object> handleHttpResponse(CloseableHttpClient httpClient,
                                                   CloseableHttpResponse httpResponse) throws IOException {
        try {
            return parseHttpResponse(httpResponse);
        } finally {
            releaseResource(httpResponse);
            releaseResource(httpClient);
        }
    }

    private void wrapHeaders(Map<String, String> headers, HttpRequestBase httpRequest) {
        if (checkParams(headers)) {
            parseHeader(headers, httpRequest);
        }
    }

    private CloseableHttpResponse execute(CloseableHttpClient httpClient, HttpRequestBase httpMethod) throws IOException {
        return httpClient.execute(httpMethod);
    }

    private URIBuilder createUriBuilder(String url, Map<String, String> params) throws URISyntaxException {
        URIBuilder builder = new URIBuilder(url);
        if (checkParams(params)) {
            parseParams(params, builder);
        }
        return builder;
    }

    private CloseableHttpClient createHttpClient(String url) {
        CloseableHttpClient httpClient;
        if (url.startsWith(HTTPS)) {
            httpClient = createInsecureSSLClient();
        } else {
            httpClient = HttpClients.createDefault();
        }
        return httpClient;
    }

    private void checkUrl(String url) {
        if (null == url || url.isEmpty()) {
            throw new IllegalArgumentException("Please check request url");
        }
    }

    private boolean checkParams(Map<String, String> headers) {
        return headers != null && headers.size() > 0;
    }

    private void parseParams(Map<String, String> params, URIBuilder builder) {
        Set<Map.Entry<String, String>> entries = params.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            builder.addParameter(entry.getKey(), entry.getValue());
        }
    }

    private void parseHeader(Map<String, String> params, HttpRequestBase httpMethod) {
        Set<Map.Entry<String, String>> entries = params.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            httpMethod.setHeader(entry.getKey(), entry.getValue());
        }
    }

    private SSLConnectionSocketFactory buildSSLConnectionSocketFactory() {
        try {
            return new SSLConnectionSocketFactory(createIgnoreVerifySSL(),
                    SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        } catch (KeyManagementException e) {
            logger.error("ssl connection fail", e);
        } catch (NoSuchAlgorithmException e) {
            logger.error("ssl connection fail", e);
        }
        return SSLConnectionSocketFactory.getSocketFactory();
    }

    private SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
        X509TrustManager trustManager = new X509TrustManager() {

            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

        };

//        SSLContext sc = SSLContext.getInstance("SSLv3");
        SSLContext sc = SSLContext.getInstance("TLS");
        sc.init(null, new TrustManager[]{trustManager}, null);
        return sc;
    }

    public CloseableHttpClient createInsecureSSLClient() {
        SSLConnectionSocketFactory factory = buildSSLConnectionSocketFactory();
        return HttpClients.custom().setSSLSocketFactory(factory).build();
    }

//    public static CloseableHttpClient createInsecureSSLClient() {
//        try {
//            SSLContext sslContext = SSLContexts.custom()
//                    .loadTrustMaterial(null, (chain, authType) -> true)
//                    .build();
//
//            SSLConnectionSocketFactory sslCsf = new SSLConnectionSocketFactory(sslContext);
//
//            return HttpClients.custom().setSSLSocketFactory(sslCsf).build();
//        } catch (KeyManagementException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (KeyStoreException e) {
//            e.printStackTrace();
//        }
//
//        return HttpClients.createDefault();
//    }

    private Map<String, Object> parseHttpResponse(CloseableHttpResponse httpResponse) throws IOException {
        Map<String, Object> result = new HashMap<>();
        if (httpResponse != null) {
            StatusLine status = httpResponse.getStatusLine();
            result.put("code", status.getStatusCode());
            result.put("msg", status.getReasonPhrase());
            if (httpResponse.getEntity() != null) {
                String body = EntityUtils.toString(httpResponse.getEntity(), CHARSET);
                result.put("body", body);
            }
        }
        return result;
    }

    private void releaseResource(Closeable closeable) throws IOException {
        if (closeable != null) {
            closeable.close();
        }
    }
}
