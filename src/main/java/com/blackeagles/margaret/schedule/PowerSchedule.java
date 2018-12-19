package com.blackeagles.margaret.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.blackeagles.margaret.config.HerokuProperties;

@Component
public class PowerSchedule {

  private final HerokuProperties herokuProperties;

  public PowerSchedule(HerokuProperties herokuProperties) {
    this.herokuProperties = herokuProperties;
  }

  @Scheduled(fixedDelay = 5000)
  public void sendStatsToSlack() {
    System.out.println(herokuProperties.getQuintonUrl());
  }

}
