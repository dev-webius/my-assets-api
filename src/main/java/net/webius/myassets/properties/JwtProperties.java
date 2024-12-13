package net.webius.myassets.properties;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.validation.annotation.Validated;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Validated
@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(String secret,
                            @ReadOnlyProperty SecretKey secretKey,
                            Token accessToken,
                            Token refreshToken) {
    private static final Logger log = LoggerFactory.getLogger(JwtProperties.class);

    public static final int SECRET_LENGTH_AT_LEAST = 64; // 64bytes -> 512bits

    public static final String DEFAULT_TOKEN_TYPE = "Bearer";

    public static final Long DEFAULT_ACCESS_TOKEN_EXPIRATION = 3600L; // 1시간 (60m * 60s)
    public static final String DEFAULT_ACCESS_TOKEN_HEADER_NAME = "Authorization";
    public static final String DEFAULT_ACCESS_TOKEN_SUBJECT = "Access Token";

    public static final Long DEFAULT_REFRESH_TOKEN_EXPIRATION = 1209600L; // 2주 (2w * 7d * 24h * 60m * 60s)
    public static final String DEFAULT_REFRESH_TOKEN_HEADER_NAME = "Authorization-Refresh";
    public static final String DEFAULT_REFRESH_TOKEN_SUBJECT = "Refresh Token";

    public String tokenType() {
        return DEFAULT_TOKEN_TYPE;
    }

    public JwtProperties {
        if (secret != null && secret.trim().length() < SECRET_LENGTH_AT_LEAST) {
            log.warn("`secret` 길이를 최소 " + SECRET_LENGTH_AT_LEAST + "자 이상으로 설정해주세요.");
            secret = null;
        }
        if (secret == null || secret.isEmpty()) {
            secretKey = Jwts.SIG.HS512.key().build();
            log.info("HS512 Random Build: {}", secretKey.getEncoded());
        } else {
            secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
            log.info("Build key by secret string");
        }
        if (accessToken == null) {
            accessToken = new Token(
                    DEFAULT_ACCESS_TOKEN_EXPIRATION,
                    DEFAULT_ACCESS_TOKEN_HEADER_NAME,
                    DEFAULT_ACCESS_TOKEN_SUBJECT);
        }
        if (refreshToken == null) {
            refreshToken = new Token(
                    DEFAULT_REFRESH_TOKEN_EXPIRATION,
                    DEFAULT_REFRESH_TOKEN_HEADER_NAME,
                    DEFAULT_REFRESH_TOKEN_SUBJECT);
        }
    }

    public record Token(@NotNull Long expiration,
                        @NotBlank String headerName,
                        @NotBlank String subject) {}
}
