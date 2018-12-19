package com.blackeagles.margaret.model;

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

  public int getPowerWatts() {
    return power;
  }

  public double getPowerKilowatts() {
    return getPowerWatts() / 1000d;
  }

  public FloorPower setPower(final int power) {
    this.power = power;
    return this;
  }

  public String getId() {
    return floor.replace(PREFIX, "");
  }

  public String getSlackString(int minutes) {
    return String.format("Floor %s used an average of %s kW in the last %d minutes", this.getId(), this.getPowerKilowatts(), minutes);
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
}
