package com.mcp.authservice.config;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfig {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonCustomizer() {
        return builder -> {
            builder.featuresToEnable(SerializationFeature.WRITE_BIGDECIMAL_AS_PLAIN);

            // Support for LocalDateTime
            builder.modules(new JavaTimeModule());

            // Long to String to avoid JS precision loss
            SimpleModule longToStringModule = new SimpleModule();
            longToStringModule.addSerializer(Long.class, new ToStringSerializer());
            longToStringModule.addSerializer(Long.TYPE, new ToStringSerializer());
            builder.modules(longToStringModule);
        };
    }
}
