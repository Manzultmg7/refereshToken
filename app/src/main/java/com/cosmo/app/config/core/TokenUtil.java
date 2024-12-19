package com.cosmo.app.config.core;


import com.cosmo.app.entity.Token;
import com.cosmo.app.entity.User;
import com.cosmo.app.repository.TokenRepository;

import java.util.List;
import java.util.Optional;

public class TokenUtil {
    public static Token saveToken(Optional<User> user, String accessToken, TokenRepository tokenRepo, String refreshToken) {
        Token token = new Token();
        token.setUser(user.get());
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);
        token.setLoggedOut(false);
        return tokenRepo.save(token);
    }

    public static void revokeAllTokensByUser(Optional<User> user, TokenRepository tokenRepository) {
        List<Token> tokens = tokenRepository.findByUserAndLoggedOutFalse(user.get());
        if (!tokens.isEmpty()) {
            for (Token token : tokens){
                token.setLoggedOut(true);
                tokenRepository.save(token);
            }
        }
    }
}