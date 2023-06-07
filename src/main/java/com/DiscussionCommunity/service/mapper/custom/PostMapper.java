package com.DiscussionCommunity.service.mapper.custom;

import com.DiscussionCommunity.domain.Post;
import com.DiscussionCommunity.domain.dto.PostDto;
import com.DiscussionCommunity.service.mapper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper extends EntityMapper<PostDto, Post> {
}
