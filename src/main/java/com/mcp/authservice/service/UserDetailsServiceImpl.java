package com.mcp.authservice.service;

import com.mcp.authservice.dto.vo.UserNameVo;
import com.mcp.authservice.dto.vo.UserVo;
import com.mcp.authservice.feign.UserServiceClient;
import com.mcp.authservice.utils.CommonUtil;
import feign.FeignException;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserServiceClient userServiceClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Fetching user details for: {}", username);
        UserNameVo userNameVo = new UserNameVo();
        userNameVo.setUsername(username);
        try {
            ResponseEntity<String> response = userServiceClient.getUserByUsername(userNameVo);
            String encodeVo = Optional.ofNullable(String.valueOf(response.getBody()))
                    .orElseThrow(() -> new RuntimeException("User Not Found with username: " + username));
            UserVo vo = CommonUtil.decodeUserVo(encodeVo);
            log.info("Successfully fetched user details for: {}", username);
            return UserDetailsImpl.buildVo(vo);
        } catch (FeignException.BadRequest e) {
            log.error("BadRequest error while fetching user details for: {}", username, e);
            throw new NotFoundException("User Not Found with username: " + username);
        } catch (FeignException e) {
            log.error("General FeignException while fetching user details for: {}", username, e);
            throw new RuntimeException("Error fetching user details. Please try again later.");
        }
    }
}
