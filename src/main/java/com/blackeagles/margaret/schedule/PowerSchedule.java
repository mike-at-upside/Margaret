package com.blackeagles.margaret.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PowerSchedule {

  @Scheduled(fixedDelay = 5000)
  public void sendStatsToSlack() {
    System.out.println("Fixed delay task - " + System.currentTimeMillis() / 1000);
  }

}
