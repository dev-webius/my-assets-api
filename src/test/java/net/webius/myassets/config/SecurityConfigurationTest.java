package net.webius.myassets.config;

import net.webius.myassets.global.auth.controller.AuthController;
import net.webius.myassets.global.health.controller.WebHealthController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(SecurityConfiguration.class)
@WebMvcTest(controllers = {
        WebHealthController.class,
        AuthController.class,
}) @DisplayName("Spring Security 테스트")
public class SecurityConfigurationTest {
    private final MockMvc mockMvc;

    @Autowired
    public SecurityConfigurationTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test @DisplayName("Permit All 테스트")
    public void permitAll() throws Exception {
        mockMvc.perform(get("/v1/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("OK"));

        RequestBuilder[] requests = {
                get("/v1/hello"),
                post("/v1/auth/login"),
                post("/v1/auth/signup"),
                get("/swagger-ui/index.html"),
                get("/v3/api-docs")
        };
        for (RequestBuilder request : requests) {
            mockMvc.perform(request)
                    .andExpect(status().is(
                            allOf(
                                    not(HttpStatus.UNAUTHORIZED),
                                    not(HttpStatus.FORBIDDEN))));
        }
    }
}
