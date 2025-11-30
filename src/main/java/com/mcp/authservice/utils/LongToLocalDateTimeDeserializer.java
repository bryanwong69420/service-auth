package com.mcp.authservice.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class LongToLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
    private static final ZoneId DEFAULT_ZONE = ZoneId.systemDefault();

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        Long epochMilli = p.getValueAsLong();
        if (epochMilli == 0L) {
            return null;
        }
        return Instant.ofEpochMilli(epochMilli).atZone(DEFAULT_ZONE).toLocalDateTime();
    }
}
