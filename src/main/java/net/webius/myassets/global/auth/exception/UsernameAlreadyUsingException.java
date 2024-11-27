package net.webius.myassets.global.auth.exception;

public class UsernameAlreadyUsingException extends RuntimeException {
    public UsernameAlreadyUsingException(String message) {
        super(message);
    }
}
