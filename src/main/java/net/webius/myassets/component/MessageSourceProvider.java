package net.webius.myassets.component;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageSourceProvider {
    private final MessageSource messageSource;

    public MessageSourceProvider(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String get(String name) {
        return get(name, null);
    }

    public String get(String name, Object[] args) {
        return get(name, args, Locale.getDefault());
    }

    public String get(String name, Object[] args, Locale locale) {
        return messageSource.getMessage(name, args, locale);
    }
}
