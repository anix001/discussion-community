package com.DiscussionCommunity.service;

public interface EmailService {
    void verifyAccount(String email, String username ,String otp);
    void forgotPassword(String email, String username, String otp);
}
