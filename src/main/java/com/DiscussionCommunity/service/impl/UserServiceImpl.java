package com.DiscussionCommunity.service.impl;

import com.DiscussionCommunity.domain.User;
import com.DiscussionCommunity.domain.dto.ChangePasswordRequest;
import com.DiscussionCommunity.domain.dto.UserDto;
import com.DiscussionCommunity.exception.custom.NotAcceptableException;
import com.DiscussionCommunity.exception.custom.NotFoundException;
import com.DiscussionCommunity.repository.UserRepository;
import com.DiscussionCommunity.service.UserService;
import com.DiscussionCommunity.service.mapper.custom.UserMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserDetailsService userDetailsService, BCryptPasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto getSingleUser(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new NotFoundException("User not Found."));
        return userMapper.toDto(user);
    }

    @Override
    public List<UserDto> getAllUser() {
        return userMapper.toDtoList(userRepository.findAll());
    }

    @Override
    public void updateUser(User user) {
        User dbUser = userRepository.findByEmail(getCurrentLoggedInUser().getUsername()).orElseThrow(()-> new NotFoundException("User not Found."));
        dbUser.setFirstName(user.getFirstName()!= null? user.getFirstName() : dbUser.getFirstName());
        dbUser.setLastName(user.getLastName()!= null ? user.getLastName() : dbUser.getLastName());
        userRepository.save(dbUser);
    }

    @Override
    @Transactional
    public void deleteUser(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new NotFoundException("User not Found."));
        userRepository.deleteById(user.getId());
    }

    @Override
    public UserDetails getCurrentLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Optional<User> user = userRepository.findByEmail(userDetails.getUsername());
        return userDetailsService.loadUserByUsername(user.get().getEmail());
    }

    @Override
    public void changePassword(ChangePasswordRequest changePasswordRequest) {
        User user = userRepository.findByEmail(getCurrentLoggedInUser().getUsername()).orElseThrow(()-> new NotFoundException("User not Found."));
        if(passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())){
            if(Objects.equals(changePasswordRequest.getNewPassword(), changePasswordRequest.getConfirmNewPassword())){
                user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
                userRepository.save(user);
            }else{
                throw new NotAcceptableException("New Password and Confirm New Password did not match.");
            }
        }else{
            throw new NotAcceptableException("Old password does not match");
        }
    }

}
