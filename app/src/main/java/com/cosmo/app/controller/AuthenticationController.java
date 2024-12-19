package com.cosmo.app.controller;

import com.cosmo.app.service.AuthenticationService;
import com.cosmo.common.constant.ApiConstant;
import com.cosmo.common.dto.ApiResponse;
import com.cosmo.common.dto.AuthenticationRequest;
import com.cosmo.common.util.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping()
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping(ApiConstant.LOGIN)
    public ApiResponse<?> authenticateUser(@RequestBody AuthenticationRequest authenticationRequest) {
        return authenticationService.authenticateUser(authenticationRequest);
    }

    @PostMapping(ApiConstant.REFRESH_TOKEN)
    public ApiResponse<?> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        return authenticationService.refreshToken(request, response);
    }
    @GetMapping("/test")
    public ApiResponse<?> test() {
        return ResponseUtil.getSuccessfulApiResponse( "Hello Manjul");
    }
}
