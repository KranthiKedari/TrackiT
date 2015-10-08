package com.kk.trackit.hicharts;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by kkedari on 8/6/15.
 */
public class AxisTitle {
    @JsonProperty("align")
    private String align;

    @JsonProperty("floating")
    private boolean floating;

    @JsonProperty("text")
    private String text;


    public AxisTitle() {
        this.align = "middle";
        this.floating = false;
        this.text = "TITLE";
    }

    public String getAlign() {
        return align;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public boolean isFloating() {
        return floating;
    }

    public void setFloating(boolean floating) {
        this.floating = floating;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
