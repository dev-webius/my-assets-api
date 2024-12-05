package net.webius.myassets.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Getter @Setter
public class ErrorResponse {
    private String message;

    @Override
    public String toString() {
        return "message=" + message;
    }
}
