package net.webius.myassets.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest @DisplayName("InvalidFieldTypeException 테스트")
public class InvalidFieldTypeExceptionTest {
    @Test @DisplayName("메시지 템플릿 테스트")
    public void getMessageTemplate() {
        try {
            throw new InvalidFieldTypeException("test");
        } catch (InvalidFieldTypeException e) {
            assertThat(e.getMessageTemplate()).isEqualTo("{exception.InvalidFieldTypeException.message}");
        }
    }
}
