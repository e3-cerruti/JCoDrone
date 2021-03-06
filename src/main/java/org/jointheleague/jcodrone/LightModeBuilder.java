package org.jointheleague.jcodrone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jointheleague.jcodrone.protocol.light.*;

import static org.jointheleague.jcodrone.protocol.Validator.isValidUnsignedByte;

public class LightModeBuilder {
    private static Logger log = LogManager.getLogger(LightModeBuilder.class);

    private LightModeDrone mode = null;
    private Colors colors = null;
    private Color color = null;
    private int interval = 255;

    private boolean modeSet = false;
    private boolean colorSet = false;
    private boolean colorsSet = false;

    public LightModeBuilder setMode(LightModeDrone mode) {
        this.mode = mode;
        this.modeSet = true;
        return this;
    }

    public LightModeBuilder setColor(Colors color) {
        if (colorSet) {
            this.colorSet = false;
            log.warn("Value of color is being overridden by setColors for light mode.");
        }
        this.colors = color;
        this.colorsSet = true;
        return this;
    }

    public LightModeBuilder setColor(String color) {
        return this.setColor(Colors.valueOf(color.toUpperCase()));
    }

    public LightModeBuilder setColor(Color color) {
        if (colorsSet) {
            this.colorsSet = false;
            log.warn("Value of colors is being overridden by setColor for light mode.");
        }
        this.color = color;
        this.colorSet = true;
        return this;
    }

    public LightModeBuilder setColor(int r, int g, int b) {
        return this.setColor(new Color(r, g, b));
    }

    public LightModeBuilder setInterval(int interval) {
        if (!isValidUnsignedByte(interval)) {
            throw new IllegalArgumentException("Interval must be between 0 to 255.");
        }
        this.interval = interval;
        return this;
    }

    public LightMode build() {
        if (!isModeSet() || (!isColorsSet() && !isColorSet())) {
            throw new IllegalStateException("A light mode requires a mode and a color.");
        } else {
            if (isColorsSet()) {
                return new LightModeColors(mode, colors, interval);
            } else {
                return new LightModeColor(mode, color, interval);
            }
        }
    }

    private boolean isColorsSet() {
        return colorsSet;
    }

    private boolean isModeSet() {
        return modeSet;
    }

    private boolean isColorSet() {
        return colorSet;
    }
}