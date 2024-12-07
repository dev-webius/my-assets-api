package net.webius.myassets.global.auth.service;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import net.webius.myassets.exception.JwtExpiredException;
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

    public Jws<Claims> extractToken(String token) throws JwtSignatureFailureException {
        try {
            if (token == null) {
                throw new JwtSignatureFailureException("token is null");
            }
            return getParser().parseSignedClaims(
                    token.replace(properties.tokenType() + " ", ""));
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtSignatureFailureException(token + " : " + e.getMessage());
        }
    }

    public void verifyTokenExpired(Jws<Claims> parsed) throws JwtExpiredException {
        var expiration = parsed.getPayload().getExpiration();
        if (expiration.before(new Date())) {
            throw new JwtExpiredException(expiration.toString());
        }
    }
}
