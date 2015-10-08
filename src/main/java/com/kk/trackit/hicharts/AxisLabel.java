package com.kk.trackit.hicharts;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by kkedari on 8/6/15.
 */
public class AxisLabel {
    @JsonProperty("align")
    private String align;

    @JsonProperty("x")
    private Object x;

    @JsonProperty("y")
    private Object y;

    @JsonProperty("format")
    private String format;

    public AxisLabel() {
        this.align = "right";
        this.format = "{value}";
    }

    public String getAlign() {
        return align;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public Object getX() {
        return x;
    }

    public void setX(Object x) {
        this.x = x;
    }

    public Object getY() {
        return y;
    }

    public void setY(Object y) {
        this.y = y;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
