package net.webius.myassets.config;

import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.core.converter.ResolvedSchema;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import net.webius.myassets.domain.ErrorResponse;
import net.webius.myassets.domain.ErrorValidationResponse;
import net.webius.myassets.properties.JwtProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Configuration @RequiredArgsConstructor
public class OpenAPIConfiguration {
    private static final Logger log = LoggerFactory.getLogger(OpenAPIConfiguration.class);

    private final JwtProperties jwtProperties;

    @Bean
    public OpenAPI defineOpenApi() {
        Info info = new Info()
                .title("My Assets API")
                .version("v0.1.0")
                .description("This API expose endpoints.");

        List<Server> servers = List.of(
                new Server()
                        .url("http://localhost:8080")
                        .description("DEV")
        );

        SecurityScheme accessTokenBearerScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP) // HTTP 는 Bearer Scheme 으로 설정 시 고정적으로 Authorization: Bearer <Key> 로 동작함
                .in(SecurityScheme.In.HEADER)
                .bearerFormat("JWT")
                .name(jwtProperties.accessToken().headerName() + "test")
                .scheme("bearer");

        SecurityScheme refreshTokenBearerScheme = new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY) // API KEY 는 Bearer Scheme 이 동작하지 않음, Swagger UI 에서 수동으로 Bearer 추가 하여 테스트 해야 함
                .in(SecurityScheme.In.HEADER)
                .bearerFormat("JWT")
                .name(jwtProperties.refreshToken().headerName());

        Components components = new Components() // addSecuritySchemes(key, item) -> key 에 적용한 내용을 SecurityRequirement 에서 연결
                .addSecuritySchemes(jwtProperties.accessToken().subject(), accessTokenBearerScheme)
                .addSecuritySchemes(jwtProperties.refreshToken().subject(), refreshTokenBearerScheme)
                ;

        var securityRequirements = List.of(
                new SecurityRequirement().addList(jwtProperties.accessToken().subject()), // Access Token
                new SecurityRequirement().addList(jwtProperties.refreshToken().subject()) // Refresh Token
        );

        return new OpenAPI()
                .info(info)
                .servers(servers)
                .components(components) // (1) Components 로 등록 (상단 Authorize 버튼을 통해 등록 가능)
                .security(securityRequirements) // (2) Security Requirements 에서 이를 연결 (각 API 우측 Authorization 자물쇠 버튼을 통해 등록 가능)
                ;
    }

    @Bean
    public OpenApiCustomizer openApiCustomizer() {
        return openApi -> {
            openApi.getPaths().values().forEach(pathItem -> {
                pathItem.readOperations().forEach(operation -> {
                    // All 태그 추가
                    operation.addTagsItem("All");

                    operation.getResponses().forEach((responseCode, apiResponse) -> {
                        MediaType mediaType;

                        // Void 를 리턴하는 경우 건너뛰기
                        if (apiResponse.getContent() == null) {
                            return;
                        }

                        if (responseCode.equals("400")) { // 400 Bad Request -> ErrorValidationResponse
                            mediaType = new MediaType().schema(resolveSchema(ErrorValidationResponse.class).schema); // NOTE: ErrorResponse 와 동시에 사용하는 방안 모색 필요
                        } else if (responseCode.startsWith("4") || responseCode.startsWith("5")) { // 4XX or 5XX -> ErrorResponse
                            mediaType = new MediaType().schema(resolveSchema(ErrorResponse.class).schema);
                        } else { // Others -> 기본 스키마 정보를 따라감
                            mediaType = apiResponse.getContent().getOrDefault("*/*", new MediaType());
                        }

                        apiResponse.content(new Content().addMediaType(APPLICATION_JSON_VALUE, mediaType)); // 모든 ApiResponse 에 대해 application/json 미디어타입을 적용함
                    });
                });
            });
        };
    }

    private ResolvedSchema resolveSchema(Class<?> clazz) {
        return ModelConverters.getInstance().resolveAsResolvedSchema(new AnnotatedType(clazz));
    }
}
