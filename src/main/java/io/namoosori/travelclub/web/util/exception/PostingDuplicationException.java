package io.namoosori.travelclub.web.util.exception;

public class PostingDuplicationException extends RuntimeException{
    private static final long serialVersionUID = 2140361229992526774L;

    public PostingDuplicationException(String message) {
        super(message);
    }
}
