package com.syansoft.schedular;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class ScheduledTasks {

    @Scheduled(fixedRate = 60000) // Run every minute
    public void reportCurrentTime() {
        System.out.println("Scheduled task executed at: " + new Date());
    }
}
