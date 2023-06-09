package com.DiscussionCommunity.domain.dto;

import com.DiscussionCommunity.domain.enumeration.VoteStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostDto {
    @Id
    private Long id;
    private String title;
    private String description;
    private String filePath;
    private Long postVote;
    private VoteStatus userVoteStatus;
    private List<CommentDto> postComments;
    private CommunityNameDto communityName;
    private String createdBy;
}
