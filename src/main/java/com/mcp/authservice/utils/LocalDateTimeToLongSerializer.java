package com.mcp.authservice.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class LocalDateTimeToLongSerializer extends JsonSerializer<LocalDateTime> {
    private static final ZoneId DEFAULT_ZONE = ZoneId.systemDefault();

    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
        } else {
            long epochMilli = value.atZone(DEFAULT_ZONE).toInstant().toEpochMilli();
            gen.writeNumber(epochMilli);
        }
    }
}
