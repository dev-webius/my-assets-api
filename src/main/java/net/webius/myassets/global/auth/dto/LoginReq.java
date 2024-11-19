package net.webius.myassets.global.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginReq {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
