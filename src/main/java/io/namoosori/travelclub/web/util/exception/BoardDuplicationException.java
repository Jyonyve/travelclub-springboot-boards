package io.namoosori.travelclub.web.util.exception;

public class BoardDuplicationException extends RuntimeException{

    private static final long serialVersionUID = 2140361229992526774L;

    public BoardDuplicationException(String message) {
        super(message);
    }
}
