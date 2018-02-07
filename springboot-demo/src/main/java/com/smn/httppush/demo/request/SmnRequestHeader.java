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

/**
 * SMN请求消息头数据
 *
 * @author zhangyx
 * @version 1.0.0
 */
public class SmnRequestHeader {
    /**
     * header:X-SMN-MESSAGE-TYPE
     */
    private String messageType;

    /**
     * header:X-SMN-MESSAGE-ID
     */
    private String messageId;

    /**
     * header:X-SMN-TOPIC-URN
     */
    private String topicUrn;

    /**
     * header:X-SMN-SUBSCRIPTION-URN
     */
    private String subscriptionUrn;

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getTopicUrn() {
        return topicUrn;
    }

    public void setTopicUrn(String topicUrn) {
        this.topicUrn = topicUrn;
    }

    public String getSubscriptionUrn() {
        return subscriptionUrn;
    }

    public void setSubscriptionUrn(String subscriptionUrn) {
        this.subscriptionUrn = subscriptionUrn;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SmnRequestHeader{");
        sb.append("messageType='").append(messageType).append('\'');
        sb.append(", messageId='").append(messageId).append('\'');
        sb.append(", topicUrn='").append(topicUrn).append('\'');
        sb.append(", subscriptionUrn='").append(subscriptionUrn).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
