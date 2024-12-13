package net.webius.myassets.component;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageSourceProvider {
    private final MessageSource messageSource;

    public MessageSourceProvider(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String get(String name) throws NoSuchMessageException {
        return get(name, null);
    }

    public String get(String name, Object[] args) throws NoSuchMessageException {
        return get(name, args, Locale.getDefault());
    }

    public String get(String name, Object[] args, Locale locale) throws NoSuchMessageException {
        return messageSource.getMessage(name, args, locale);
    }
}
