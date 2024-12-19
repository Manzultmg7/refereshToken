package com.cosmo.app.config;

import com.cosmo.app.entity.Token;
import com.cosmo.app.repository.TokenRepository;
import com.cosmo.common.dto.ApiResponse;
import com.cosmo.common.util.ResponseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class CustomLogoutHandler implements LogoutHandler {
    private final ObjectMapper objectMapper;
    private final TokenRepository tokenRepository;

    public CustomLogoutHandler(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            ApiResponse<Object> apiResponse = ResponseUtil.getFailureResponse("Invalid token");
            writeResponse(response, apiResponse);
            return;
        }

        String token = authHeader.substring(7);
        Token stroedToken = tokenRepository.findByAccessToken(token).orElse(null);

        if (stroedToken != null) {
            stroedToken.setLoggedOut(true);
            tokenRepository.save(stroedToken);
            log.info("Logged out successfully");
            ApiResponse<Object> apiResponse = ResponseUtil.getSuccessfulApiResponse("Logged out successfully");
            writeResponse(response, apiResponse);
            return;
        }
        ApiResponse<Object> apiResponse = ResponseUtil.getFailureResponse("Logout Unsuccessful");
        writeResponse(response, apiResponse);
    }

    private void writeResponse(HttpServletResponse response, ApiResponse<?> apiResponse) {
        response.setContentType("application/json");
        try {
            response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
