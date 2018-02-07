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
