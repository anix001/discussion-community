package com.DiscussionCommunity.service;

import com.DiscussionCommunity.domain.User;
import com.DiscussionCommunity.domain.dto.PasswordRequest;

public interface AuthService {
    void store(User user);
    String verifyOtp(String email, String otp);
    void regenerateOtp(String email, String type);
    void forgotPassword(String email);
    void setPassword(PasswordRequest passwordRequest);
    String authenticate(PasswordRequest passwordRequest);
}
