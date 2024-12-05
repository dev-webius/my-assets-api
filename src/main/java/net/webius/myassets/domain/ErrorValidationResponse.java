package net.webius.myassets.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Getter @Setter
public class ErrorValidationResponse {
    private String[] messages;

    @Override
    public String toString() {
        return "messages=" + Arrays.toString(messages);
    }
}
