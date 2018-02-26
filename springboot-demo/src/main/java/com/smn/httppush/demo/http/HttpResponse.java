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
package com.smn.http;

import org.apache.http.Header;

import java.util.HashMap;
import java.util.Map;

/**
 * http response data
 *
 * @author zhangyx
 * @version 2.0.0
 */
public class HttpResponse {

    /**
     * http status code
     */
    private int httpCode;

    /**
     * http response data string
     */
    private String content;

    /**
     * response headers
     */
    private Map<String, String> headers;


    /**
     * @return the httpcode
     */
    public int getHttpCode() {
        return httpCode;
    }

    /**
     * set httpcode
     *
     * @param httpCode
     */
    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    /**
     * @return http response conent
     */
    public String getContent() {
        return content;
    }

    /**
     * set http conent
     *
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Judge whether the request is successful or not
     *
     * @return if success return true, else return false
     */
    public boolean isSuccess() {
        return 200 <= httpCode && httpCode < 300;
    }
}
