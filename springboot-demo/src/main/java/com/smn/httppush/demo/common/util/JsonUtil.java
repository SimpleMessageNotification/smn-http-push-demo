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
package com.smn.httppush.demo.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

/**
 * json util
 *
 * @author zhangyx
 * @version 1.0.0
 */
public class JsonUtil {
    /**
     * Object mapper root
     */
    private static final ObjectMapper OBJMAPPER = new ObjectMapper();

    public static final Map<String, Object> parseJsonMessage(String message) {

        if (message == null) {
            return new HashMap<String, Object>();
        }

        try {
            return OBJMAPPER.readValue(message, Map.class);
        } catch (Exception e) {
            return null;
        }
    }
}
