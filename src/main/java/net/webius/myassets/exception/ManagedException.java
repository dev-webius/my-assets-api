package net.webius.myassets.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ManagedException extends RuntimeException {
    public ManagedException(String message) {
        super(message);
    }

    protected final String getMessageTemplate() {
        return "{exception." + getTemplateName() +  ".message}";
    }

    protected final String getExceptionName() {
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
