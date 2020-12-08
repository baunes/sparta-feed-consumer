package com.sparta.domain.records;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Record {

  private final Long recordIndex;
  private final Long timestamp;
  private final String city;
  private final List<Sensor> sensor;

  public Record(Long recordIndex, Long timestamp, String city, List<Sensor> sensor) {
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

  public List<Sensor> getSensor() {
    return Collections.unmodifiableList(sensor);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Record record = (Record) o;
    return Objects.equals(recordIndex, record.recordIndex) &&
      Objects.equals(timestamp, record.timestamp) &&
      Objects.equals(city, record.city) &&
      Objects.equals(sensor, record.sensor);
  }

  @Override
  public int hashCode() {
    return Objects.hash(recordIndex, timestamp, city, sensor);
  }
}
