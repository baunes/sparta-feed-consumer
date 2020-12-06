package com.sparta.domain;

import java.util.Arrays;
import java.util.Objects;

public class Record {

  private final Long recordIndex;
  private final Long timestamp;
  private final String city;
  private final Sensor[] sensor;

  public Record(Long recordIndex, Long timestamp, String city, Sensor[] sensor) {
    this.recordIndex = recordIndex;
    this.timestamp = timestamp;
    this.city = city;
    this.sensor = sensor;
  }

  public Long getRecordIndex() {
    return recordIndex;
  }

  public Long getTimestamp() {
    return timestamp;
  }

  public String getCity() {
    return city;
  }

  public Sensor[] getSensor() {
    return Arrays.copyOf(this.sensor, this.sensor.length);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Record record = (Record) o;
    return Objects.equals(recordIndex, record.recordIndex) &&
      Objects.equals(timestamp, record.timestamp) &&
      Objects.equals(city, record.city) &&
      Arrays.equals(sensor, record.sensor);
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(recordIndex, timestamp, city);
    result = 31 * result + Arrays.hashCode(sensor);
    return result;
  }
}
