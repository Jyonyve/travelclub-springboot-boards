package io.namoosori.travelclub.web.util.exception;

public class NoSuchCommentException extends RuntimeException{

    private static final long serialVersionUID = 9123404025008896115L;

    public NoSuchCommentException(String message){
        super(message);
    }
}
