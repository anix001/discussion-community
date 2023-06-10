package com.DiscussionCommunity.service;

import com.DiscussionCommunity.domain.Community;
import com.DiscussionCommunity.domain.dto.CommunityDto;

import java.util.List;
import java.util.UUID;

public interface CommunityService {
    void join(Long communityId);
    void leave(Long communityId);
    void store(Community community);
    CommunityDto get(Long communityId);
    List<CommunityDto> index();
    void delete(Long communityId);
    void update(Long communityId, Community community);
}
