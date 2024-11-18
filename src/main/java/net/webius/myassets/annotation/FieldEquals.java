package net.webius.myassets.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldEquals {
    @AliasFor("name")
    String[] value() default {};

    @AliasFor("value")
    String[] name() default {};
}
