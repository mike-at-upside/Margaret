package com.blackeagles.margaret.schedule;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.blackeagles.margaret.Quinton;
import com.blackeagles.margaret.model.FloorPower;
import com.blackeagles.margaret.service.InfluxService;

@Component
public class ScheduledSlack {

  private static final List<String> MEDALS = Arrays.asList(":first_place_medal:", ":second_place_medal:", ":third_place_medal:");

  private static final int MINUTES = 5;

  private final InfluxService influxService;
  private Quinton quinton;

  public ScheduledSlack(Quinton quinton, InfluxService influxService) {
    this.quinton = quinton;
    this.influxService = influxService;
  }

  @Scheduled(fixedDelay = MINUTES * 60 * 1000)
  public void sendFiveMinuteDigestToSlack() {
    ZonedDateTime end = ZonedDateTime.now();
    ZonedDateTime begin = end.minusMinutes(MINUTES);
    final List<FloorPower> meanPowers = influxService.getMeanPower(begin, end);
    quinton.say(floorPowerListToString(meanPowers));
  }

  private String floorPowerListToString(List<FloorPower> floorPowers) {
    StringBuilder sb = new StringBuilder();
    int medalIndex = 0;

    for (FloorPower floorPower : floorPowers) {
      sb.append(MEDALS.get(medalIndex) + " ");
      sb.append(floorPower.getSlackString(MINUTES) + "\n");
      medalIndex++;
    }
    return sb.toString();
  }

}