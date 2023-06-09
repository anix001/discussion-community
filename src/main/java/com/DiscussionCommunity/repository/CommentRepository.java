package com.DiscussionCommunity.repository;

import com.DiscussionCommunity.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(
          "SELECT comments from Comment as comments WHERE comments.post.id = :postId"
    )
    List<Comment> fetchAllComments(@Param("postId") Long postId);
}
