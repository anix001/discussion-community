package com.DiscussionCommunity.service.mapper.custom;

import com.DiscussionCommunity.domain.Comment;
import com.DiscussionCommunity.domain.dto.CommentDto;
import com.DiscussionCommunity.service.mapper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper extends EntityMapper<CommentDto, Comment> {
}
