package net.webius.myassets.properties;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "my-assets.auth")
public record AuthProperties(@NotBlank String secret,
                             Integer saltLength,
                             Pbkdf2Properties pbkdf2) {
    public static final Integer DEFAULT_SALT_LENGTH = 64;

    public AuthProperties {
        if (saltLength == null) {
            saltLength = DEFAULT_SALT_LENGTH;
        }
        if (pbkdf2 == null) {
            pbkdf2 = new Pbkdf2Properties();
        }
    }

    public record Pbkdf2Properties(Integer iterations,
                                   SecretKeyFactoryAlgorithm algorithm) {
        public static final Integer DEFAULT_ITERATIONS = 310000;
        public static final SecretKeyFactoryAlgorithm DEFAULT_ALGORITHM = SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256;

        public Pbkdf2Properties() {
            this(DEFAULT_ITERATIONS, DEFAULT_ALGORITHM);
        }

        public Pbkdf2Properties {
            if (iterations == null || iterations < 1) {
                iterations = DEFAULT_ITERATIONS;
            }
            if (algorithm == null) {
                algorithm = DEFAULT_ALGORITHM;
            }
        }
    }
}
