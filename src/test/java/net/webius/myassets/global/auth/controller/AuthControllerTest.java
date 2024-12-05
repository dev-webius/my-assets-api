package net.webius.myassets.global.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.webius.myassets.global.auth.dto.SignupReq;
import net.webius.myassets.global.auth.dto.UserSessionRes;
import net.webius.myassets.global.auth.exception.UsernameAlreadyUsingException;
import net.webius.myassets.handler.GlobalExceptionHandler;
import net.webius.myassets.test.annotation.MyAssetsWebMvcTest;
import net.webius.myassets.global.auth.dto.LoginReq;
import net.webius.myassets.global.auth.exception.UsernameNotFoundException;
import net.webius.myassets.global.auth.service.JwtService;
import net.webius.myassets.global.auth.service.LoginService;
import net.webius.myassets.global.auth.service.SignupService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MyAssetsWebMvcTest(controllers = AuthController.class) @DisplayName("AuthController 테스트")
public class AuthControllerTest {
    private final MockMvc mockMvc;

    private final LoginReq loginReq;
    private final String loginReqJson;
    private final SignupReq signupReq;
    private final String signupReqJson;
    private final UserSessionRes userSessionRes;
    private final String userSessionJson;

    @Autowired private ApplicationContext context;

    @MockBean private final LoginService loginService;
    @MockBean private final SignupService signupService;
    @MockBean private final JwtService jwtService;

    @Autowired
    public AuthControllerTest(MockMvc mockMvc, ObjectMapper objectMapper, LoginService loginService, SignupService signupService, JwtService jwtService) throws JsonProcessingException {
        this.mockMvc = mockMvc;
        this.loginService = loginService;
        this.signupService = signupService;
        this.jwtService = jwtService;

        this.loginReq = new LoginReq();
        loginReq.setUsername("user");
        loginReq.setPassword("password");
        this.loginReqJson = objectMapper.writeValueAsString(loginReq);

        this.signupReq = new SignupReq();
        signupReq.setUsername("user");
        signupReq.setPassword("P@ssW0rD");
        signupReq.setPasswordConfirm("P@ssW0rD");
        signupReq.setBirthday(LocalDate.now());
        this.signupReqJson = objectMapper.writeValueAsString(signupReq);

        this.userSessionRes = new UserSessionRes();
        userSessionRes.setAccessToken("someAccessToken");
        userSessionRes.setRefreshToken("someRefreshToken");
        userSessionRes.setTokenType("Bearer");
        userSessionRes.setExpiresIn(3600);
        this.userSessionJson = objectMapper.writeValueAsString(userSessionRes);
    }

    @Test @DisplayName("POST /v1/auth/login 실패")
    public void loginFailure() throws Exception {
        Mockito
                .doThrow(UsernameNotFoundException.class)
                .when(loginService)
                .login(Mockito.any(LoginReq.class));

        mockMvc.perform(post("/v1/auth/login")
                        .content(loginReqJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test @DisplayName("POST /v1/auth/login 성공")
    public void loginSuccess() throws Exception {
        Mockito
                .doNothing()
                .when(loginService)
                .login(Mockito.any(LoginReq.class));
        Mockito
                .when(jwtService.createUserSession(Mockito.eq(loginReq.getUsername())))
                .thenReturn(userSessionRes);

        mockMvc.perform(post("/v1/auth/login")
                        .content(loginReqJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(userSessionJson))
                .andDo(print());
    }

    @Test @DisplayName("POST /v1/auth/signup 실패")
    public void signupFailure() throws Exception {
        Mockito
                .doThrow(UsernameAlreadyUsingException.class)
                .when(signupService)
                .signup(Mockito.any(SignupReq.class));

        mockMvc.perform(post("/v1/auth/signup")
                        .content(signupReqJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andDo(print());
    }

    @Test @DisplayName("POST /v1/auth/signup 성공")
    public void signupSuccess() throws Exception {
        Mockito
                .doNothing()
                .when(signupService)
                .signup(Mockito.any(SignupReq.class));
        Mockito
                .when(jwtService.createUserSession(Mockito.eq(signupReq.getUsername())))
                .thenReturn(userSessionRes);

        mockMvc.perform(post("/v1/auth/signup")
                        .content(signupReqJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string(userSessionJson))
                .andDo(print());
    }
}
