package com.kk.trackit.hicharts;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by kkedari on 8/6/15.
 */
public class Legend {
    @JsonProperty("align")
    private String align;

    @JsonProperty("verticalAlign")
    private String verticalAlign;

    @JsonProperty("x")
    private Object x;
    @JsonProperty("y")
    private Object y;
    @JsonProperty("floating")
    private boolean floating;
    @JsonProperty("borderWidth")
    private int borderWidth;


    public Legend() {
        this.align ="left";
        this.verticalAlign = "bottom";
        this.x = 0;
        this. y = 0;
        this.floating = true;
        this.borderWidth = 20;
    }

    public String getAlign() {
        return align;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public String getVerticalAlign() {
        return verticalAlign;
    }

    public void setVerticalAlign(String verticalAlign) {
        this.verticalAlign = verticalAlign;
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

    public boolean isFloating() {
        return floating;
    }

    public void setFloating(boolean floating) {
        this.floating = floating;
    }

    public int getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
    }
}
