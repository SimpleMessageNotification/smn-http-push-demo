package com.smn.httppush.demo.service;

import com.smn.httppush.demo.request.SmnPushMessageRequest;

/**
 * 消息处理逻辑类
 *
 * @author zhangyx
 * @version 1.0.0
 */
public interface MessageHandleService {

    /**
     * 处理订阅确认消息
     *
     * @param request 消息体
     * @return
     */
    boolean handleSubscriptionConfirmation(SmnPushMessageRequest request);

    /**
     * 处理推送消息
     *
     * @param request 消息体
     * @return
     */
    boolean handleNotificationMessage(SmnPushMessageRequest request);
}
