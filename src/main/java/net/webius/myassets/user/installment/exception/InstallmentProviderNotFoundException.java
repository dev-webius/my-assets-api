package net.webius.myassets.user.installment.exception;

import net.webius.myassets.exception.ManagedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InstallmentProviderNotFoundException extends ManagedException {
    public InstallmentProviderNotFoundException(String message) {
        super(message);
    }
}
