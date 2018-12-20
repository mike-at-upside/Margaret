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
    ZonedDateTime now = ZonedDateTime.now();
    ZonedDateTime fiveMinutesAgo = now.minusMinutes(MINUTES);
    final List<FloorPower> meanFiveMinutesPowers = influxService.getMeanPower(fiveMinutesAgo, now);
    quinton.say(floorPowerListToString(meanFiveMinutesPowers));





    ZonedDateTime oneHourAgo = now.minusHours(1);
    final List<FloorPower> meanOneHourPowers = influxService.getMeanPower(oneHourAgo.minusMinutes(5), oneHourAgo);

    int totalOneHourAgo = 0;
    for (FloorPower floorPower : meanOneHourPowers) {
      totalOneHourAgo = totalOneHourAgo + floorPower.getPowerWatts();
    }

    int totalFiveMinutes = 0;
    for (FloorPower floorPower : meanFiveMinutesPowers) {
      totalFiveMinutes = totalFiveMinutes + floorPower.getPowerWatts();
    }

    if (totalFiveMinutes > totalOneHourAgo) {
      double percentageDifference = 100 * (totalFiveMinutes-totalOneHourAgo)/(double)totalFiveMinutes;
      quinton.say(String.format("Easy tiger, you're using %s kW right now; (%s%% *more* than the same time an hour ago) :tiger2:",
          totalFiveMinutes/1000d, FloorPower.round(percentageDifference, 2)));
    } else {
      double percentageDifference = 100 * (totalOneHourAgo-totalFiveMinutes)/(double)totalOneHourAgo;
      quinton.say(String.format("Congrats! You're using %s kW right now; (%s%% *less* than the same time an hour ago) :beret-parrot:",
          totalFiveMinutes/1000d, FloorPower.round(percentageDifference, 1)));
    }
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
