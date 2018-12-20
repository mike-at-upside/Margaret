package com.blackeagles.margaret.service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.blackeagles.margaret.model.FloorPower;

@Component
public class PowerService {

  private static final Pattern MINUTES_PATTERN = Pattern.compile("last [0-9]+ minutes");
  private static final Pattern HOURS_PATTERN = Pattern.compile("last [0-9]+ hours");

  private final InfluxService influxService;

  public PowerService(InfluxService influxService) {
    this.influxService = influxService;
  }

  // would need refactoring - these methods are awful!
  public String bestPeriod(String text) {
    String normalisedText = normaliseText(text);

    if (MINUTES_PATTERN.matcher(normalisedText).matches()) {
      int minutes = Integer.parseInt(normalisedText.replaceAll("\\D+", ""));
      ZonedDateTime end = ZonedDateTime.now();
      ZonedDateTime begin = end.minusMinutes(minutes);
      final List<FloorPower> meanPowers = influxService.getMeanPower(begin, end);
      final FloorPower bestFloor = meanPowers.get(0);
      return String.format(
          ":party-parrot: Floor %s performed best using an average of %s kW over the last %d minutes :party-parrot:",
          bestFloor.getId(), bestFloor.getPowerKilowatts(), minutes);
    } else if (HOURS_PATTERN.matcher(normalisedText).matches()) {
      int hours = Integer.parseInt(normalisedText.replaceAll("\\D+", ""));
      ZonedDateTime end = ZonedDateTime.now();
      ZonedDateTime begin = end.minusHours(hours);
      final List<FloorPower> meanPowers = influxService.getMeanPower(begin, end);
      final FloorPower bestFloor = meanPowers.get(0);
      return String.format(
          ":party-parrot: Floor %s performed best using an average of %s kW over the last %d hours :party-parrot:",
          bestFloor.getId(), bestFloor.getPowerKilowatts(), hours);
    } else if (normalisedText.equals("last hour") || normalisedText.equals("one hour")) {
      ZonedDateTime end = ZonedDateTime.now();
      ZonedDateTime begin = end.minusHours(1);
      final List<FloorPower> meanPowers = influxService.getMeanPower(begin, end);
      final FloorPower bestFloor = meanPowers.get(0);
      return String
          .format(":party-parrot: Floor %s performed best using an average of %s kW over the last hour :party-parrot:",
              bestFloor.getId(), bestFloor.getPowerKilowatts());
    } else if (normalisedText.equals("day") || normalisedText.equals("last day") || normalisedText.equals("24 hours")) {
      ZonedDateTime end = ZonedDateTime.now();
      ZonedDateTime begin = end.minusHours(24);
      final List<FloorPower> meanPowers = influxService.getMeanPower(begin, end);
      final FloorPower bestFloor = meanPowers.get(0);
      return String.format(
          ":party-parrot: Floor %s performed best using an average of %s kW over the last 24 hours :party-parrot:",
          bestFloor.getId(), bestFloor.getPowerKilowatts());
    } else {
      return "couldn't get best for period: " + normalisedText;
    }
  }

  public String worstPeriod(String text) {
    String normalisedText = normaliseText(text);

    if (MINUTES_PATTERN.matcher(normalisedText).matches()) {
      int minutes = Integer.parseInt(normalisedText.replaceAll("\\D+", ""));
      ZonedDateTime end = ZonedDateTime.now();
      ZonedDateTime begin = end.minusMinutes(minutes);
      final List<FloorPower> meanPowers = influxService.getMeanPower(begin, end);
      final FloorPower worstFloor = meanPowers.get(meanPowers.size() - 1);
      return String.format(
          ":skull: Floor %s performed worst using an average of %s kW over the last %d minutes :skull:",
          worstFloor.getId(), worstFloor.getPowerKilowatts(), minutes);
    } else if (HOURS_PATTERN.matcher(normalisedText).matches()) {
      int hours = Integer.parseInt(normalisedText.replaceAll("\\D+", ""));
      ZonedDateTime end = ZonedDateTime.now();
      ZonedDateTime begin = end.minusHours(hours);
      final List<FloorPower> meanPowers = influxService.getMeanPower(begin, end);
      final FloorPower worstFloor = meanPowers.get(meanPowers.size() - 1);
      return String.format(
          ":skull: Floor %s performed worst using an average of %s kW over the last %d hours :skull:",
          worstFloor.getId(), worstFloor.getPowerKilowatts(), hours);
    } else if (normalisedText.equals("last hour") || normalisedText.equals("one hour")) {
      ZonedDateTime end = ZonedDateTime.now();
      ZonedDateTime begin = end.minusHours(1);
      final List<FloorPower> meanPowers = influxService.getMeanPower(begin, end);
      final FloorPower worstFloor = meanPowers.get(meanPowers.size() - 1);
      return String.format(":skull: Floor %s performed worst using an average of %s kW over the last hour :skull:",
          worstFloor.getId(), worstFloor.getPowerKilowatts());
    } else if (normalisedText.equals("day") || normalisedText.equals("last day") || normalisedText.equals("24 hours")) {
      ZonedDateTime end = ZonedDateTime.now();
      ZonedDateTime begin = end.minusHours(24);
      final List<FloorPower> meanPowers = influxService.getMeanPower(begin, end);
      final FloorPower worstFloor = meanPowers.get(meanPowers.size() - 1);
      return String.format(":skull: Floor %s performed worst using an average of %s kW over the last 24 hours :skull:",
          worstFloor.getId(), worstFloor.getPowerKilowatts());
    } else {
      return "couldn't get worst for period: " + normalisedText;
    }
  }

  private String normaliseText(String text) {
    String trimmedText = text == null ? "" : text.trim();

    if (trimmedText.isEmpty()) {
      return "last 5 minutes";
    }

    return trimmedText.replaceAll(" +", " ").toLowerCase();
  }
}
