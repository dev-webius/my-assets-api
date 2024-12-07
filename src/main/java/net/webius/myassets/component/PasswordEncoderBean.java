package net.webius.myassets.component;

import lombok.RequiredArgsConstructor;
import net.webius.myassets.properties.AuthProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Component;

@Component @RequiredArgsConstructor
public class PasswordEncoderBean {
    private final AuthProperties authProperties;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Pbkdf2PasswordEncoder(
                authProperties.secret(),
                authProperties.saltLength(),
                authProperties.pbkdf2().iterations(),
                authProperties.pbkdf2().algorithm());
    }
}
