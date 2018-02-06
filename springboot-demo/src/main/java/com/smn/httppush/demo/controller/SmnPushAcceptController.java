package com.smn.httppush.demo.controller;

import com.smn.httppush.demo.common.MessageType;
import com.smn.httppush.demo.request.SmnPushMessageRequest;
import com.smn.httppush.demo.response.Response;
import com.smn.httppush.demo.service.MessageHandleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SmnPushAcceptController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SmnPushAcceptController.class);

    @Autowired
    private MessageHandleService messageHandleService;

    @RequestMapping(value = "/smn_push", method = RequestMethod.POST)
    @ResponseBody
    public Response<Void> doSmnMessage(@RequestBody SmnPushMessageRequest request) {
        LOGGER.info(request.toString());
        if (MessageType.SubscriptionConfirmation.getValue().equals(request.getType())) {
            messageHandleService.handleSubscriptionConfirmation(request);
        }

        return Response.newSuccess(null);
    }
}
