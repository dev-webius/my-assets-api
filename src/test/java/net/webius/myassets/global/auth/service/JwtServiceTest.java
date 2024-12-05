package net.webius.myassets.global.auth.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;
import net.webius.myassets.properties.JwtProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest @DisplayName("JwtService 테스트")
public class JwtServiceTest {
    private static final Logger log = LoggerFactory.getLogger(JwtServiceTest.class);

    private final JwtProperties jwtProperties;
    private final JwtService jwtService;
    private final String accessToken;

    @Autowired
    public JwtServiceTest(JwtProperties jwtProperties, JwtService jwtService) {
        this.jwtProperties = jwtProperties;
        this.jwtService = jwtService;
        this.accessToken = jwtService.createAccessToken("string");
        log.info("Created access token: {}", accessToken);
    }

    @Test @DisplayName("Access Token 테스트")
    public void accessToken() {
        var parsed = jwtService.extractAccessToken(accessToken);
        var header = parsed.getHeader();
        var payload = parsed.getPayload();

        assertThat(header.isPayloadEncoded()).isTrue();
        assertThat(header.getAlgorithm()).isEqualTo(Jwts.SIG.HS512.toString());

        assertThat(payload.getSubject()).isEqualTo(jwtProperties.accessToken().subject());
        assertThat(payload.get("username")).isEqualTo("string");
    }

    @Test @DisplayName("Signature Exception 테스트")
    public void signatureException() {
        assertThatThrownBy(() -> {
            Jwts.parser()
                    .verifyWith(Jwts.SIG.HS512.key().build())
                    .build()
                    .parseSignedClaims(accessToken);
        })
                .isInstanceOf(SignatureException.class);
    }
}
