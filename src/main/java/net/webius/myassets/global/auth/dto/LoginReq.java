package net.webius.myassets.global.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginReq {
    @NotBlank
    @Schema(example = "testuser")
    private String username;

    @NotBlank
    @Schema(example = "testPass12@")
    private String password;
}
