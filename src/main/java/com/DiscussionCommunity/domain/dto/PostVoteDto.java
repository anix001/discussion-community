package com.DiscussionCommunity.domain.dto;

import com.DiscussionCommunity.domain.enumeration.VoteStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostVoteDto {
    @Id
    private Long id;
    private VoteStatus voteStatus;
    private String createdBy;
}
