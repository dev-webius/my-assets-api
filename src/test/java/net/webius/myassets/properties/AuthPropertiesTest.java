package net.webius.myassets.properties;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest @DisplayName("AuthProperties 테스트")
public class AuthPropertiesTest {
    private final AuthProperties authProperties;

    @Autowired
    public AuthPropertiesTest(AuthProperties authProperties) {
        this.authProperties = authProperties;
    }

    @Test @DisplayName("secret 필드 검증")
    public void secretField() {
        assertThat(authProperties.getSecret()).isEqualTo("test");
    }
}
