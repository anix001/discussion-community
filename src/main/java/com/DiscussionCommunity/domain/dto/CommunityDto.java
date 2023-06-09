package com.DiscussionCommunity.domain.dto;

import com.DiscussionCommunity.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommunityDto {
    @Id
    private Long id;
    private String name;
    private List<UserDto> users;
}
