package net.webius.myassets.handler;

import lombok.RequiredArgsConstructor;
import net.webius.myassets.component.MessageSourceProvider;
import net.webius.myassets.domain.ErrorResponse;
import net.webius.myassets.domain.ErrorValidationResponse;
import net.webius.myassets.exception.ManagedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice @RequiredArgsConstructor
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private final MessageSourceProvider messageSourceProvider;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorValidationResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        var response = new ErrorValidationResponse();
        response.setMessages(
                e.getBindingResult().getAllErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList()
                        .toArray(new String[0]));

        log.error("MethodArgumentNotValidException: {}", response);

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ManagedException.class)
    protected ResponseEntity<ErrorResponse> handleManagedException(ManagedException e) {
        var responseStatus = e.getClass().getAnnotation(ResponseStatus.class);

        ErrorResponse response = new ErrorResponse();
        response.setMessage(messageSourceProvider.get(e.getMessageTemplate(), e.getArguments()));

        log.error("ManagedException[{}]: {}", responseStatus.value(), response);

        return ResponseEntity.status(responseStatus.value()).body(response);
    }

    @ExceptionHandler(Throwable.class)
    protected ResponseEntity<ErrorResponse> handleThrowable(Throwable e) {
        ErrorResponse response = new ErrorResponse();
        response.setMessage(messageSourceProvider.get("exception.Throwable.message"));

        log.error("Throwable: {} {}", response, e.getMessage());

        return ResponseEntity.internalServerError().body(response);
    }
}
