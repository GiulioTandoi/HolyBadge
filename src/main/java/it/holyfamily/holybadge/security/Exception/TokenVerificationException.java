package it.holyfamily.holybadge.security.Exception;

public class TokenVerificationException extends RuntimeException {
    public TokenVerificationException(Throwable t) {
        super(t);
    }
}