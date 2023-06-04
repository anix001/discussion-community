package com.DiscussionCommunity.exception.custom;

public class InternalServerException extends RuntimeException{
    public InternalServerException() {
        super("Internal Server Error !!");
    }

    public InternalServerException(String message) {
        super(message);
    }
}
