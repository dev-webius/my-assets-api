package net.webius.myassets.learning.spring;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.util.UriComponentsBuilder;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest @DisplayName("UriComponentsBuilder 테스트")
public class UriComponentsBuilderTest {
    private static final Logger log = LoggerFactory.getLogger(UriComponentsBuilderTest.class);

    @Test @DisplayName("newInstance 테스트")
    public void newInstance() {
        String uri = "/v1/foo/bar";

        UriComponentsBuilder builder = UriComponentsBuilder.newInstance();
        builder.path("/v1");
        builder.path("/foo");
        builder.path("/bar");
        var uriComponents = builder.build();

        assertThat(uriComponents.toString()).isEqualTo(uri);
    }

    @Test @DisplayName("파라미터 테스트")
    public void parameter() {
        String uri = "/v1/foo/bar";

        UriComponentsBuilder builder = UriComponentsBuilder.newInstance();
        builder.path("/v1");
        builder.path("/foo");
        builder.path("/{param}");
        var uriComponents = builder.build().expand("bar");

        assertThat(uriComponents.toString()).isEqualTo(uri);
    }

    @Test @DisplayName("파라미터 여러개 테스트")
    public void parameters() {
        String uri = "/v1/foo/bar";

        UriComponentsBuilder builder = UriComponentsBuilder.newInstance();
        builder.path("/v1");
        builder.path("/{p1}");
        builder.path("/{p2}");
        var uriComponents = builder.build().expand("foo", "bar");

        assertThat(uriComponents.toString()).isEqualTo(uri);
    }

    @Test @DisplayName("파라미터 개수 다름 테스트")
    public void parametersThrow() {
        UriComponentsBuilder builder = UriComponentsBuilder.newInstance();
        builder.path("/v1");
        var uriComponents = builder.build().expand("foo", "bar");

        // 오류가 발생하진 않고 Variables 가 무시됨
        assertThat(uriComponents.toString()).isEqualTo("/v1");
    }
}
