package com.blackeagles.margaret.schedule;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.blackeagles.margaret.Quinton;
import com.blackeagles.margaret.model.FloorPower;
import com.blackeagles.margaret.service.InfluxService;

@Component
public class ScheduledSlack {

  private final InfluxService influxService;
  private Quinton quinton;

  public ScheduledSlack(Quinton quinton, InfluxService influxService) {
    this.quinton = quinton;
    this.influxService = influxService;
  }

  @Scheduled(fixedDelay = 5 * 60 * 1000)
  public void sendFiveMinuteDigestToSlack() {
    ZonedDateTime end = ZonedDateTime.now();
    ZonedDateTime begin = end.minusMinutes(5);
    final List<FloorPower> meanPowers = influxService.getMeanPower(begin, end);
    quinton.say(floorPowerListToString(meanPowers));
  }

  private String floorPowerListToString(List<FloorPower> floorPowers) {
    StringBuilder sb = new StringBuilder();
    for (FloorPower floorPower : floorPowers) {
      sb.append("Floor " + floorPower.getFloor() + " used " + floorPower.getPower() + " RANDOM in the last 5 minutes\n");
    }
    return sb.toString();
  }

}
