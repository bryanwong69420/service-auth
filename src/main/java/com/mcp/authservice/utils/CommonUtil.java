package com.mcp.authservice.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcp.authservice.dto.vo.UserVo;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.zip.GZIPInputStream;

public class CommonUtil {
    public static UserVo decodeUserVo(String encoded) {
        byte[] compressed = Base64.getDecoder().decode(encoded);
        ByteArrayInputStream bais = new ByteArrayInputStream(compressed);
        try (GZIPInputStream gzip = new GZIPInputStream(bais)) {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(gzip, UserVo.class);
        } catch (IOException e) {
            return null;
        }
    }
}
