package com.DiscussionCommunity.service.mapper.custom;

import com.DiscussionCommunity.domain.User;
import com.DiscussionCommunity.domain.dto.UserDto;
import com.DiscussionCommunity.service.mapper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDto, User> {
}
