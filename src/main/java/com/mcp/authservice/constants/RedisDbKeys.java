package com.mcp.authservice.constants;

import java.text.MessageFormat;

public class RedisDbKeys {
    private RedisDbKeys() {
    }

    public static String getRedisCacheTokenKey(String token) {
        return MessageFormat.format(RedisKeyConstants.AUTH_SERVICE_MODULE_TOKEN, token);
    }
}
