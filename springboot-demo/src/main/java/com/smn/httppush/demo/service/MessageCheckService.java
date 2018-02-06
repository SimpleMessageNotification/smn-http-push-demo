package com.smn.httppush.demo.service;

import com.smn.httppush.demo.request.SmnPushMessageRequest;

/**
 * 消息签名验证逻辑
 *
 * @author zhangyx
 * @version 1.0.0
 */
public interface MessageCheckService {

    boolean checkMessageValid(SmnPushMessageRequest request);

}
