package net.webius.myassets.global.auth.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import net.webius.myassets.exception.JwtSignatureFailureException;
import net.webius.myassets.global.auth.dto.UserSessionRes;
import net.webius.myassets.properties.JwtProperties;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service @RequiredArgsConstructor
public class JwtService {
    private final JwtProperties properties;

    private JwtParser getParser() {
        return Jwts.parser()
                .verifyWith(properties.secretKey())
                .build();
    }

    public String createAccessToken(String username) {
        Date currentTime = new Date();
        return Jwts.builder()
                .subject(properties.accessToken().subject())
                .expiration(new Date(currentTime.getTime() + properties.accessToken().expiration() * 1000))
                .claim("username", username)
                .signWith(properties.secretKey())
                .compact();
    }

    public String createRefreshToken(String username) {
        Date currentTime = new Date();
        return Jwts.builder()
                .subject(properties.refreshToken().subject())
                .expiration(new Date(currentTime.getTime() + properties.refreshToken().expiration() * 1000))
                .claim("username", username)
                .signWith(properties.secretKey())
                .compact();
    }

    public UserSessionRes createUserSession(String username) {
        String accessToken = createAccessToken(username);
        String refreshToken = createRefreshToken(username);

        var response = new UserSessionRes();
        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);
        response.setTokenType(properties.tokenType());
        response.setExpiresIn(properties.accessToken().expiration());

        return response;
    }

    private Jws<Claims> extractToken(String token) throws JwtSignatureFailureException {
        try {
            return getParser()
                    .parseSignedClaims(token.replace(properties.tokenType() + " ", ""));
        } catch (SignatureException e) {
            throw new JwtSignatureFailureException(e.getMessage());
        } catch (JwtException e) {
            throw new RuntimeException(e);
        }
    }

    public Jws<Claims> extractAccessToken(String accessToken) throws JwtSignatureFailureException {
        return extractToken(accessToken);
    }

    public Jws<Claims> extractRefreshToken(String refreshToken) throws JwtSignatureFailureException {
        return extractToken(refreshToken);
    }
}
