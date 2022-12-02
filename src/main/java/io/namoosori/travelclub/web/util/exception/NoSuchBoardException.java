package io.namoosori.travelclub.web.util.exception;

public class NoSuchBoardException extends RuntimeException{

    private static final long serialVersionUID = 9123404025008896115L;

    public NoSuchBoardException(String message) {
        super(message);
    }
}
