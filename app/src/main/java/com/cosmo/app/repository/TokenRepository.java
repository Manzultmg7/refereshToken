package com.cosmo.app.repository;

import com.cosmo.app.entity.Token;
import com.cosmo.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    List<Token> findByUserAndLoggedOutFalse(User user);

    Optional<Token> findByAccessToken(String accessToken);
    Optional<Token> findByRefreshToken(String refreshToken);
}
