package com.DiscussionCommunity.domain.dto;

import com.DiscussionCommunity.domain.enumeration.Role;
import com.DiscussionCommunity.domain.enumeration.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    @Id
    private UUID id;

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private Role role;

    private UserStatus isUserActive;

}
