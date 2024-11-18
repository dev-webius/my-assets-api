package net.webius.myassets.validator.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.webius.myassets.annotation.FieldEqualsConstraint;
import net.webius.myassets.annotation.FieldEquals;

@FieldEqualsConstraint
@AllArgsConstructor @Getter @Setter
public class PrimitiveEquals {
    private int int1;
    @FieldEquals("int1")
    private int int2;

    private long long1;
    @FieldEquals("long1")
    private long long2;

    private double double1;
    @FieldEquals("double1")
    private double double2;

    private boolean boolean1;
    @FieldEquals("boolean1")
    private boolean boolean2;
}
