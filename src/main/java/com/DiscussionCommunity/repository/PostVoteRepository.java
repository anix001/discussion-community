package com.DiscussionCommunity.repository;

import com.DiscussionCommunity.domain.PostVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface PostVoteRepository extends JpaRepository<PostVote, Long> {

    @Query(
        "SELECT postVote FROM PostVote as postVote WHERE postVote.user.id =:userId AND postVote.post.id =:postId"
    )
    PostVote fetchByUserIdAndPostId(@Param("userId") UUID userId, @Param("postId") Long postId);

    @Query(
            "SELECT postVote FROM PostVote as postVote WHERE postVote.user.id =:userId AND postVote.post.id =:postId"
    )
    Optional<PostVote> isUserAlreadyVotedPost(@Param("userId") UUID userId, @Param("postId") Long postId);
}
