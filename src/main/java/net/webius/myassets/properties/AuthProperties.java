package net.webius.myassets.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "my-assets.auth")
@RequiredArgsConstructor @Getter
public class AuthProperties {
    private final String secret;
}
