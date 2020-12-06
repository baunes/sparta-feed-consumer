package com.sparta.infraestructure;


import com.sparta.domain.Record;
import com.sparta.domain.Sensor;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.zip.CRC32;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ByteArrayToLoadBatchTest {

  @Test
  void testToStringWithNull() {
    NullPointerException ex = assertThrows(NullPointerException.class,
      () -> ByteArrayToLoadBatch.toString(null));
    assertThat(ex.getMessage()).contains("bytes[] can not be null");
  }

  @Test
  void testToStringWithZeroBytes() {
    assertThrows(EOFException.class, () -> {
      ByteArrayToLoadBatch.toString(new byte[0]);
    });
  }

  @Test
  void testToStringWithBytes() throws IOException {
    String expected = "This is a test \uD83D\uDE00.";
    byte[] expectedBytes = stringToBytes(expected);
    String value = ByteArrayToLoadBatch.toString(expectedBytes);
    assertThat(value).isEqualTo(expected);
  }


  @Test
  void testToSensorWithNull() {
    NullPointerException ex = assertThrows(NullPointerException.class,
      () -> ByteArrayToLoadBatch.toSensor(null));
    assertThat(ex.getMessage()).contains("bytes[] can not be null");
  }

  @Test
  void testToSensorWithZeroBytes() {
    assertThrows(EOFException.class, () -> {
      ByteArrayToLoadBatch.toSensor(new byte[0]);
    });
  }

  @Test
  void testToSensorWithBytes() throws IOException {
    Sensor sensor = new Sensor("1", new Random().nextInt());
    byte[] sensorBytes = sensorToBytes(sensor);
    Sensor value = ByteArrayToLoadBatch.toSensor(sensorBytes);
    assertThat(value).isEqualTo(sensor);
  }


  @Test
  void testToSensorArrayWithNull() {
    NullPointerException ex = assertThrows(NullPointerException.class,
      () -> ByteArrayToLoadBatch.toSensorArray(null));
    assertThat(ex.getMessage()).contains("bytes[] can not be null");
  }

  @Test
  void testToSensorArrayWithZeroBytes() {
    assertThrows(EOFException.class, () -> {
      ByteArrayToLoadBatch.toSensorArray(new byte[0]);
    });
  }

  @Test
  void testToSensorArrayWithBytes() throws IOException {
    Sensor[] sensorArray = new Sensor[]{
      new Sensor("1", new Random().nextInt()),
      new Sensor("2", new Random().nextInt()),
      new Sensor("3", new Random().nextInt()),
      new Sensor("4", new Random().nextInt()),
      new Sensor("5", new Random().nextInt())
    };
    byte[] sensorArrayBytes = sensorArrayToBytes(sensorArray);
    Sensor[] value = ByteArrayToLoadBatch.toSensorArray(sensorArrayBytes);
    assertThat(value).isEqualTo(sensorArray);
  }


  @Test
  void testToRecordWithNull() {
    NullPointerException ex = assertThrows(NullPointerException.class,
      () -> ByteArrayToLoadBatch.toRecord(null));
    assertThat(ex.getMessage()).contains("bytes[] can not be null");
  }

  @Test
  void testToRecordWithZeroBytes() {
    assertThrows(EOFException.class, () -> {
      ByteArrayToLoadBatch.toRecord(new byte[0]);
    });
  }

  @Test
  void testToRecordWithBytes() throws IOException {
    Sensor[] sensorArray = new Sensor[]{
      new Sensor("1", new Random().nextInt()),
      new Sensor("2", new Random().nextInt()),
      new Sensor("3", new Random().nextInt()),
      new Sensor("4", new Random().nextInt()),
      new Sensor("5", new Random().nextInt())
    };
    Record record = new Record(1L, System.currentTimeMillis(), "Valencia", sensorArray);
    byte[] recordBytes = recordToBytes(record);
    Record value = ByteArrayToLoadBatch.toRecord(recordBytes);
    assertThat(value).isEqualTo(record);
  }

  @Test
  void testToRecordWithWrongCRC() throws IOException {
    Sensor[] sensorArray = new Sensor[]{
      new Sensor("1", new Random().nextInt()),
      new Sensor("2", new Random().nextInt()),
      new Sensor("3", new Random().nextInt()),
      new Sensor("4", new Random().nextInt()),
      new Sensor("5", new Random().nextInt())
    };
    Record record = new Record(1L, System.currentTimeMillis(), "Valencia", sensorArray);
    byte[] recordBytes = recordToBytes(record, 12345L);
    RuntimeException ex = assertThrows(RuntimeException.class,
      () -> ByteArrayToLoadBatch.toRecord(recordBytes));
    assertThat(ex.getMessage()).contains("invalid CRC32");
  }


  @Test
  void testToRecordArrayWithNull() {
    NullPointerException ex = assertThrows(NullPointerException.class,
      () -> ByteArrayToLoadBatch.toRecordArray(null));
    assertThat(ex.getMessage()).contains("bytes[] can not be null");
  }

  @Test
  void testToRecordArrayWithZeroBytes() {
    assertThrows(EOFException.class, () -> {
      ByteArrayToLoadBatch.toRecordArray(new byte[0]);
    });
  }

  @Test
  void testToRecordArrayWithBytes() throws IOException {
    Sensor[] sensorArray1 = new Sensor[]{
      new Sensor("1", new Random().nextInt()),
      new Sensor("2", new Random().nextInt()),
      new Sensor("3", new Random().nextInt()),
      new Sensor("4", new Random().nextInt()),
      new Sensor("5", new Random().nextInt())
    };

    Sensor[] sensorArray2 = new Sensor[]{
      new Sensor("1", new Random().nextInt()),
      new Sensor("2", new Random().nextInt()),
      new Sensor("3", new Random().nextInt()),
      new Sensor("4", new Random().nextInt()),
      new Sensor("5", new Random().nextInt())
    };
    Record[] recordArray = new Record[]{
      new Record(1L, System.currentTimeMillis(), "Valencia", sensorArray1),
      new Record(1L, System.currentTimeMillis(), "Geneva", sensorArray2)
    };
    byte[] recordArrayBytes = recordArrayToBytes(recordArray);
    Record[] value = ByteArrayToLoadBatch.toRecordArray(recordArrayBytes);
    assertThat(value).isEqualTo(recordArray);
  }


  private byte[] stringToBytes(String value) throws IOException {
    byte[] valueBytes = value.getBytes(StandardCharsets.UTF_8);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(baos);
    dos.writeInt(valueBytes.length);
    dos.write(valueBytes);
    return baos.toByteArray();
  }

  private byte[] sensorToBytes(Sensor sensor) throws IOException {
    // Id
    byte[] valueBytes = sensor.getId().getBytes(StandardCharsets.UTF_8);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(baos);
    dos.writeInt(valueBytes.length);
    dos.write(valueBytes);

    // measure
    dos.writeInt(sensor.getMeasure());
    return baos.toByteArray();
  }

  private byte[] sensorArrayToBytes(Sensor[] sensorArray) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(baos);

    // Length
    dos.writeInt(sensorArray.length);

    for (Sensor sensor : sensorArray) {
      dos.write(sensorToBytes(sensor));
    }
    return baos.toByteArray();
  }

  private byte[] recordToBytes(Record record) throws IOException {
    return recordToBytes(record, -1);
  }

  private byte[] recordToBytes(Record record, long crc32Value) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(baos);

    // Index
    dos.writeLong(record.getRecordIndex());

    // Timestamp
    dos.writeLong(record.getTimestamp());

    // City
    byte[] valueBytes = record.getCity().getBytes(StandardCharsets.UTF_8);
    dos.writeInt(valueBytes.length);
    dos.write(valueBytes);

    CRC32 crc32 = new CRC32();
    byte[] sensorArrayBytes = sensorArrayToBytes(record.getSensor());
    // Bytes of SensorData
    dos.writeInt(sensorArrayBytes.length);

    // Sensor Data
    dos.write(sensorArrayBytes);
    crc32.update(sensorArrayBytes);

    // CRC32
    if (crc32Value != -1) {
      dos.writeLong(crc32Value);
    } else {
      dos.writeLong(crc32.getValue());
    }

    return baos.toByteArray();
  }

  private byte[] recordArrayToBytes(Record[] recordArray) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(baos);

    dos.writeLong(recordArray.length);
    for (Record record : recordArray) {
      dos.write(recordToBytes(record));
    }
    return baos.toByteArray();
  }

}