package net.webius.myassets.validator.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.webius.myassets.annotation.FieldEqualsConstraint;
import net.webius.myassets.annotation.FieldEquals;

@FieldEqualsConstraint
@NoArgsConstructor @Getter @Setter
public class EqualsNoProvidedField {
    @FieldEquals
    private String field;
}
