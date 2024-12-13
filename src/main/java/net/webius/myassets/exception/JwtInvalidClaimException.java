package net.webius.myassets.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class JwtInvalidClaimException extends RuntimeException {
    public JwtInvalidClaimException(String message) {
        super(message);
    }
}
