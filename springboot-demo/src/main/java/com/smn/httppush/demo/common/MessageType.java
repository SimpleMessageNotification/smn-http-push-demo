package com.smn.httppush.demo.common;

/**
 * 短信消息类型
 */
public enum MessageType {
    SubscriptionConfirmation("SubscriptionConfirmation"),
    Notification("Notification"),
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
