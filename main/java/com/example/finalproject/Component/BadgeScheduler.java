package com.example.finalproject.Component;

import com.example.finalproject.Model.Badge;
import com.example.finalproject.Service.BadgeService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BadgeScheduler {

    private BadgeService badgeService;

    @Scheduled(cron = "0 0 0 * * *")
    public void updateBadge() {
        badgeService.assignBadge();
    }
}
