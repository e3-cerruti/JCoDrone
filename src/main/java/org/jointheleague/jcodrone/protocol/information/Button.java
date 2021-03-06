package org.jointheleague.jcodrone.protocol.information;

import org.jointheleague.jcodrone.CoDrone;
import org.jointheleague.jcodrone.Internals;
import org.jointheleague.jcodrone.Link;
import org.jointheleague.jcodrone.Sensors;
import org.jointheleague.jcodrone.protocol.InvalidDataSizeException;
import org.jointheleague.jcodrone.protocol.Serializable;

import java.nio.ByteBuffer;

public class Button implements Serializable {
    private final byte button;

    public Button(byte button) {
        this.button = button;
    }

    public static byte getSize() {
        return 1;
    }

    public byte getInstanceSize() {
        return getSize();
    }

    @Override
    public byte[] toArray() {
        ByteBuffer buffer = ByteBuffer.allocate(getSize());
        buffer.put(button);
        return buffer.array();
    }

    public static Button parse(byte[] data) throws InvalidDataSizeException {
        if (data.length != getSize()) {
            throw new InvalidDataSizeException(getSize(), data.length);
        }

        ByteBuffer buffer = ByteBuffer.wrap(data);
        byte button = buffer.get();
        return new Button(button);
    }

    @Override
    public void handle(CoDrone coDrone, Link link, Sensors sensors, Internals internals) {
        internals.setButton(this);
    }
}
