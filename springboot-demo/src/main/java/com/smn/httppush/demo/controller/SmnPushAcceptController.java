package com.smn.httppush.demo.controller;

import com.smn.httppush.demo.common.MessageType;
import com.smn.httppush.demo.request.SmnPushMessageRequest;
import com.smn.httppush.demo.request.SmnRequestHeader;
import com.smn.httppush.demo.response.Response;
import com.smn.httppush.demo.service.MessageCheckService;
import com.smn.httppush.demo.service.MessageHandleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 接收smn推送消息的controller
 *
 * @author zhangyx
 * @version 1.0.0
 */
@Controller
public class SmnPushAcceptController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SmnPushAcceptController.class);

    /**
     * 消息处理服务
     */
    @Autowired
    private MessageHandleService messageHandleService;

    /**
     * 消息签名校验逻辑
     */
    @Autowired
    private MessageCheckService messageCheckService;

    /**
     * 接收smn推送消息，并处理
     *
     * @param smnPushMessageRequest body体
     * @param headers               请求头数据
     * @return
     */
    @RequestMapping(value = "/smn_push", method = RequestMethod.POST)
    @ResponseBody
    public Response<Void> doSmnMessage(@RequestBody SmnPushMessageRequest smnPushMessageRequest,
                                       @RequestHeader HttpHeaders headers) {
        SmnRequestHeader header = getHeader(headers);
        LOGGER.info("body message is: {}", smnPushMessageRequest);
        LOGGER.info("header message is: {}", header);

        // 校验消息签名
        boolean checkResult = messageCheckService.checkMessageValid(smnPushMessageRequest);
        if(!checkResult) {
            LOGGER.info("This message signature is check invalid.");
            return Response.newSuccess(null);
        }

        String messageType = smnPushMessageRequest.getType();
        if (MessageType.SubscriptionConfirmation.getValue().equals(messageType)) {
            // 处理订阅确认消息
            messageHandleService.handleSubscriptionConfirmation(smnPushMessageRequest);
        } else if (MessageType.Notification.getValue().equals(messageType)) {
            // 处理推送消息
            messageHandleService.handleNotificationMessage(smnPushMessageRequest);
        } else if (MessageType.UnsubscribeConfirmation.getValue().equals(messageType)) {
            // TODO something here
        } else {
            LOGGER.info("Incorrect message type");
        }

        return Response.newSuccess(null);
    }

    private SmnRequestHeader getHeader(HttpHeaders headers) {
        SmnRequestHeader header = new SmnRequestHeader();
        header.setMessageId(headers.getFirst("X-SMN-MESSAGE-ID"));
        header.setMessageType(headers.getFirst("X-SMN-MESSAGE-TYPE"));
        header.setSubscriptionUrn(headers.getFirst("X-SMN-SUBSCRIPTION-URN"));
        header.setTopicUrn(headers.getFirst("X-SMN-TOPIC-URN"));

        return header;
    }
}
