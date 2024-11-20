package net.webius.myassets.global.auth.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor @Getter
public enum UserRole {
    USER("ROLE_USER", "사용자"),
    ADMIN("ROLE_ADMIN","관리자"),
    ;

    private final String name;
    private final String alias;
}
