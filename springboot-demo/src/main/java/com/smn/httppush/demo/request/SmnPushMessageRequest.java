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
package com.smn.httppush.demo.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * 消息DTO
 *
 * @author zhangyx
 * @version 1.0.0
 */
public class SmnPushMessageRequest implements Serializable {

    private static final long serialVersionUID = -7268917189862446786L;

    @JsonProperty("type")
    private String type;

    @JsonProperty("topic_urn")
    private String topicUrn;

    @JsonProperty("message_id")
    private String messageId;

    @JsonProperty("message")
    private String message;

    @JsonProperty("unsubscribe_url")
    private String unsubscribeUrl;

    @JsonProperty("subscribe_url")
    private String subscribeUrl;

    @JsonProperty("signature")
    private String signature;

    @JsonProperty("signature_version")
    private String signatureVersion;

    @JsonProperty("signing_cert_url")
    private String signingCertUrl;

    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("subject")
    private String subject;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTopicUrn() {
        return topicUrn;
    }

    public void setTopicUrn(String topicUrn) {
        this.topicUrn = topicUrn;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUnsubscribeUrl() {
        return unsubscribeUrl;
    }

    public void setUnsubscribeUrl(String unsubscribeUrl) {
        this.unsubscribeUrl = unsubscribeUrl;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getSignatureVersion() {
        return signatureVersion;
    }

    public void setSignatureVersion(String signatureVersion) {
        this.signatureVersion = signatureVersion;
    }

    public String getSigningCertUrl() {
        return signingCertUrl;
    }

    public void setSigningCertUrl(String signingCertUrl) {
        this.signingCertUrl = signingCertUrl;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSubscribeUrl() {
        return subscribeUrl;
    }

    public void setSubscribeUrl(String subscribeUrl) {
        this.subscribeUrl = subscribeUrl;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SmnPushMessageRequest{");
        sb.append("type='").append(type).append('\'');
        sb.append(", topicUrn='").append(topicUrn).append('\'');
        sb.append(", messageId='").append(messageId).append('\'');
        sb.append(", message='").append(message).append('\'');
        sb.append(", unsubscribeUrl='").append(unsubscribeUrl).append('\'');
        sb.append(", subscribeUrl='").append(subscribeUrl).append('\'');
        sb.append(", signature='").append(signature).append('\'');
        sb.append(", signatureVersion='").append(signatureVersion).append('\'');
        sb.append(", signingCertUrl='").append(signingCertUrl).append('\'');
        sb.append(", timestamp='").append(timestamp).append('\'');
        sb.append(", subject='").append(subject).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
