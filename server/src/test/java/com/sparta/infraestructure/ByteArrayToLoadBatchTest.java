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

}