package com.kk.trackit.hicharts;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by kkedari on 8/6/15.
 */
public class Options3D {

    @JsonProperty("alpha")
    private int alpha;

    @JsonProperty("beta")
    private int beta;

    @JsonProperty("depth")
    private int depth;

    @JsonProperty("enabled")
    private boolean enabled;

    @JsonProperty("viewDistance")
    private int viewDistance;

    public Options3D() {
        alpha = beta =0;
        depth =viewDistance = 100;

        enabled = false;
    }

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public int getBeta() {
        return beta;
    }

    public void setBeta(int beta) {
        this.beta = beta;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getViewDistance() {
        return viewDistance;
    }

    public void setViewDistance(int viewDistance) {
        this.viewDistance = viewDistance;
    }
}
