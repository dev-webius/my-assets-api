package net.webius.myassets.annotation.validator.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.webius.myassets.annotation.FieldEqualsConstraint;
import net.webius.myassets.annotation.FieldEquals;

@FieldEqualsConstraint
@AllArgsConstructor @Getter @Setter
public class PasswordEquals {
    private String password;

    @FieldEquals("password")
    private String passwordConfirm;
}
