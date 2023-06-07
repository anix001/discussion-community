package com.DiscussionCommunity.domain.dto;

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
    private List<CommentDto> postComments;
    private String createdBy;
}
