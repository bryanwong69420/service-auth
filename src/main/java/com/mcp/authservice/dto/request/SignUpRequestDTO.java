package com.mcp.authservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignUpRequestDTO {

    @NotBlank
    @Size(min = 8, max = 200, message = "Username must be at between 7 and 200 characters long")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 40, message = "Password must be at between 8 and 40 characters long")
    private String password;

    @NotBlank(message = "Matching Password is Required")
    @Size(min = 8, max = 40, message = "Matching Password must be at between 8 and 40 characters long")
    private String matchingPassword;

}
