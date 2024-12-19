package com.cosmo.app.service.impl;

import com.cosmo.app.config.core.JwtService;
import com.cosmo.app.config.core.TokenUtil;
import com.cosmo.app.entity.Token;
import com.cosmo.app.entity.User;
import com.cosmo.app.repository.TokenRepository;
import com.cosmo.app.repository.UserRepository;
import com.cosmo.app.service.AuthenticationService;
import com.cosmo.common.dto.ApiResponse;
import com.cosmo.common.dto.AuthenticationRequest;
import com.cosmo.common.dto.AuthenticationResponse;
import com.cosmo.common.util.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;

    @Override
    public ApiResponse<?> authenticateUser(AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()
            ));
            Optional<User> appUser = userRepository.findByUsername(authenticationRequest.getUsername());
            if (appUser.isEmpty()) {
                return ResponseUtil.getFailureResponse("No user found with this username");
            }

            TokenUtil.revokeAllTokensByUser(appUser, tokenRepository);
            Token token =  TokenUtil.saveToken(appUser,
                    jwtService.generateAccessToken(appUser.get()),
                    tokenRepository,
                    jwtService.generateRefreshToken(appUser.get()));

            if (token!=null) {
                AuthenticationResponse authenticationResponse = new AuthenticationResponse();
                authenticationResponse.setAccessToken(token.getAccessToken());
                authenticationResponse.setRefreshToken(token.getRefreshToken());
                return ResponseUtil.getSuccessfulApiResponse(authenticationResponse, "Successfully Logged in.");
            }
            return ResponseUtil.getFailureResponse("Token generation failed");
        }catch (Exception e){
            log.info("Error: {}", e.getMessage());
            return ResponseUtil.getFailureResponse("Invalid username or password");
        }
    }

    @Override
    public ApiResponse<?> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseUtil.getFailureResponse("Invalid token");
        }

        String refreshToken = authHeader.substring(7);
        String username = jwtService.extractUsername(refreshToken);
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            return ResponseUtil.getFailureResponse("No user found");
        }
        TokenUtil.revokeAllTokensByUser(user, tokenRepository);
        Token token =  TokenUtil.saveToken(user,
                jwtService.generateAccessToken(user.get()),
                tokenRepository,
                jwtService.generateRefreshToken(user.get()));
        if (token!=null) {
            AuthenticationResponse authenticationResponse = new AuthenticationResponse();
            authenticationResponse.setAccessToken(token.getAccessToken());
            authenticationResponse.setRefreshToken(token.getRefreshToken());
            return ResponseUtil.getSuccessfulApiResponse(authenticationResponse, "Successfully refreshed token.");
        }
        return ResponseUtil.getFailureResponse("Invalid refresh token");
    }
}
