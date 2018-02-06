package com.smn.httppush.demo.common;

/**
 * 短信事件内省
 *
 * @author zhangyx
 * @version 1.0.0
 */
public enum EventType {

    // 短信回复消息事件
    SMS_REPLY_EVENT("sms_reply_event"),
    // 短信发送失败事件
    SMS_FAIL_EVENT("sms_fail_event"),
    // 短信发送成功事件
    SMS_SUCCESS_EVENT("sms_success_event");

    private String value;

    private EventType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
