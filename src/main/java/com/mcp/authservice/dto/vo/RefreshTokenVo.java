package com.mcp.authservice.dto.vo;

import com.mcp.authservice.utils.JsonEpochMilli;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenVo implements Serializable {
    private long id;
    private Long userId;
    private String refreshToken;
    @JsonEpochMilli
    private LocalDateTime expDate;
}
