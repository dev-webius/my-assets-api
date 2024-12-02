package net.webius.myassets.global.auth.exception;

import net.webius.myassets.exception.ManagedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UsernameNotFoundException extends ManagedException {
    public UsernameNotFoundException(String message) {
        super(message);
    }
}