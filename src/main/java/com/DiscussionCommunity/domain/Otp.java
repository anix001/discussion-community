package com.DiscussionCommunity.domain;

import com.DiscussionCommunity.domain.enumeration.OtpStatus;
import com.DiscussionCommunity.domain.enumeration.OtpType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "_user_otp")
public class Otp extends Auditable{
    @Id
    @GeneratedValue
    private Long id;

    private String otp;

    private Date expiresAt;

    @Enumerated(EnumType.STRING)
    private OtpType otpType;

    @Enumerated(EnumType.STRING)
    private OtpStatus otpStatus;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public Otp(String otp, Date expiresAt, OtpType otpType, OtpStatus otpStatus) {
        this.otp = otp;
        this.expiresAt = expiresAt;
        this.otpType = otpType;
        this.otpStatus = otpStatus;
    }
}
