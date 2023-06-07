package com.DiscussionCommunity.service;

import com.DiscussionCommunity.domain.Comment;
import com.DiscussionCommunity.domain.Post;
import com.DiscussionCommunity.domain.Reply;
import com.DiscussionCommunity.domain.dto.PostDto;

import java.io.IOException;
import java.util.List;

public interface PostService {
    void store(Post post);
    List<PostDto> index();
    PostDto get(Long postId);
    byte[] download(Long postId);
    void delete(Long postId);
    void update(Long postId,Post post);
    void postComment(Long postId, List<Comment> comment);
    void updatePostComment(Long commentId, Comment comment);
    void deletePostComment(Long commentId);
    void replyComment(Long commentId, List<Reply> reply);
    void updateReply(Long replyId, Reply reply);
    void deleteReply(Long replyId);
}
