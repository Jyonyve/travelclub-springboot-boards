package io.namoosori.travelclub.web.util.exception;

public class CommentDuplicationException extends RuntimeException {

    private static final long serialVersionUID = 2140361229992526774L;

    public CommentDuplicationException(String message) {
        super(message);
    }
}
