package com.DiscussionCommunity.repository;

import com.DiscussionCommunity.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    @Query(
            "SELECT DISTINCT users FROM User as users JOIN users.communityList community WHERE community.id =:communityId"
    )
    List<User> findUserByCommunityId(@Param("communityId") Long communityId);
}
