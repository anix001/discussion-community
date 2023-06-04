package com.DiscussionCommunity.resource;

import com.DiscussionCommunity.domain.User;
import com.DiscussionCommunity.domain.dto.ApiResponse;
import com.DiscussionCommunity.domain.dto.PasswordRequest;
import com.DiscussionCommunity.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Objects;

@RestController
@RequestMapping("/auth")
public class AuthResource {
    private final AuthService authService;

    public AuthResource(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody User user){
        authService.store(user);
        ApiResponse<ArrayList<Object>> response = new ApiResponse<>(true, HttpStatus.CREATED, "Check your Email and Verify user", new ArrayList<>());
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @GetMapping("/verify-user")
    public ModelAndView verifyUser(@RequestParam(value = "user") String user, @RequestParam(value = "type") String type,@RequestParam(value = "activate_user") String activate_user){
        String result = authService.verifyOtp(user, activate_user);
        ModelAndView verifyUserTemplate = new ModelAndView(Objects.equals(result, "verified") ? "verifyUser" : "RegenerateVerifyOtp");
        verifyUserTemplate.addObject("user", user);
        verifyUserTemplate.addObject("type", type);
        return verifyUserTemplate;
    }

    @GetMapping("/regenerate-otp")
    public ResponseEntity<ApiResponse> regenerateOtp(@RequestParam(value = "user") String user, @RequestParam(value = "type") String type){
        authService.regenerateOtp(user, type);
        ApiResponse<ArrayList<Object>> response = new ApiResponse<>(true, HttpStatus.CREATED, "Check your Email and Verify user", new ArrayList<>());
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse> forgotPassword(@RequestParam(value = "user") String user){
        authService.forgotPassword(user);
        ApiResponse<ArrayList<Object>> response = new ApiResponse<>(true, HttpStatus.CREATED, "Check your Email and Verify user", new ArrayList<>());
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @PostMapping("/set-password")
    public ResponseEntity<ApiResponse> setNewPassword(@RequestBody PasswordRequest passwordData){
        authService.setPassword(passwordData);
        ApiResponse<ArrayList<Object>> response = new ApiResponse<>(true, HttpStatus.OK, "Password is set.", new ArrayList<>());
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<ApiResponse> authenticate(@RequestBody PasswordRequest passwordRequest){
        ApiResponse<String> response = new ApiResponse<>(true, HttpStatus.OK, "User LoggedIn Successfully", authService.authenticate(passwordRequest));
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

}
