package com.DiscussionCommunity.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReplyDto {
    @Id
    private Long id;
    private String commentReply;
    private String createdBy;
}
