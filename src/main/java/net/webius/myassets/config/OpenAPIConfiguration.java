package net.webius.myassets.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfiguration {
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

        return new OpenAPI()
                .info(info)
                .servers(servers);
    }
}
