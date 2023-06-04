package com.DiscussionCommunity.exception.custom;

public class NotAcceptableException extends RuntimeException{
    public NotAcceptableException() {
        super("Not Acceptable request");
    }

    public NotAcceptableException(String message) {
        super(message);
    }
}
