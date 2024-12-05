package net.webius.myassets.properties;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static net.webius.myassets.properties.AuthProperties.DEFAULT_SALT_LENGTH;
import static net.webius.myassets.properties.AuthProperties.Pbkdf2Properties.DEFAULT_ITERATIONS;
import static net.webius.myassets.properties.AuthProperties.Pbkdf2Properties.DEFAULT_ALGORITHM;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest @DisplayName("AuthProperties 테스트")
public class AuthPropertiesTest {
    private final AuthProperties authProperties;

    @Autowired
    public AuthPropertiesTest(AuthProperties authProperties) {
        this.authProperties = authProperties;
    }

    @Test @DisplayName("기본 필드 검증")
    public void secretAndSaltFields() {
        assertThat(authProperties.secret()).isEqualTo("test");
        assertThat(authProperties.saltLength()).isEqualTo(DEFAULT_SALT_LENGTH);
    }

    @Test @DisplayName("PBKDF2 필드 검증")
    public void pbkdf2Fields() {
        assertThat(authProperties.pbkdf2().iterations()).isEqualTo(DEFAULT_ITERATIONS);
        assertThat(authProperties.pbkdf2().algorithm()).isEqualTo(DEFAULT_ALGORITHM);
    }
}
