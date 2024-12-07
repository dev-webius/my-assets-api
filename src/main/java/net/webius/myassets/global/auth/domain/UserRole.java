package net.webius.myassets.global.auth.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

@RequiredArgsConstructor @Getter
public enum UserRole {
    USER("ROLE_USER", "사용자"),
    ADMIN("ROLE_ADMIN","관리자"),
    ;

    private final String name;
    private final String alias;

    public Collection<GrantedAuthority> getAuthorities() {
        var roles = new HashSet<UserRole>();

        roles.add(this);
        roles.add(UserRole.USER);

        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
