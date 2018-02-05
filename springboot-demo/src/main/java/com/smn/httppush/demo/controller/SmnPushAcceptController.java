package com.smn.httppush.demo.controller;

import com.smn.httppush.demo.request.SmnPushMessageRequest;
import com.smn.httppush.demo.response.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SmnPushAcceptController {

    @RequestMapping(value = "/smn_push", method = RequestMethod.POST)
    @ResponseBody
    public Response<Void> doSmnMessage(@RequestBody SmnPushMessageRequest request) {
        System.out.println(request);
        return Response.newSuccess(null);
    }
}
