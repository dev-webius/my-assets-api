package net.webius.myassets.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ErrorResponse {
    private String[] messages;
}