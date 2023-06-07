package com.DiscussionCommunity.repository;

import com.DiscussionCommunity.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Query(
            "select replies from Reply as replies where replies.comment.id  = :commentId"
    )
    List<Reply> fetchReplyByCommentId(@Param("commentId") Long commentId);
}
