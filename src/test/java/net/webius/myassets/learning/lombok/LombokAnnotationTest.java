package net.webius.myassets.learning.lombok;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class LombokAnnotationTest {
    @Test
    public void testClass() {
        TestClass testClass = new TestClass("Tom", "Cruise");

        assertThat(testClass.name).isEqualTo("Tom");
        assertThat(testClass.value).isEqualTo("Cruise");
    }

    // INFO: gradle - testAnnotationProcessor 'org.projectlombok:lombok' 추가 후 동작
    @AllArgsConstructor @Getter @Setter
    private static class TestClass {
        private String name;
        private String value;
    }
}
