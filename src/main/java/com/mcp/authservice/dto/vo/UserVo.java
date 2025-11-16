package com.mcp.authservice.dto.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UserVo implements Serializable {
    private Long id;
    private String username;
    private String password;
    private String email;
    private Set<RoleVo> roles = new HashSet<>();
    private LocalDateTime last_login_at;
    private RefreshTokenVo refreshTokenVO;
}
