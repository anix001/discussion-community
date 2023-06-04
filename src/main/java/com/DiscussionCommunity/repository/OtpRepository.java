package com.DiscussionCommunity.repository;

import com.DiscussionCommunity.domain.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp, Long> {
    @Query(
            "SELECT otp FROM Otp as otp WHERE otp.id =(select max(otp.id) FROM otp)"
    )
    Optional<Otp> fetchLastValueFromTable();
}
