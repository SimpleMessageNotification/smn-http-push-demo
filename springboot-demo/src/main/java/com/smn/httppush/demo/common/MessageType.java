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
