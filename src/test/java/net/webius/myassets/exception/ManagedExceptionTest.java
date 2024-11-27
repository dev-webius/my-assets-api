package net.webius.myassets.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest @DisplayName("ManagedException 테스트")
public class ManagedExceptionTest {
    private static final Logger log = LoggerFactory.getLogger(ManagedExceptionTest.class);

    @Test @DisplayName("Class Name 결과 표시")
    public void getClassName() {
        String classFullName = getClass().getName();
        assertThat(classFullName).isEqualTo("net.webius.myassets.exception.ManagedExceptionTest");
        assertThat(Arrays.stream(classFullName.split("\\.")).toList().getLast())
                .isEqualTo("ManagedExceptionTest");
        assertThat(classFullName.substring(classFullName.lastIndexOf(".") + 1))
                .isEqualTo("ManagedExceptionTest");
    }

    @Test @DisplayName("getExceptionName() 테스트")
    public void getExceptionName() {
        try {
            throw new ManagedException("test");
        } catch (ManagedException e) {
            assertThat(e.getTemplateName()).isEqualTo("ManagedException");
            assertThat(e.getMessageTemplate()).isEqualTo("{exception.ManagedException.message}");
        }
    }
}
