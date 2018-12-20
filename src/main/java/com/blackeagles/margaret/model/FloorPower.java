package com.blackeagles.margaret.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class FloorPower {

  private static final String PREFIX = "emon/emontx3/power";

  public String floor; // e.g. emon/emontx3/power1
  public int power;

  public String getFloor() {
    return floor;
  }

  public FloorPower setFloor(final String floor) {
    this.floor = floor;
    return this;
  }

  public FloorPower setPower(final int power) {
    this.power = power;
    return this;
  }

  public int getPowerWatts() {
    return power;
  }

  public double getPowerKilowatts() {
    return getPowerWatts() / 1000d;
  }

  public String getId() {
    return floor.replace(PREFIX, "");
  }

  public double getKilowattHours(int minutes) {
    int hours = 60 / minutes;
    double kilowattHours = this.getPowerKilowatts() / hours;
    return round(kilowattHours, 2);
  }

  public String getSlackString(int minutes) {
    return String.format("Floor %s used an average of %s kW (%s kWh) over the last %d minutes",
        this.getId(), this.getPowerKilowatts(), this.getKilowattHours(minutes), minutes);
  }

  @Override
  public final boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof FloorPower)) {
      return false;
    }
    final FloorPower that = (FloorPower) o;
    return power == that.power &&
        Objects.equals(floor, that.floor);
  }

  @Override
  public final int hashCode() {
    return Objects.hash(floor, power);
  }

  public static double round(double value, int places) {
    if (places < 0) throw new IllegalArgumentException();

    BigDecimal bd = new BigDecimal(value);
    bd = bd.setScale(places, RoundingMode.HALF_UP);
    return bd.doubleValue();
  }

  public String toString() {
    return floor + " " + power;
  }
}
