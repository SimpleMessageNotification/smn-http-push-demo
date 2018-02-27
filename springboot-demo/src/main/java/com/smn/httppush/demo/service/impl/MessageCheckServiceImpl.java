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
package com.smn.httppush.demo.service.impl;

import com.smn.httppush.demo.common.MessageType;
import com.smn.httppush.demo.common.util.HttpUtil;
import com.smn.httppush.demo.request.SmnPushMessageRequest;
import com.smn.httppush.demo.service.MessageCheckService;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 消息签名验证逻辑
 *
 * @author zhangyx
 * @version 1.0.0
 * @see MessageCheckService
 */
@Service
public class MessageCheckServiceImpl implements MessageCheckService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageCheckServiceImpl.class);

    // 缓存证书, 证书失效期为24小时，根据url判断， 每天凌晨1点执行证书缓存的清理，
    // 查看定时器Scheduler
    private static final ConcurrentHashMap<String, byte[]> certCache = new ConcurrentHashMap<String, byte[]>();

    @Override
    public boolean checkMessageValid(SmnPushMessageRequest request) {
        if (!checkTypeValid(request.getType())) {
            return false;
        }

        InputStream in = getCertInputStream(request.getSigningCertUrl());
        if (in == null) {
            LOGGER.error("Get SigningCert failed, inputStream is null.");
            return false;
        }
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            X509Certificate cert = (X509Certificate) cf.generateCertificate(in);
            Signature sig = Signature.getInstance(cert.getSigAlgName());
            sig.initVerify(cert.getPublicKey());
            sig.update(buildSignMessage(request).getBytes("UTF-8"));

            byte[] sigByte = new Base64().decode(request.getSignature());

            if (sig.verify(sigByte)) {
                return true;
            }
        } catch (Exception e) {
            LOGGER.error("Verify method failed.", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    LOGGER.error("close fail");
                }
            }
        }
        return false;
    }

    private InputStream getCertInputStream(String certUrl) {
        byte[] bytes = certCache.get(certUrl);

        if (bytes == null) {
            synchronized (this) {
                bytes = certCache.get(certUrl);
                if (bytes == null) {
                    try {
                        bytes = downloadCert(certUrl);
                        if (bytes == null) {
                            return null;
                        }
                        certCache.putIfAbsent(certUrl, bytes);
                    } catch (Exception e) {
                        LOGGER.error("Get cert inputStream failed. certUrl[" + certUrl + "]", e);
                        return null;
                    }
                }
            }
        }
        return bytes2InputStream(bytes);
    }

    private byte[] downloadCert(String url) {
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = null;
        try {
            httpClient = HttpUtil.getHttpClient();
            HttpGet httpRequest = new HttpGet(URLDecoder.decode(url));
            response = httpClient.execute(httpRequest);
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                // 确认订阅成功
                return inputStream2Byte(response.getEntity().getContent());
            } else {
                LOGGER.info("Get cert inputStream failed, status[{}], url[{}]", status, url);
                return null;
            }
        } catch (Exception e) {
            LOGGER.error("Get cert error, url[" + url + "]", e);
            return null;
        } finally {
            try {
                HttpUtil.closeClientAndResponse(response, httpClient);
            } catch (IOException e) {
                LOGGER.warn("fail to close httpclient and httpResponse, url[" + url + "]", e);
            }
        }
    }

    private InputStream bytes2InputStream(byte[] bytes) {
        return new ByteArrayInputStream(bytes);
    }

    private byte[] inputStream2Byte(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while ((rc = inputStream.read(buff, 0, 100)) > 0) {
            outputStream.write(buff, 0, rc);
        }
        return outputStream.toByteArray();
    }

    private boolean checkTypeValid(String type) {
        if (MessageType.Notification.getValue().equals(type) ||
                MessageType.SubscriptionConfirmation.getValue().equals(type) ||
                MessageType.UnsubscribeConfirmation.getValue().equals(type)) {
            return true;
        }
        return false;
    }

    private String buildSignMessage(SmnPushMessageRequest request) {
        String type = request.getType();
        String message = null;
        if (MessageType.Notification.getValue().equals(type)) {
            message = buildNotificationMessage(request);
        } else if (MessageType.SubscriptionConfirmation.getValue().equals(type) ||
                MessageType.UnsubscribeConfirmation.getValue().equals(type)) {
            message = buildSubscriptionMessage(request);
        }
        return message;
    }

    private String buildSubscriptionMessage(SmnPushMessageRequest request) {
        String stringMessage = "message\n";
        stringMessage += request.getMessage() + "\n";
        stringMessage += "message_id\n";
        stringMessage += request.getMessageId() + "\n";
        stringMessage += "subscribe_url\n";
        stringMessage += request.getSubscribeUrl() + "\n";
        stringMessage += "timestamp\n";
        stringMessage += request.getTimestamp() + "\n";
        stringMessage += "topic_urn\n";
        stringMessage += request.getTopicUrn() + "\n";
        stringMessage += "type\n";
        stringMessage += request.getType() + "\n";
        return stringMessage;
    }

    private String buildNotificationMessage(SmnPushMessageRequest request) {
        String stringMessage = "message\n";
        stringMessage += request.getMessage() + "\n";
        stringMessage += "message_id\n";
        stringMessage += request.getMessageId() + "\n";
        stringMessage += "subject\n";
        if (request.getSubject() != null) {
            stringMessage += request.getSubject() + "\n";
        } else {
            stringMessage += "" + "\n";
        }
        stringMessage += "timestamp\n";
        stringMessage += request.getTimestamp() + "\n";
        stringMessage += "topic_urn\n";
        stringMessage += request.getTopicUrn() + "\n";
        stringMessage += "type\n";
        stringMessage += request.getType() + "\n";
        return stringMessage;
    }

    @Override
    public void clearCertCache() {
        certCache.clear();
    }
}