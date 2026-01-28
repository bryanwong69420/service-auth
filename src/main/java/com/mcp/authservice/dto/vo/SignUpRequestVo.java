package com.mcp.authservice.dto.vo;

import com.mcp.authservice.validation.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ValidPassword
public class SignUpRequestVo {
    private String username;
    private String email;
    private String password;
    private String matchingPassword;
}
