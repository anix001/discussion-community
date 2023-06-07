package com.DiscussionCommunity.service.impl;

import com.DiscussionCommunity.domain.Otp;
import com.DiscussionCommunity.domain.enumeration.OtpStatus;
import com.DiscussionCommunity.repository.OtpRepository;
import com.DiscussionCommunity.service.TriggerService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

@Service
public class TriggerServiceImpl implements TriggerService {
    private final OtpRepository otpRepository;

    public TriggerServiceImpl(OtpRepository otpRepository) {
        this.otpRepository = otpRepository;
    }

    @Override
    public void otpExpireUpdate() {
        List<Otp> otpList = otpRepository.findAll();
        for (Otp otp : otpList) {
            if(otp.getOtpStatus() == OtpStatus.ACTIVE){
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                Timestamp timestamp2 = new Timestamp(otp.getExpiresAt().getTime());
                // get time difference in seconds
                long milliseconds = timestamp.getTime() - timestamp2.getTime();
                int seconds = (int) milliseconds / 1000;
                if(seconds > 0){
                    otp.setOtpStatus(OtpStatus.EXPIRED);
                    otpRepository.save(otp);
                }
            }
        }

    }
}
