package com.smn.httppush.demo.service;

import com.smn.httppush.demo.request.SmnPushMessageRequest;

public interface MessageHandleService {

    boolean handleSubscriptionConfirmation(SmnPushMessageRequest request);
}
