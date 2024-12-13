package net.webius.myassets.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.webius.myassets.domain.ErrorResponse;
import net.webius.myassets.domain.ErrorValidationResponse;
import net.webius.myassets.exception.ManagedException;
import net.webius.myassets.global.auth.dto.SignupReq;
import net.webius.myassets.global.auth.exception.UsernameAlreadyUsingException;
import net.webius.myassets.global.auth.service.SignupService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest @DisplayName("GlobalExceptionHandler 테스트")
public class GlobalExceptionHandlerTest {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @MockBean private final SignupService signupService;

    private final String signupReqJson;
    private final String invalidSignupReqJson;

    @Autowired
    public GlobalExceptionHandlerTest(MockMvc mockMvc, ObjectMapper objectMapper, SignupService signupService) throws JsonProcessingException {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.signupService = signupService;

        SignupReq invalidSignupReq = new SignupReq();
        invalidSignupReq.setUsername("admin");
        invalidSignupReq.setPassword("admin");
        invalidSignupReq.setPasswordConfirm("mismatch");
        invalidSignupReq.setBirthday(LocalDate.now());
        this.invalidSignupReqJson = objectMapper.writeValueAsString(invalidSignupReq);

        SignupReq signupReq = new SignupReq();
        signupReq.setUsername("global_user");
        signupReq.setPassword("Except!0n12#");
        signupReq.setPasswordConfirm("Except!0n12#");
        signupReq.setBirthday(LocalDate.now());
        this.signupReqJson = objectMapper.writeValueAsString(signupReq);
    }

    @Test @DisplayName("Validation : MethodArgumentNotValidException 테스트")
    public void methodArgumentNotValidException() throws Exception {
        // 부적절한 Validation 객체를 전달하여 Bad Request 가 발생하는지 확인
        mockMvc.perform(post("/v1/auth/signup")
                .content(invalidSignupReqJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    // 예외가 MethodArgumentNotValidException 인지 검증
                    var exception = result.getResolvedException();
                    assertThat(exception instanceof MethodArgumentNotValidException).isTrue();

                    // Response Content 를 ErrorValidationResponse 로 읽고, 메시지 수가 하나 이상인지 검증
                    String content = result.getResponse().getContentAsString();
                    ErrorValidationResponse response = objectMapper.readValue(content, ErrorValidationResponse.class);
                    assertThat(response.getMessages().length).isGreaterThan(0);
                })
                .andDo(print());
    }

    @WithMockUser // 인가 권한이 필요한 URL 로 인한 403 방지를 위해 인가된 사용자로 설정
    @Test @DisplayName("Throwable 테스트")
    public void throwable() throws Exception {
        // 존재하지 않는 API 를 호출하여 예외 발생, 제어되지 않는 Throwable 은 별도로 핸들링 중임
        mockMvc.perform(get("/v1/foo/bar"))
                .andExpect(status().isInternalServerError())
                .andExpect(result -> {
                    // 예외가 Null 이 아닌지 검증 (Throwable 이 Exception 보다 부모이기 때문에 instanceof 대신 null 체크)
                    var exception = result.getResolvedException();
                    assertThat(exception != null).isTrue();

                    // Response Content 가 ErrorResponse 클래스인지 검증, 메시지가 Null, Empty 가 아닌지 검증
                    String content = result.getResponse().getContentAsString();
                    ErrorResponse response = objectMapper.readValue(content, ErrorResponse.class);
                    assertThat(response.getMessage()).isNotNull().isNotEmpty();
                })
                .andDo(print());
    }

    @Test @DisplayName("ManagedException 테스트")
    public void managedException() throws Exception {
        Mockito.doThrow(UsernameAlreadyUsingException.class)
                        .when(signupService)
                        .signup(Mockito.any(SignupReq.class)); // SignupReq.class 에 대한 eq matches 가 기본적으로 구현되지 않아 eq()를 적용하기 어려움

        mockMvc.perform(post("/v1/auth/signup")
                .content(signupReqJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(result -> {
                    // 예외가 ManagedException, UsernameAlreadyUsingException 관련 인스턴스인지 검증
                    var exception = result.getResolvedException();
                    assertThat(exception instanceof ManagedException).isTrue();
                    assertThat(exception instanceof UsernameAlreadyUsingException).isTrue();

                    // 예외에 적용된 ResponseStatus 가 CONFLICT 인지, 상태 코드가 `result` 와 같은지 검증
                    var responseStatusAnnotation = exception.getClass().getAnnotation(ResponseStatus.class);
                    var responseStatus = responseStatusAnnotation.value();
                    assertThat(responseStatus).isEqualTo(HttpStatus.CONFLICT);
                    assertThat(responseStatus.value()).isEqualTo(result.getResponse().getStatus());

                    // Response Content 가 ErrorResponse 클래스인지 검증, 메시지가 Null, Empty 가 아닌지 검증
                    String content = result.getResponse().getContentAsString();
                    ErrorResponse response = objectMapper.readValue(content, ErrorResponse.class);
                    assertThat(response.getMessage()).isNotNull().isNotEmpty();
                })
                .andDo(print());
    }
}
