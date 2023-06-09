package com.DiscussionCommunity.service.mapper.custom;

import com.DiscussionCommunity.domain.Community;
import com.DiscussionCommunity.domain.dto.CommunityDto;
import com.DiscussionCommunity.service.mapper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommunityMapper extends EntityMapper<CommunityDto, Community> {
}
