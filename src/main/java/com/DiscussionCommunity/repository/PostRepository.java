package com.DiscussionCommunity.repository;

import com.DiscussionCommunity.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(
            "SELECT posts from Post as posts Where posts.community.id =:communityId"
    )
    List<Post> findPostsByCommunityId(@Param("communityId") Long communityId);
}
