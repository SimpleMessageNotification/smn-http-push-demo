package com.smn.httppush.demo.service.impl;

import com.smn.httppush.demo.common.MessageType;
import com.smn.httppush.demo.request.SmnPushMessageRequest;
import com.smn.httppush.demo.service.MessageCheckService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;
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
    private static final ConcurrentHashMap<String, InputStream> certCache = new ConcurrentHashMap<String, InputStream>();

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
            byte[] sigByte = Base64.getDecoder().decode(request.getSignature());

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
        InputStream in = certCache.get(certUrl);

        if (in == null) {
            synchronized (this) {
                in = certCache.get(certUrl);
                if (in == null) {
                    URL url = null;
                    try {
                        url = new URL(certUrl);
                        in = url.openStream();
                        certCache.putIfAbsent(certUrl, in);
                    } catch (Exception e) {
                        LOGGER.error("Get cert inputStream failed. certUrl[{}]", url);
                        return null;
                    }
                }
            }
        }
        return in;
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