package io.namoosori.travelclub.web.util.exception;

public class NoSuchPostingException extends RuntimeException{

    private static final long serialVersionUID = 9123404025008896115L;
    public NoSuchPostingException(String message) {
        super(message);
    }
}
