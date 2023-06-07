package com.DiscussionCommunity.repository;

import com.DiscussionCommunity.domain.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OtpRepository extends JpaRepository<Otp, Long> {
    @Query(
            "SELECT otp FROM Otp as otp WHERE otp.id =(select max(otp.id) FROM otp)"
    )
    Optional<Otp> fetchLastValueFromTable();

    @Query(
            "SELECT otp from Otp as otp WHERE otp.user.id = :userId"
    )
    List<Otp> fetchAllByUserId(@Param("userId") UUID userId);
}
