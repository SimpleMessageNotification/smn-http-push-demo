package com.smn.httppush.demo.service.impl;

import com.smn.httppush.demo.common.EventType;
import com.smn.httppush.demo.common.util.JsonUtil;
import com.smn.httppush.demo.request.SmnPushMessageRequest;
import com.smn.httppush.demo.service.MessageHandleService;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @see MessageHandleService
 */
@Service
public class MessageHandleServiceImpl implements MessageHandleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageHandleServiceImpl.class);
    @Override
    public boolean handleSubscriptionConfirmation(SmnPushMessageRequest request) {
        // 获取确认订阅url，并请求确认订阅
        HttpClient client = new DefaultHttpClient();
        HttpResponse response;
        try {

            HttpGet httpRequest = new HttpGet(URLDecoder.decode(request.getSubscribeUrl()));
            response = client.execute(httpRequest);
        } catch (IOException e) {
            LOGGER.error("Subscribe error, url[{}]", request.getSubscribeUrl());
            return false;
        }

        int status = response.getStatusLine().getStatusCode();
        if (status >= 200 && status < 300) {
            LOGGER.info("Subscribe success, url[{}]", request.getSubscribeUrl());
        } else {
            LOGGER.info("Subscribe fail, url[{}]", request.getSubscribeUrl());
            return false;
        }
        return true;
    }

    @Override
    public boolean handleNotificationMessage(SmnPushMessageRequest request) {
        Map<String, Object> messageMap = JsonUtil.parseJsonMessage(request.getMessage());
        String messageType = "";
        if (messageMap != null) {
            messageType = (String) messageMap.get("event_type");
        }

        /**
         * 短信发送成功或者失败的body
         *{
             message_id:xxxxx,
             status:1/0,
             sign_id:xxx,
             status_desc:错误码,
             fee_num:2,
             extend_code:xxxx,
             nation_code:86,
             mobile:134****8888,
             submit_time:2016-08-08T08:10:00Z,
             deliver_time:2016-08-08T08:10:00Z,
             event_type: "sms_success_event" 或者 "sms_fail_event"
         }
         短信回复的上行reply
         {
             nation_code: "86"
             mobile:"134****8888",
             reply:"xxxxx",
             sign_id:xxx,
             deliver_time:2016-08-08T08:10:00Z,
             event_type: "sms_reply_event"
         }
         */
        if (EventType.SMS_FAIL_EVENT.getValue().equals(messageType)) {
            LOGGER.info("This is a sms send fail message, message[{}]", request.getMessage());
            // TODO something here
        } else if (EventType.SMS_SUCCESS_EVENT.getValue().equals(messageType)) {
            LOGGER.info("This is a sms send success message, message[{}]", request.getMessage());
            // TODO something here
        } else if (EventType.SMS_REPLY_EVENT.getValue().equals(messageType)) {
            LOGGER.info("This is a reply sms message, message[{}]", request.getMessage());
            // TODO something here
        } else {
            LOGGER.info("This is a notification message, message[{}]", request.getMessage());
            // TODO something here
        }
        return true;
    }
}
