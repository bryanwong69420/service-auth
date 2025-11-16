package com.mcp.authservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RefreshJwtDTO {
    private String type;
    private String access_token;
    private String refresh_token;
    private Long expires_in;
}
