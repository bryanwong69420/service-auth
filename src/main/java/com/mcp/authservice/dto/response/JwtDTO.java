package com.mcp.authservice.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class JwtDTO {
    private String access_token;
    private String refresh_token;
    private Long expires_in;
    private String type = "Bearer";
    private Long id;
    private Long userId;
    private String username;
    private String email;
    private List<String> roles;

    public JwtDTO(String accessToken, String refreshToken, Long expiresIn,
                  Long id, String username, String email, List<String> roles) {
        this.access_token = accessToken;
        this.refresh_token = refreshToken;
        this.expires_in = expiresIn;
        this.id = id;
        this.userId = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}
