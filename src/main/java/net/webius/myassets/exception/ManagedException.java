package net.webius.myassets.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ManagedException extends RuntimeException {
    public ManagedException(String message) {
        super(message);
    }

    public final String getMessageTemplate() {
        return "exception." + getTemplateName() +  ".message";
    }

    public final String getExceptionName() {
        String classFullName = this.getClass().getName();
        return classFullName.substring(classFullName.lastIndexOf('.') + 1);
    }

    public String getTemplateName() {
        return getExceptionName();
    }

    public Object[] getArguments() {
        return null;
    }
}
