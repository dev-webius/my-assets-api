package net.webius.myassets.user.installment.exception;

import net.webius.myassets.exception.ManagedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InstallmentNotFoundException extends ManagedException {
    public InstallmentNotFoundException(String message) {
        super(message);
    }
}
