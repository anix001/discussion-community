package com.DiscussionCommunity.service.mapper.custom;

import com.DiscussionCommunity.domain.Reply;
import com.DiscussionCommunity.domain.dto.ReplyDto;
import com.DiscussionCommunity.service.mapper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReplyMapper extends EntityMapper<ReplyDto, Reply> {
}
