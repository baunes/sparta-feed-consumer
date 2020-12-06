package com.sparta.infraestructure;

import com.sparta.domain.Record;
import com.sparta.domain.Sensor;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

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
        DataInputStream dis = toDataOutputStream(bytes);

        return readSensorArray(dis);
    }

    public static Sensor[] readSensorArray(DataInputStream dis) throws IOException {
        int length = dis.readInt();
        Sensor[] sensors = new Sensor[length];
        for (int index = 0; index < length; index++) {
            sensors[index] = readSensor(dis);
        }
        return sensors;
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
        DataInputStream dis = toDataOutputStream(bytes);

        long recordIndex = dis.readLong();
        long timestamp = dis.readLong();
        String city = readString(dis);
        Sensor[] sensorArray = readSensorArray(dis);
        // TODO Check CRC32

        return new Record(recordIndex, timestamp, city, sensorArray);
    }

    private static DataInputStream toDataOutputStream(byte[] content) {
        ByteArrayInputStream bais = new ByteArrayInputStream(content);
        return new DataInputStream(bais);
    }

}
