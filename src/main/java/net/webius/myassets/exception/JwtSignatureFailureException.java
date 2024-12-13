package net.webius.myassets.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class JwtSignatureFailureException extends ManagedException {
    public JwtSignatureFailureException(String message) {
        super(message);
    }
}
