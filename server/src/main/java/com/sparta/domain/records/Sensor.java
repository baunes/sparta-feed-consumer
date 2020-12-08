package com.sparta.domain.records;

import java.util.Objects;

public class Sensor {

  private final String id;
  private final int measure;

  public Sensor(String id, int measure) {
    this.id = id;
    this.measure = measure;
  }

  public String getId() {
    return id;
  }

  public int getMeasure() {
    return measure;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Sensor sensor = (Sensor) o;
    return measure == sensor.measure &&
      Objects.equals(id, sensor.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, measure);
  }
}
