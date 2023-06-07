package com.DiscussionCommunity.exception.custom;

public class BadRequestException extends RuntimeException{
    public BadRequestException() {
        super("Bad Request!!");
    }

    public BadRequestException(String message) {
        super(message);
    }
}
