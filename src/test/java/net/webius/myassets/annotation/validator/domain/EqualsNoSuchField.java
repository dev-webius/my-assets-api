package net.webius.myassets.annotation.validator.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.webius.myassets.annotation.FieldEqualsConstraint;
import net.webius.myassets.annotation.FieldEquals;

@FieldEqualsConstraint
@NoArgsConstructor @Getter @Setter
public class EqualsNoSuchField {
    @FieldEquals("unknownField")
    private String field;
}
