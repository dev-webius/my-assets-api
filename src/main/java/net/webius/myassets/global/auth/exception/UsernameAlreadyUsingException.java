package net.webius.myassets.global.auth.exception;

import net.webius.myassets.exception.ManagedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UsernameAlreadyUsingException extends ManagedException {
    public UsernameAlreadyUsingException(String message) {
        super(message);
    }
}
