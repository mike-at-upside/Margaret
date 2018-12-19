package com.blackeagles.margaret.service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.influxdb.InfluxDB;
import org.springframework.stereotype.Component;

import com.blackeagles.margaret.config.HerokuProperties;
import com.blackeagles.margaret.model.FloorPower;

@Component
public class InfluxService {

  private static final Random RANDOM = new Random();

  private final InfluxDB influxDB;
  private HerokuProperties herokuProperties;

  public InfluxService(InfluxDB influxDB, HerokuProperties herokuProperties) {
   this.influxDB = influxDB;
   this.herokuProperties = herokuProperties;
  }

  public List<FloorPower> getMeanPower(ZonedDateTime beginTime, ZonedDateTime endTime) {
    List<FloorPower> floorPowers = new ArrayList<>();

    for (String phase : herokuProperties.getPhases()) {
      FloorPower floorPower = new FloorPower()
          .setFloor(phase)
          .setPower(RANDOM.nextInt(100000));
      floorPowers.add(floorPower);
    }

    floorPowers.sort((f1, f2) -> f1.getPower() - f2.getPower());

    return floorPowers;
  }
}
