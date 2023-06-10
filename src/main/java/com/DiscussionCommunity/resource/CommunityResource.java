package com.DiscussionCommunity.resource;

import com.DiscussionCommunity.domain.Community;
import com.DiscussionCommunity.domain.dto.ApiResponse;
import com.DiscussionCommunity.domain.dto.CommunityDto;
import com.DiscussionCommunity.service.CommunityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/community")
@RestController
public class CommunityResource {
    private final CommunityService communityService;

    public CommunityResource(CommunityService communityService) {
        this.communityService = communityService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> store(@RequestBody Community community){
      communityService.store(community);
      ApiResponse response = new ApiResponse(true, HttpStatus.CREATED, "Community Created", new ArrayList<>());
      return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

  @GetMapping
  public ResponseEntity<ApiResponse<List<CommunityDto>>> getAll(){
      ApiResponse response = new ApiResponse(true, HttpStatus.OK, "Community Details Fetched", communityService.index());
      return new ResponseEntity<>(response, HttpStatus.OK);
  }

    @GetMapping("/{communityId}")
    public ResponseEntity<ApiResponse<CommunityDto>> get(@PathVariable Long communityId){
        ApiResponse response = new ApiResponse(true, HttpStatus.OK, "Community Detail Fetched", communityService.get(communityId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{communityId}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long communityId, @RequestBody Community community){
        communityService.update(communityId, community);
        ApiResponse response = new ApiResponse(true, HttpStatus.OK, "Community Details Updated", new ArrayList<>());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{communityId}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long communityId){
        communityService.delete(communityId);
        ApiResponse response = new ApiResponse(true, HttpStatus.OK, "Community Deleted", new ArrayList<>());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/{communityId}/join")
    public ResponseEntity<ApiResponse> join(@PathVariable Long communityId){
        communityService.join(communityId);
        ApiResponse response = new ApiResponse(true, HttpStatus.OK, "Community Joined", new ArrayList<>());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{communityId}/leave")
    public ResponseEntity<ApiResponse>  leave(@PathVariable Long communityId){
         communityService.leave(communityId);
        ApiResponse response = new ApiResponse(true, HttpStatus.OK, "Community left", new ArrayList<>());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
