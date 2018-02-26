package com.smn.httppush.demo.common.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

public class HttpUtil {

    private static final int DEFAULT_CONNECT_TIMEOUT = 60000;
    private static final int DEFAULT_SOCKET_TIMEOUT = 60000;

    public static HttpResponse get(String url, Map<String, String> headerParams) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
        CloseableHttpClient httpclient = getHttpClient();
        HttpRequestBase httpRequestBase = new HttpGet(url);
        httpRequestBase.setConfig(getRequestConfig());
        buildHttpHeader(headerParams, httpRequestBase);
        return httpclient.execute(httpRequestBase);
    }

    private static void buildHttpHeader(Map<String, String> requestHeaderMap, HttpRequestBase httpRequestBase) {
        for (String headerKey : requestHeaderMap.keySet()) {
            httpRequestBase.addHeader(headerKey, requestHeaderMap.get(headerKey));
        }
    }

    private static CloseableHttpClient getHttpClient() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        return HttpClients.custom().setSSLSocketFactory(buildSslFactoryIgnoreCertificate()).build();
    }

    private static RequestConfig getRequestConfig() {

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(DEFAULT_CONNECT_TIMEOUT)
                .setSocketTimeout(DEFAULT_SOCKET_TIMEOUT)
                .build();
        return requestConfig;
    }

    private static SSLConnectionSocketFactory buildSslFactoryIgnoreCertificate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {

        SSLContext sslContext = new SSLContextBuilder()
                .loadTrustMaterial(null, new TrustSelfSignedStrategy())
                .build();

        X509TrustManager tm = new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }

            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        sslContext.init(null, new TrustManager[]{tm}, null);

        return new SSLConnectionSocketFactory(sslContext, new String[]{"TLSv1.1", "TLSv1.2"}, null, new NoopHostnameVerifier());
    }
}
