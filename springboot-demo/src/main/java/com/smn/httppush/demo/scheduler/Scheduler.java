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
package com.smn.httppush.demo.scheduler;

import com.smn.httppush.demo.service.MessageCheckService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时器
 */
@Component
public class Scheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(Scheduler.class);

    /**
     * 消息签名校验逻辑
     */
    @Autowired
    private MessageCheckService messageCheckService;

    /**
     * 每天凌晨一点执行证书缓存的清理
     */
    @Scheduled(cron = "${cert.cache.clean.task}")
    public void certCacheCleanTask() {
        LOGGER.info("Start to clear cert cache");
        messageCheckService.clearCertCache();
        LOGGER.info("End to clear cert cache");
    }
}
