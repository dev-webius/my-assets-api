package net.webius.myassets.learning.spring;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SpringBootClassTest {
    @Test
    public void testClass() {
        TestClass testClass = new TestClass("Tom", "Cruise");

        assertThat(testClass.name).isEqualTo("Tom");
        assertThat(testClass.value).isEqualTo("Cruise");
    }

    private record TestClass(String name, String value) {}
}
