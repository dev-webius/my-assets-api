package net.webius.myassets.config;

import lombok.RequiredArgsConstructor;
import net.webius.myassets.global.auth.domain.UserRole;
import net.webius.myassets.properties.AuthProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration @RequiredArgsConstructor
public class SecurityConfiguration {
    private final AuthProperties authProperties;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(registry -> {
                    registry
                            .requestMatchers(HttpMethod.GET, "/v1/hello").permitAll()
                            .requestMatchers(HttpMethod.POST, "/v1/auth/login", "/v1/auth/signup").permitAll()
                            .requestMatchers(HttpMethod.GET, "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                            .anyRequest().hasRole(UserRole.USER.name());
                })
                .formLogin(AbstractHttpConfigurer::disable);

        return http.build();
    }
}
