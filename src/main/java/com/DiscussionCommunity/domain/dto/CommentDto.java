package com.DiscussionCommunity.domain.dto;

import com.DiscussionCommunity.domain.Reply;
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
public class CommentDto {
    @Id
    private Long id;
    private String userComment;
    private List<ReplyDto> replyList;
    private String createdBy;
}
