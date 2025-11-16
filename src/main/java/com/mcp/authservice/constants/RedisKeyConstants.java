package com.mcp.authservice.constants;

public class RedisKeyConstants {
    public static final String GLOBAL = "global:mcp:";
    public static final String AUTH_SERVICE_MODULE = GLOBAL + "authService:";
    public static final String AUTH_SERVICE_MODULE_TOKEN = AUTH_SERVICE_MODULE + "token:{0}";
}
