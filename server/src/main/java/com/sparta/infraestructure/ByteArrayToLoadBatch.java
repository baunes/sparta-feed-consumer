package com.sparta.infraestructure;

import com.sparta.domain.Record;
import com.sparta.domain.Sensor;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.Checksum;

public final class ByteArrayToLoadBatch {

  private ByteArrayToLoadBatch() {
  }

  /**
   * Reads an String from a byte array.
   *
   * @param bytes Byte array
   * @return The string read
   * @throws IOException
   */
  public static String toString(byte[] bytes) throws IOException {
    bytes = Objects.requireNonNull(bytes, "bytes[] can not be null");
    DataInputStream dis = toDataOutputStream(bytes);
    return readString(dis);
  }

  private static String readString(DataInputStream dis) throws IOException {
    int length = dis.readInt();
    byte[] content = dis.readNBytes(length);
    return new String(content, StandardCharsets.UTF_8);
  }

  /**
   * Reads a Sensor from a byte array.
   *
   * @param bytes Byte array
   * @return The sensor read
   * @throws IOException
   */
  public static Sensor toSensor(byte[] bytes) throws IOException {
    bytes = Objects.requireNonNull(bytes, "bytes[] can not be null");
    DataInputStream dis = toDataOutputStream(bytes);

    return readSensor(dis);
  }

  private static Sensor readSensor(DataInputStream dis) throws IOException {
    String id = readString(dis);
    int measure = dis.readInt();
    return new Sensor(id, measure);
  }

  /**
   * Reads a Sensor collection from a byte array.
   *
   * @param bytes Byte array
   * @return The sensor Array read
   * @throws IOException
   */
  public static Sensor[] toSensorArray(byte[] bytes) throws IOException {
    bytes = Objects.requireNonNull(bytes, "bytes[] can not be null");
    DataInputStreamWithCRC dis = toDataOutputStream(bytes);

    return readSensorArray(dis).sensors;
  }

  private static SensorArrayAndCRC readSensorArray(DataInputStreamWithCRC dis) throws IOException {
    int length = dis.readInt();
    dis.resetChecksum();
    Sensor[] sensors = new Sensor[length];
    for (int index = 0; index < length; index++) {
      sensors[index] = readSensor(dis);
    }
    long checksum = dis.getChecksum();
    return new SensorArrayAndCRC(sensors, checksum);
  }

  /**
   * Reads a Record from a byte array.
   *
   * @param bytes Byte array
   * @return The Record read
   * @throws IOException
   */
  public static Record toRecord(byte[] bytes) throws IOException {
    bytes = Objects.requireNonNull(bytes, "bytes[] can not be null");
    DataInputStreamWithCRC dis = toDataOutputStream(bytes);

    long recordIndex = dis.readLong();
    long timestamp = dis.readLong();
    String city = readString(dis);
    SensorArrayAndCRC sensorArray = readSensorArray(dis);
    long checksumCalculated = sensorArray.getChecksum();
    long checksum = dis.readLong();
    if (checksumCalculated != checksum) {
      // TODO specialized Exception
      throw new RuntimeException(String.format("invalid CRC32 [%s] checksum, expected to be [%s]", checksumCalculated, checksum));
    }

    return new Record(recordIndex, timestamp, city, sensorArray.getSensors());
  }

  private static DataInputStreamWithCRC toDataOutputStream(byte[] content) {
    ByteArrayInputStream bais = new ByteArrayInputStream(content);
    return new DataInputStreamWithCRC(bais, new CRC32());
  }

  private static final class SensorArrayAndCRC {
    private final Sensor[] sensors;
    private final long checksum;

    public SensorArrayAndCRC(Sensor[] sensors, long checksum) {
      this.sensors = sensors;
      this.checksum = checksum;
    }

    public Sensor[] getSensors() {
      return sensors;
    }

    public long getChecksum() {
      return checksum;
    }
  }

  private static final class DataInputStreamWithCRC extends DataInputStream {

    private final Checksum checksum;

    public DataInputStreamWithCRC(InputStream is, Checksum checksum) {
      super(new CheckedInputStream(is, checksum));
      this.checksum = checksum;
    }

    public long getChecksum() {
      return checksum.getValue();
    }

    public void resetChecksum() {
      checksum.reset();
    }
  }

}
