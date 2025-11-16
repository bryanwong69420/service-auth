package com.mcp.authservice.controller;

import com.mcp.authservice.config.security.TokenProvider;
import com.mcp.authservice.constants.ApiConstants;
import com.mcp.authservice.constants.RedisDbKeys;
import com.mcp.authservice.dto.request.LoginRequestDTO;
import com.mcp.authservice.dto.request.RegisterUserDTO;
import com.mcp.authservice.dto.response.ApiDTO;
import com.mcp.authservice.dto.response.JwtDTO;
import com.mcp.authservice.service.UserDetailsImpl;
import com.redis.redisutil.util.RedisUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping(ApiConstants.AUTH_BASE_URL)
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final RedisUtil redisUtil;
    private final


    @PostMapping(ApiConstants.LOGIN_ENDPOINT)
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        String username = loginRequestDTO.getUsername();
        String password = loginRequestDTO.getPassword();
        log.info("Start Login Authentication for Username: {}", username);
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            username,
                            password
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = tokenProvider.createToken(authentication);
            log.info("JWT generated for user: {}", username);

            long expiryDuration = tokenProvider.getExpiryDuration(jwt);
            String key = RedisDbKeys.getRedisCacheTokenKey(jwt);
            redisUtil.setCacheObject(key, jwt, Duration.ofMillis(expiryDuration));
            log.info("Stored JWT token in Redis with expiry {}ms for user: {}", expiryDuration, username);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority).collect(Collectors.toList());

            return ResponseEntity.ok(new JwtDTO(jwt, userDetails.getRefreshTokenVo().getRef_token(), expiryDuration,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    roles));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new ApiDTO(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiDTO(false, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }




}
