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
