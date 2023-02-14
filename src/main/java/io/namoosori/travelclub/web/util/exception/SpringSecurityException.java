package io.namoosori.travelclub.web.util.exception;

public class SpringSecurityException extends RuntimeException{
    private static final long serialVersionUID = 5586581752975347197L;

    public SpringSecurityException(String message) {
        super(message);
    }
}
