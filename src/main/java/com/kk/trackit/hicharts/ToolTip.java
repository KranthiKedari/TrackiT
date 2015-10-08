package com.kk.trackit.hicharts;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by kkedari on 8/6/15.
 */
public class ToolTip {
    @JsonProperty("shared")
    private boolean shared;
    @JsonProperty("crosshair")
    private boolean crosshair;


    public ToolTip() {
        this.shared = true;
        this.crosshair = true;
    }

    public boolean isShared() {
        return shared;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
    }

    public boolean isCrosshair() {
        return crosshair;
    }

    public void setCrosshair(boolean crosshair) {
        this.crosshair = crosshair;
    }
}
