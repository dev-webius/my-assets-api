package net.webius.myassets.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class JwtRequiredException extends ManagedException {
    public JwtRequiredException(String message) {
        super(message);
    }
}
