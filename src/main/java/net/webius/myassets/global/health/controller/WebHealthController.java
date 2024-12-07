package net.webius.myassets.global.health.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Global")
@Tag(name = "Web Health")
@RestController
@RequestMapping("/v1/hello")
public class WebHealthController {
    @GetMapping
    @Operation(summary = "웹 서비스 체크")
    @ApiResponse(responseCode = "200", description = "정상 동작", content = @Content(examples = @ExampleObject("OK")))
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("OK");
    }
}
