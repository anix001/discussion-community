package com.DiscussionCommunity.resource;

import com.DiscussionCommunity.domain.User;
import com.DiscussionCommunity.domain.dto.ApiResponse;
import com.DiscussionCommunity.domain.dto.UserDto;
import com.DiscussionCommunity.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/users")
@RestController
public class UserResource {
    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDto>>> index(){
     ApiResponse<List<UserDto>> userList =  new ApiResponse<>(true, HttpStatus.OK, "Users Fetched Successfully.", userService.getAllUser());
     return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @GetMapping({"/{userId}"})
    public ResponseEntity<ApiResponse<UserDto>> get(@PathVariable(name = "userId") UUID userId){
        ApiResponse<UserDto> userList =  new ApiResponse<>(true, HttpStatus.OK, "User Fetched Successfully.", userService.getSingleUser(userId));
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ApiResponse> update(@RequestBody User user){
        userService.updateUser(user);
        ApiResponse<ArrayList<Object>> response = new ApiResponse<>(true, HttpStatus.OK, "User Update Successfully.", new ArrayList<>());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> delete(@PathVariable(name = "userId") UUID userId){
        userService.deleteUser(userId);
        ApiResponse<ArrayList<Object>> response = new ApiResponse<>(true, HttpStatus.OK, "User deleted Successfully.", new ArrayList<>());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
