package net.webius.myassets.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidFieldTypeException extends ManagedException {
    public InvalidFieldTypeException(String message) {
        super(message);
    }
}
