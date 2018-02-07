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
package com.smn.httppush.demo.common;

/**
 * 推送消息类型
 *
 * @author zhangyx
 * @version 1.0.0
 */
public enum MessageType {
    // 确认订阅消息
    SubscriptionConfirmation("SubscriptionConfirmation"),
    // 推送消息
    Notification("Notification"),
    // 取消订阅消息确认
    UnsubscribeConfirmation("UnsubscribeConfirmation");

    private String value;

    private MessageType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
