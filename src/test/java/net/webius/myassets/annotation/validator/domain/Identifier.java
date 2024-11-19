package net.webius.myassets.annotation.validator.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.webius.myassets.annotation.IdentifierPattern;

@AllArgsConstructor @Getter @Setter
public class Identifier {
    @IdentifierPattern
    private String identifier;
}
