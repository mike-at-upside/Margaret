package com.blackeagles.margaret.service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.influxdb.InfluxDB;
import org.influxdb.dto.BoundParameterQuery;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBResultMapper;
import org.springframework.stereotype.Component;

import com.blackeagles.margaret.config.HerokuProperties;
import com.blackeagles.margaret.model.FloorPower;
import com.blackeagles.margaret.model.MeanEntry;

@Component
public class InfluxService {

  private static final String DATABASE = "telegraf";
  private static final String SERIES = "mqtt_consumer";

  private final InfluxDB influxDB;
  private final InfluxDBResultMapper resultMapper;
  private HerokuProperties herokuProperties;

  public InfluxService(InfluxDB influxDB, HerokuProperties herokuProperties) {
   this.influxDB = influxDB;
   this.herokuProperties = herokuProperties;
   this.resultMapper = new InfluxDBResultMapper();
  }

  public List<FloorPower> getMeanPower(ZonedDateTime begin, ZonedDateTime end) {
    List<FloorPower> floorPowers = new ArrayList<>();

    for (String phase : herokuProperties.getPhases()) {

      StringBuilder sb = new StringBuilder();
      sb.append("SELECT mean(value) FROM " + SERIES + " ");
      sb.append("WHERE topic = $topic ");
      sb.append("AND time >= $begin ");
//      sb.append("AND time < $end ");

      final BoundParameterQuery query = BoundParameterQuery.QueryBuilder
          .newQuery(sb.toString())
          .forDatabase(DATABASE)
          .bind("topic", phase)
          .bind("begin", begin.toEpochSecond() * 1000)
          .bind("end", end.toEpochSecond() * 1000)
          .create();

      final QueryResult queryResult = influxDB.query(query);

      final List<MeanEntry> meanEntries = resultMapper.toPOJO(queryResult, MeanEntry.class);

      int meanPower = meanEntries.isEmpty() ? 0 : meanEntries.get(0).getMean();
      FloorPower floorPower = new FloorPower()
          .setFloor(phase)
          .setPower(meanPower);
      floorPowers.add(floorPower);
    }

    floorPowers.sort(Comparator.comparingInt(FloorPower::getPowerWatts));

    return floorPowers;
  }
}
