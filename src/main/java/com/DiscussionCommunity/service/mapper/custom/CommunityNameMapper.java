package com.DiscussionCommunity.service.mapper.custom;

import com.DiscussionCommunity.domain.Community;
import com.DiscussionCommunity.domain.dto.CommunityNameDto;
import com.DiscussionCommunity.service.mapper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommunityNameMapper extends EntityMapper<CommunityNameDto, Community> {
}
