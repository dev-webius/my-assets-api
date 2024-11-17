package net.webius.myassets.learning.spring;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest @DisplayName("스프링 부트 학습 테스트")
public class SpringBootClassTest {
    @Test @DisplayName("@SpringBootTest 어노테이션을 활용한 테스트 수행")
    public void testClass() {
        TestClass testClass = new TestClass("Tom", "Cruise");

        assertThat(testClass.name).isEqualTo("Tom");
        assertThat(testClass.value).isEqualTo("Cruise");
    }

    private record TestClass(String name, String value) {}
}
