package net.webius.myassets.validator.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.webius.myassets.annotation.FieldEquals;
import net.webius.myassets.annotation.FieldEqualsConstraint;

@FieldEqualsConstraint
@AllArgsConstructor @Getter @Setter
public class MultipleEquals {
    private String field1;

    private String field2;

    @FieldEquals({"field1", "field2"})
    private String field3;
}
