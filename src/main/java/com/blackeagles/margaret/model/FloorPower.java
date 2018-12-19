package com.blackeagles.margaret.model;

import java.util.Objects;

public class FloorPower {

  public String floor;
  public int power;

  public String getFloor() {
    return floor;
  }

  public FloorPower setFloor(final String floor) {
    this.floor = floor;
    return this;
  }

  public int getPower() {
    return power;
  }

  public FloorPower setPower(final int power) {
    this.power = power;
    return this;
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
