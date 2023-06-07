package com.DiscussionCommunity.config;

import com.DiscussionCommunity.service.TriggerService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class ScheduleConfig {
    private final TriggerService triggerService;

    public ScheduleConfig(TriggerService triggerService) {
        this.triggerService = triggerService;
    }

    @Scheduled(fixedRate = 30000)
    public void updateOtpStatus(){
        triggerService.otpExpireUpdate();
    }
}
