package net.webius.myassets.handler;

import lombok.RequiredArgsConstructor;
import net.webius.myassets.component.MessageSourceProvider;
import net.webius.myassets.domain.ErrorResponse;
import net.webius.myassets.exception.ManagedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice @RequiredArgsConstructor
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private final MessageSourceProvider messageSourceProvider;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ErrorResponse response = new ErrorResponse();
        response.setMessages(
                e.getBindingResult().getAllErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList()
                        .toArray(new String[0]));

        log.error("{}", response);

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ManagedException.class)
    protected ResponseEntity<ErrorResponse> handleManagedException(ManagedException e) {
        ErrorResponse response = new ErrorResponse();
        response.setMessages(messageSourceProvider.get(e.getTemplateName(), e.getArguments()));

        log.error("{}", response);

        return ResponseEntity.badRequest().body(response);
    }
}
