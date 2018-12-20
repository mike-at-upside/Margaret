package com.blackeagles.margaret.service;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import com.blackeagles.margaret.model.FloorPower;

@Component
public class PowerService {

  private final InfluxService influxService;

  public PowerService(InfluxService influxService) {
    this.influxService = influxService;
  }

  public String bestPeriod(String text) {
    String normalisedText = normaliseText(text);

    if (normalisedText.equals("last hour")) {
      ZonedDateTime end = ZonedDateTime.now();
      ZonedDateTime begin = end.minusHours(1);
      final List<FloorPower> meanPowers = influxService.getMeanPower(begin, end);
      final FloorPower bestFloor = meanPowers.get(0);
      return String.format("Floor %s performed best using an average of %s kW (%s kWh) over the last %d minutes",
          bestFloor.getId(), bestFloor.getPowerKilowatts(), bestFloor.getKilowattHours(60), 60);
    } else {
      return "couldn't get best for period: " + normalisedText;
    }
  }

  public String worstPeriod(String text) {
    String normalisedText = normaliseText(text);

    if (normalisedText.equals("last hour")) {
      ZonedDateTime end = ZonedDateTime.now();
      ZonedDateTime begin = end.minusHours(1);
      final List<FloorPower> meanPowers = influxService.getMeanPower(begin, end);
      final FloorPower bestFloor = meanPowers.get(meanPowers.size() - 1);
      return String.format("Floor %s performed worst using an average of %s kW (%s kWh) over the last %d minutes",
          bestFloor.getId(), bestFloor.getPowerKilowatts(), bestFloor.getKilowattHours(60), 60);
    } else {
      return "couldn't get worst for period: " + normalisedText;
    }
  }

  private String normaliseText(String text) {
    String trimmedText = text == null ? "" : text.trim();

    if (trimmedText.isEmpty()) {
      return "now";
    }

    return trimmedText.replaceAll(" +", " ").toLowerCase();
  }
}
