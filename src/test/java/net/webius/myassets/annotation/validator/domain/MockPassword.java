package net.webius.myassets.annotation.validator.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.webius.myassets.annotation.Password;

@AllArgsConstructor @Getter @Setter
public class MockPassword {
    @Password
    private String password;
}
