package com.smn.httppush.demo.service.impl;

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

@Service
public class MessageHandleServiceImpl implements MessageHandleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageHandleServiceImpl.class);

    @Override
    public boolean handleSubscriptionConfirmation(SmnPushMessageRequest request) {

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
        }
        return true;
    }
}
