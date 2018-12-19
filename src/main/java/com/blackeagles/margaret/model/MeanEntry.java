package com.blackeagles.margaret.model;

import java.time.ZonedDateTime;

import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

@Measurement(name = "mqtt_consumer")
public class MeanEntry {

  // ISO-8601 datetime.
  @Column(name = "time")
  private String time;

  // Power in watts.
  @Column(name = "mean")
  private Integer mean;

  // Required for InfluxDBResultMapper.
  public MeanEntry() {
  }

  public int getMean() {
    return mean;
  }

  public ZonedDateTime getTime() {
    return ZonedDateTime.parse(time);
  }
}
