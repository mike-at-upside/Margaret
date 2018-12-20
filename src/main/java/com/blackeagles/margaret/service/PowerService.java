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

    if (normalisedText.equals("last hour") || normalisedText.equals("one hour")) {
      ZonedDateTime end = ZonedDateTime.now();
      ZonedDateTime begin = end.minusHours(1);
      final List<FloorPower> meanPowers = influxService.getMeanPower(begin, end);
      final FloorPower bestFloor = meanPowers.get(0);
      return String.format(":party-parrot: Floor %s performed best using %s kW over the last hour :party-parrot:",
          bestFloor.getId(), bestFloor.getPowerKilowatts());
    } else if (normalisedText.equals("day") || normalisedText.equals("last day") || normalisedText.equals("24 hours")) {
      ZonedDateTime end = ZonedDateTime.now();
      ZonedDateTime begin = end.minusHours(24);
      final List<FloorPower> meanPowers = influxService.getMeanPower(begin, end);
      final FloorPower bestFloor = meanPowers.get(0);
      return String.format(":party-parrot: Floor %s performed best using %s kW over the 24 hours :party-parrot:",
          bestFloor.getId(), bestFloor.getPowerKilowatts());
    } else {
      return "couldn't get best for period: " + normalisedText;
    }
  }

  public String worstPeriod(String text) {
    String normalisedText = normaliseText(text);

    if (normalisedText.equals("last hour") || normalisedText.equals("one hour")) {
      ZonedDateTime end = ZonedDateTime.now();
      ZonedDateTime begin = end.minusHours(1);
      final List<FloorPower> meanPowers = influxService.getMeanPower(begin, end);
      final FloorPower worstFloor = meanPowers.get(meanPowers.size() - 1);
      return String.format(":skull: Floor %s performed worst using %s kW over the last hour :skull:",
          worstFloor.getId(), worstFloor.getPowerKilowatts());
    } else if (normalisedText.equals("day") || normalisedText.equals("last day") || normalisedText.equals("24 hours")) {
      ZonedDateTime end = ZonedDateTime.now();
      ZonedDateTime begin = end.minusHours(24);
      final List<FloorPower> meanPowers = influxService.getMeanPower(begin, end);
      final FloorPower worstFloor = meanPowers.get(meanPowers.size() - 1);
      return String.format(":skull: Floor %s performed worst using %s kW over the last 24 hours :skull:",
          worstFloor.getId(), worstFloor.getPowerKilowatts());
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
