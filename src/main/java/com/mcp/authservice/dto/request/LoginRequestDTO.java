package com.mcp.authservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequestDTO {
    @NotBlank(message = "Username is required")
    @Size(min = 7, max = 200, message = "Username must be at between 7 and 200 characters long")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 40, message = "Password must be at between 6 and 40 characters long")
    private String password;
}
