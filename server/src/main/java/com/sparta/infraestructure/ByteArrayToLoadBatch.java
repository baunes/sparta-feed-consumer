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
     * @param bytes Byte array
     * @return The string read
     * @throws IOException
     */
    public static String toString(byte[] bytes) throws IOException {
        bytes = Objects.requireNonNull(bytes, "bytes[] can not be null");
        DataInputStream dis = toDataOutputStream(bytes);
        return readString(dis);
    }

    /**
     * Reads a Sensor from a byte array.
     * @param bytes Byte array
     * @return The sensor read
     * @throws IOException
     */
    public static Sensor toSensor(byte[] bytes) throws IOException {
        bytes = Objects.requireNonNull(bytes, "bytes[] can not be null");
        DataInputStream dis = toDataOutputStream(bytes);
        String id = readString(dis);
        int measure = dis.readInt();
        return new Sensor(id, measure);
    }

    private static String readString(DataInputStream dis) throws IOException {
        int length = dis.readInt();
        byte[] content = dis.readNBytes(length);
        String id = new String(content, StandardCharsets.UTF_8);
        return id;
    }

    private static DataInputStream toDataOutputStream(byte[] content) {
        ByteArrayInputStream bais = new ByteArrayInputStream(content);
        return new DataInputStream(bais);
    }

}
