package net.webius.myassets.global.health.controller;

import net.webius.myassets.test.annotation.MyAssetsWebMvcTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MyAssetsWebMvcTest(controllers = WebHealthController.class) @DisplayName("WebHealthController 테스트")
public class WebHealthControllerTest {
    private final MockMvc mvc;

    @Autowired
    public WebHealthControllerTest(MockMvc mvc) {
        this.mvc = mvc;
    }

    @Test @DisplayName("웹 서비스 체크")
    public void hello() throws Exception {
        mvc.perform(get("/v1/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("OK"));
    }
}
