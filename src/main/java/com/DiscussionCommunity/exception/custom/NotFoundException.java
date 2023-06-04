package com.DiscussionCommunity.exception.custom;

public class NotFoundException extends RuntimeException{
    public NotFoundException() {
        super("Resource is not found on server");
    }

    public NotFoundException(String message) {
        super(message);
    }
}
