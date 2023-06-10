package com.DiscussionCommunity.service.impl;

import com.DiscussionCommunity.domain.Community;
import com.DiscussionCommunity.domain.User;
import com.DiscussionCommunity.domain.dto.CommunityDto;
import com.DiscussionCommunity.domain.enumeration.Role;
import com.DiscussionCommunity.exception.custom.NotFoundException;
import com.DiscussionCommunity.exception.custom.UnauthorizedException;
import com.DiscussionCommunity.repository.CommunityRepository;
import com.DiscussionCommunity.repository.UserRepository;
import com.DiscussionCommunity.service.CommunityService;
import com.DiscussionCommunity.service.UserService;
import com.DiscussionCommunity.service.mapper.custom.CommunityMapper;
import com.DiscussionCommunity.service.mapper.custom.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class CommunityServiceImpl implements CommunityService {
    private final CommunityRepository communityRepository;
    private final CommunityMapper communityMapper;
    private final UserRepository userRepository;
    private final UserService userService;
    private final UserMapper userMapper;

    public CommunityServiceImpl(CommunityRepository communityRepository, CommunityMapper communityMapper, UserRepository userRepository, UserService userService, UserMapper userMapper) {
        this.communityRepository = communityRepository;
        this.communityMapper = communityMapper;
        this.userRepository = userRepository;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Override
    public void join(Long communityId) {
       Community community = communityRepository.findById(communityId).orElseThrow(()->new NotFoundException("Community not found"));
        User user = userRepository.findByEmail(userService.getCurrentLoggedInUser().getUsername()).orElseThrow(()->new NotFoundException("User is not authorized"));
        List<User> userByCommunityId = userRepository.findUserByCommunityId(community.getId());
        userByCommunityId.add(user);
        community.setUserList(userByCommunityId);
        communityRepository.save(community);
    }

    @Override
    public void leave(Long communityId) {
        Community community = communityRepository.findById(communityId).orElseThrow(()->new NotFoundException("Community not found"));
        User user = userRepository.findByEmail(userService.getCurrentLoggedInUser().getUsername()).orElseThrow(()->new NotFoundException("User is not authorized"));
        List<User> userByCommunityId = userRepository.findUserByCommunityId(community.getId());
        userByCommunityId.remove(user);
        community.setUserList(userByCommunityId);
        communityRepository.save(community);
    }

    @Override
    public void store(Community community) {
       communityRepository.save(community);
    }

    @Override
    public CommunityDto get(Long communityId) {
       Community community = communityRepository.findById(communityId).orElseThrow(()->new NotFoundException("Community not found"));
        community.setUsers(userMapper.toDtoList(community.getUserList()));
        return communityMapper.toDto(community);
    }

    @Override
    public List<CommunityDto> index() {
        List<Community> allCommunity = communityRepository.findAll();
        for (Community community: allCommunity){
            community.setUsers(userMapper.toDtoList(community.getUserList()));
        }
        return communityMapper.toDtoList(allCommunity);
    }

    @Override
    public void delete(Long communityId) {
        Community community = communityRepository.findById(communityId).orElseThrow(()->new NotFoundException("Community not found"));
        User user = userRepository.findByEmail(userService.getCurrentLoggedInUser().getUsername()).orElseThrow(()->new NotFoundException("User is not authorized"));
        if(Objects.equals(community.getCreatedBy(), user.getEmail()) || user.getRole() == Role.ADMIN){
            communityRepository.deleteById(communityId);
        }else {
            throw new UnauthorizedException("Not authorized");
        }

    }

    @Override
    public void update(Long communityId, Community community) {
        Community dbCommunity = communityRepository.findById(communityId).orElseThrow(()->new NotFoundException("Community not found"));
        User user = userRepository.findByEmail(userService.getCurrentLoggedInUser().getUsername()).orElseThrow(()->new NotFoundException("User is not authorized"));
        if(Objects.equals(dbCommunity.getCreatedBy(), user.getEmail()) || user.getRole() == Role.ADMIN){
            dbCommunity.setName(community.getName());
            communityRepository.save(dbCommunity);
        }else {
            throw new UnauthorizedException("Not authorized");
        }

    }
}
