package net.webius.myassets.global.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import net.webius.myassets.annotation.FieldEqualsConstraint;
import net.webius.myassets.annotation.FieldEquals;
import net.webius.myassets.annotation.IdentifierPattern;
import net.webius.myassets.annotation.Password;

import java.time.LocalDate;

@FieldEqualsConstraint
@Getter @Setter
public class SignupReq {
    @NotBlank
    @Size(max = 20)
    @IdentifierPattern
    @Schema(example = "testuser")
    private String username;

    @NotBlank
    @Password
    @Schema(example = "testPass12@")
    private String password;

    @NotBlank
    @FieldEquals("password")
    @Schema(example = "testPass12@")
    private String passwordConfirm;

    @NotNull
    @PastOrPresent
    private LocalDate birthday;
}
