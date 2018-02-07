package com.smn.httppush.demo.service;

import com.smn.httppush.demo.request.SmnPushMessageRequest;

/**
 * 消息签名验证逻辑
 *
 * @author zhangyx
 * @version 1.0.0
 */
public interface MessageCheckService {

    /**
     * 校验消息是否有效
     * @param request
     * @return
     */
    boolean checkMessageValid(SmnPushMessageRequest request);

    /**
     * 清空证书缓存
     */
    void clearCertCache();
}
