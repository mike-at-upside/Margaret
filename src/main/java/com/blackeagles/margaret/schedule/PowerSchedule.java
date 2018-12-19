package com.blackeagles.margaret.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.blackeagles.margaret.Quinton;

@Component
public class PowerSchedule {

  private Quinton quinton;


  public PowerSchedule(Quinton quinton) {
    this.quinton = quinton;
  }

  @Scheduled(fixedDelay = 5 * 60 * 1000)
  public void sendStatsToSlack() {
    quinton.say("hello");
  }

}
