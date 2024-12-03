package net.webius.myassets.test.annotation;

import net.webius.myassets.component.MessageSourceProvider;
import net.webius.myassets.handler.GlobalExceptionHandler;
import net.webius.myassets.test.config.MyAssetsWebMvcTestConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@WebMvcTest
@MockBean(MessageSourceProvider.class)
@Import({MyAssetsWebMvcTestConfiguration.class, GlobalExceptionHandler.class})
public @interface MyAssetsWebMvcTest {
    @AliasFor(attribute = "controllers", annotation = WebMvcTest.class)
    Class<?>[] value() default {};

    @AliasFor(attribute = "value", annotation = WebMvcTest.class)
    Class<?>[] controllers() default {};
}
