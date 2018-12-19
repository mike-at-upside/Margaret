package com.blackeagles.margaret.schedule;

import org.springframework.scheduling.annotation.Scheduled;

public class PowerSchedule {

  @Scheduled(fixedDelay = 5)
  public void sendStatsToSlack() {
    System.out.println("Fixed delay task - " + System.currentTimeMillis() / 1000);
  }

}
