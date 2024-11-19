package net.webius.myassets.global.auth.dto;

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
    private String username;

    @NotBlank
    @Password
    private String password;

    @NotBlank
    @FieldEquals("password")
    private String passwordConfirm;

    @NotNull
    @PastOrPresent
    private LocalDate birthday;
}
