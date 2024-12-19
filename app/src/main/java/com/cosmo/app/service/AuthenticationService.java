package com.cosmo.app.service;

import com.cosmo.common.dto.ApiResponse;
import com.cosmo.common.dto.AuthenticationRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthenticationService {
    ApiResponse<?> authenticateUser(AuthenticationRequest authenticationRequest);
    ApiResponse<?> refreshToken(HttpServletRequest request, HttpServletResponse response);
}
