package org.jointheleague.jcodrone.protocol.information;

import org.jointheleague.jcodrone.protocol.InvalidDataSizeException;
import org.jointheleague.jcodrone.protocol.Serializable;

import java.nio.ByteBuffer;

public class TrimDrive implements Serializable {
    private final short wheel;

    public TrimDrive(short wheel) {
        this.wheel = wheel;
    }

    public static int getSize() {
        return 2;
    }

    @Override
    public byte[] toArray() {
        ByteBuffer buffer = ByteBuffer.allocate(getSize());
        buffer.putShort(wheel);
        return buffer.array();
    }

    public static TrimDrive parse(byte[] data) throws InvalidDataSizeException {
        if (data.length != getSize()) {
            throw new InvalidDataSizeException(getSize(), data.length);
        }

        ByteBuffer buffer = ByteBuffer.wrap(data);
        short wheel = buffer.getShort();
        return new TrimDrive(wheel);

    }
}