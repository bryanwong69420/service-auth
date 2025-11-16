package com.mcp.authservice.feign;

import com.mcp.authservice.constants.ApiConstants;
import com.mcp.authservice.dto.vo.UserNameVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "${user-service.name}", url = "${user-service.url}")
public interface UserServiceClient {
    @PostMapping(value = ApiConstants.USER_GET_BY_USERNAME)
    ResponseEntity<String> getUserByUsername(@RequestBody UserNameVo userNameVo);
}
