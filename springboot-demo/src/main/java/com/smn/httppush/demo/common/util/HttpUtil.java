/*
 * Copyright (C) 2018. Huawei Technologies Co., LTD. All rights reserved.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of Apache License, Version 2.0.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * Apache License, Version 2.0 for more details.
 */
package com.smn.httppush.demo.common.util;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
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

/**
 * http 工具类
 */
public class HttpUtil {

    private static final int DEFAULT_CONNECT_TIMEOUT = 60000;
    private static final int DEFAULT_SOCKET_TIMEOUT = 60000;

    /**
     * 获取httpclient
     *
     * @return CloseableHttpClient httpclient
     * @throws NoSuchAlgorithmException 算法不支持抛出异常
     * @throws KeyStoreException        ssl证书异常
     * @throws KeyManagementException   ssl证书异常
     */
    public static CloseableHttpClient getHttpClient() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        return HttpClients.custom().setSSLSocketFactory(buildSslFactoryIgnoreCertificate()).build();
    }

    /**
     * 根据url获取get方法的request
     *
     * @param url 请求地址
     * @return HttpRequestBase
     */
    public static HttpRequestBase getHttpGetRequest(String url) {
        HttpRequestBase request = new HttpGet(url);
        request.setConfig(getRequestConfig());
        return request;
    }

    /**
     * 关闭httpClient 和httpResponse
     *
     * @param response   待关闭httpResponse
     * @param httpClient 待关闭httpClient
     * @throws IOException 关闭异常抛出
     */
    public static void closeClientAndResponse(CloseableHttpResponse response, CloseableHttpClient httpClient) throws IOException {
        if (response != null) {
            response.close();
        }
        if (httpClient != null) {
            httpClient.close();
        }
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
