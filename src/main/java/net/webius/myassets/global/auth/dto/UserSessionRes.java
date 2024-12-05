package net.webius.myassets.global.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserSessionRes {
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private long expiresIn;
}
