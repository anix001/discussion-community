package com.DiscussionCommunity.service;

import com.DiscussionCommunity.domain.User;
import com.DiscussionCommunity.domain.dto.ChangePasswordRequest;
import com.DiscussionCommunity.domain.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserDto getSingleUser(UUID userId);
    List<UserDto> getAllUser();
    void updateUser(User user);
    void deleteUser(UUID userId);
    UserDetails getCurrentLoggedInUser();
    void changePassword(ChangePasswordRequest changePasswordRequest);

}
